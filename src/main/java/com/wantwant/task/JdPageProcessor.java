package com.wantwant.task;

import com.alibaba.fastjson.JSON;
import com.wantwant.entity.po.JdItemPo;
import com.wantwant.utils.HttpUtils;
import com.wantwant.utils.downloader.HttpClientDownloader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * jd抓包处理
 *
 * @author zhouxiaowen
 * @date 2021-04-21 09:25
 * @version 1.0
 */
@Slf4j
@Service
public class JdPageProcessor implements PageProcessor {


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

    //抓取网站的相关配置
    private Site site = Site.me()
            .setRetryTimes(3)//下载失败重试次数
            .setSleepTime(2 * 1000)//请求之间间隔时间
            .setTimeOut(10 * 1000)//超时时间
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * 爬虫核心逻辑，编写抽取逻辑
     *
     * @param page
     * @return: void
     * @author: zhouxiaowen
     * @date: 2021-04-21 10:00
     */
    @Override
    public void process(Page page) {
        HttpUtils httpUtils = new HttpUtils();
        Document document = page.getHtml().getDocument();
        Elements elements = document.select("div#J_goodsList >ul >li");
        elements.forEach(e ->{
            JdItemPo jdItemPo = new JdItemPo();
            jdItemPo.setSpu(e.attr("data-spu"));
            String skuStr = e.attr("data-sku");
            jdItemPo.setSku(skuStr);
            jdItemPo.setTitle(e.select("div.p-name > a > em").text());
            jdItemPo.setStore(e.select("div.p-shop > span > a").text());
            jdItemPo.setPic("https:"+e.select("img").attr("data-lazy-img"));

            String priceStr = httpUtils.doGetHtml(priceUrl + skuStr, false);
            Map<String,String> priceMap = JSON.parseObject(JSON.parseArray(priceStr).get(0).toString(),Map.class);
            jdItemPo.setCurrentPrice(priceMap.get("p"));
            jdItemPo.setGuidePrice(priceMap.get("op"));

            //详情页
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

            try {
                //todo:解析优惠劵
                String params = EntityUtils.toString(new UrlEncodedFormEntity(paramList, Consts.UTF_8));
                String coupons = httpUtils.doGetHtml(couponUrl + "?" + params, false);
                jdItemPo.setCoupon(coupons);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            jdItemPo.setPromotions(stringBuilder.toString());
            jdItemPo.setCreated(LocalDateTime.now());
            page.putField("jdItemPo",jdItemPo);

        });

    }

    //@Scheduled(initialDelay = 1000,fixedDelay = 10 * 1000)
    public void doProcess(){
        for (int i = 1; i <= 2; i++) {
            int pageNum = i * 2 - 1;
            log.info("page:{}",pageNum);
            Spider.create(new JdPageProcessor())
                    .setDownloader(new HttpClientDownloader())
                    .addUrl(url+pageNum)
                    .thread(10)
                    .addPipeline(new MybatisPipeline())
                    .run();
        }
    }

}
