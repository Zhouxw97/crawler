package com.wantwant.task;

import com.alibaba.fastjson.JSON;
import com.wantwant.entity.po.JdItemPo;
import com.wantwant.entity.vo.JdItemVo;
import com.wantwant.service.JdItemService;
import com.wantwant.utils.HttpUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JD定时爬取数据
 *
 * @author zhouxiaowen
 * @date 2021-04-18 22:55
 * @version 1.0
 */
@Slf4j
@Component
public class JdItemTask {

    @Autowired
    private HttpUtils httpUtils;

    @Autowired
    private JdItemService jdItemService;

    //正则表达式
    private static String patternStr19 = "\"19\":(.*?)(\",)";
    private static String patternStr60 = "\"60\":(.*?)(\",)";
    private static String patterCat = "(cat:)(.*?)(])";
    private static String patterVenderId = "(venderId:)(.*?)(,)";

    //入口
    private static final String url = "https://search.jd.com/Search?keyword=%E6%97%BA%E6%97%BA&qrst=1&wq=%E6%97%BA%E6%97%BA&stock=1&ev=exbrand_%E6%97%BA%E6%97%BA%5E&click=0&page=";
    private static final String priceUrl = "http://p.3.cn/prices/mgets?skuIds=J_";
    private static final String itemUrl= "https://item.jd.com/?.html";
    private static final String couponUrl = "https://cd.jd.com/coupon/service";

    //当下载任务完成后，间隔多长时间进行下次任务。
    //@Scheduled(fixedDelay = 100 * 1000)
    @SneakyThrows
    public void doJdItemTask() {
        for (int i = 1; i < 2; i = i + 2) {
            int pageNum = 1 * 2 - 1;
            log.info("pageNum:{}",pageNum);
            String html = httpUtils.doGetHtml(url + pageNum,false);
            //解析页面，获取商品数据并存储
            parseHtml(html);
            Thread.sleep(200l);
        }
    }

    /**
     * 解析页面，并存储数据到数据库
     *
     * @param html
     * @return: void
     * @author: zhouxiaowen
     * @date: 2021-04-18 22:42
     */
    @SneakyThrows
    private void parseHtml(String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("div#J_goodsList >ul >li");
        for (Element element : elements) {
            JdItemPo jdItemPo = new JdItemPo();
            jdItemPo.setSpu(element.attr("data-spu"));
            String skuStr = element.attr("data-sku");
            jdItemPo.setSku(skuStr);
            if (skuStr != "") {
                //根据sku查询数据库这个商品,有的话跳过
                List<JdItemVo> itemBySku = jdItemService.getItemBySku(skuStr);
                if (CollectionUtils.isEmpty(itemBySku)){
                    continue;
                }
                jdItemPo.setTitle(element.select("div.p-name > a > em").text());
                jdItemPo.setStore(element.select("div.p-shop > span > a").text());
                jdItemPo.setPic("https:"+element.select("img").attr("data-lazy-img"));

                String priceStr = httpUtils.doGetHtml(priceUrl + skuStr, false);
                Map<String,String> priceMap = JSON.parseObject(JSON.parseArray(priceStr).get(0).toString(),Map.class);
                jdItemPo.setCurrentPrice(priceMap.get("p"));
                jdItemPo.setGuidePrice(priceMap.get("op"));

                //详情
                String itemUrlReplace = itemUrl.replace("?", skuStr);
                jdItemPo.setUrl(itemUrlReplace);
                String itemStr = httpUtils.doGetHtml(itemUrlReplace, true);
                Document itemDocument = Jsoup.parse(itemStr);
                String scriptStr = itemDocument.getElementsByTag("script").toString();

                StringBuilder stringBuilder = new StringBuilder();
                //获取19促销内容
                Pattern pattern19 = Pattern.compile(patternStr19);
                Matcher matcher19 = pattern19.matcher(scriptStr);
                if(matcher19.find()){
                    String promotions = matcher19.group().trim();
                    if (!StringUtils.isEmpty(promotions)){
                        stringBuilder.append(promotions.substring(6, promotions.length() - 2));
                        stringBuilder.append(";");
                    }
                }
                //获取60促销内容
                Pattern pattern60 = Pattern.compile(patternStr60);
                Matcher matcher60 = pattern60.matcher(scriptStr);
                if(matcher60.find()){
                    String promotions = matcher60.group().trim();
                    if (!StringUtils.isEmpty(promotions)){
                        stringBuilder.append(promotions.substring(6, promotions.length() - 2));
                        stringBuilder.append(";");
                    }
                }
                jdItemPo.setPromotions(stringBuilder.toString());

                String cats = "";
                Pattern patternCat = Pattern.compile(patterCat);
                Matcher matcherCat = patternCat.matcher(scriptStr);
                if (matcherCat.find()){
                    cats = matcherCat.group().trim();
                    cats = cats.substring(6, cats.length() - 1);
                }
                String venderId = "";
                Pattern patternVenderId = Pattern.compile(patterVenderId);
                Matcher matcherVenderId = patternVenderId.matcher(scriptStr);
                if (matcherVenderId.find()){
                    venderId = matcherVenderId.group().trim();
                    venderId = venderId.substring(9, cats.length() - 1);
                }
                List<BasicNameValuePair> paramList = new ArrayList<>();
                paramList.add(new BasicNameValuePair("skuId",skuStr));
                paramList.add(new BasicNameValuePair("cat",cats));
                paramList.add(new BasicNameValuePair("venderId",venderId));
                String params = EntityUtils.toString(new UrlEncodedFormEntity(paramList, Consts.UTF_8));
                String coupons = httpUtils.doGetHtml(couponUrl + "?" + params, true);
                //todo:解析优惠劵
                jdItemPo.setCoupon(coupons);
                jdItemService.saveJdItem(jdItemPo);
            }
        }
    }

}
