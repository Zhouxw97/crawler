package com.wantwant.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wantwant.entity.dto.JdCouponsDto;
import com.wantwant.entity.po.JdItemPo;
import com.wantwant.entity.vo.JdItemVo;
import com.wantwant.service.JdItemService;
import com.wantwant.utils.HttpUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
    private static String patterAvlCoupon = "(\"avlCoupon\":)(.*?)(},\\n)";

    //入口
    private static final String url = "https://search.jd.com/Search?keyword=%E6%97%BA%E6%97%BA&enc=utf-8&suggest=1.his.0.0&wq=&pvid=594f60ed400f48d693a3eb9f3d98e617&page=";
    private static final String priceUrl = "http://p.3.cn/prices/mgets?skuIds=J_";
    private static final String itemUrl= "https://item.jd.com/?.html";
    private static final String couponUrl = "https://cd.jd.com/coupon/service";

    //当下载任务完成后，间隔多长时间进行下次任务。
    //@Scheduled(fixedDelay = 100 * 1000)
    @SneakyThrows
    public void doJdItemTask(int pages) {
        for (int i = 1; i <= pages; i++) {
            int pageNum = i * 2 - 1;
            log.info("pageNum:{}",i);
            String html = httpUtils.doGetHtml(url + i,false);
            //解析页面，获取商品数据并存储
            parseHtml(html);
            Thread.sleep(1000);
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
                if (!CollectionUtils.isEmpty(itemBySku)){
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
                Thread.sleep(500);
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
                if (StringUtils.isEmpty(jdItemPo.getPromotions())){
                    jdItemPo.setCoupon(element.select("div.p-icons > i[data-tips=本商品参与满件促销]").text());
                }

                Pattern p = Pattern.compile(patterAvlCoupon);
                Matcher m = p.matcher(itemStr);
                if (m.find()){
                    String coupons = m.group().trim();
                    coupons = coupons.substring(12, coupons.length() - 1);
                    JSONObject jsonObject = JSONObject.parseObject(coupons);
                    List<JdCouponsDto> couponsList = JSONObject.parseArray(jsonObject.get("coupons").toString(), JdCouponsDto.class);
                    if (CollectionUtils.isNotEmpty(couponsList)){
                        log.info("coupons:{}",couponsList.toString());
                        jdItemPo.setCoupon(StringUtils.join(couponsList.toArray(),"\n"));
                    }
                }
                if (StringUtils.isEmpty(jdItemPo.getCoupon())) {
                    jdItemPo.setCoupon(element.select("div.p-icons > i[data-tips=本商品可领用优惠券]").text());
                }
                jdItemPo.setCreated(LocalDateTime.now());
                jdItemService.saveJdItem(jdItemPo);
            }
        }
    }


    @SneakyThrows
    public void getDetail(){
        String url = "https://item.jd.com/6338667.html";
        String itemStr = httpUtils.doGetHtml(url, true);

        String coupons = "";
        Pattern p = Pattern.compile(patterAvlCoupon);
        Matcher m = p.matcher(itemStr);
        if (m.find()){
            coupons = m.group().trim();
            coupons = coupons.substring(12, coupons.length() - 1);
            log.info("coupons:{}",coupons);
            JSONObject jsonObject = JSONObject.parseObject(coupons);
            List<JdCouponsDto> couponsList = JSONObject.parseArray(jsonObject.get("coupons").toString(), JdCouponsDto.class);
            log.info("couponsList:{}",StringUtils.join(couponsList.toArray(),"\n"));
        }


    }

}
