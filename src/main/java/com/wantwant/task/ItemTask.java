package com.wantwant.task;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.wantwant.entity.po.ItemPo;
import com.wantwant.enums.StatusEnum;
import com.wantwant.service.ItemService;
import com.wantwant.utils.HttpUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 定时爬取数据
 *
 * @author zhouxiaowen
 * @date 2021-04-18 22:55
 * @version 1.0
 */
@Component
@Slf4j
public class ItemTask {

    @Autowired
    private HttpUtils httpUtils;

    @Autowired
    private ItemService itemService;

    //当下载任务完成后，间隔多长时间进行下次任务。
    //@Scheduled(fixedDelay = 100 * 1000)
    @SneakyThrows
    public void itemTask() throws JsonProcessingException {
        //1.声明地址
        String url = "https://search.jd.com/Search?keyword=%E6%97%BA%E6%97%BA&qrst=1&wq=%E6%97%BA%E6%97%BA&stock=1&ev=exbrand_%E6%97%BA%E6%97%BA%5E&click=0&page=";
        //2.遍历页面
        for (int i = 1; i < 2; i = i + 2) {
            log.info("url:{}",url+i);
            String html = httpUtils.doGetHtml(url + i,false);
            //解析页面，获取商品数据并存储
            parse(html);
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
    private void parse(String html) throws JsonProcessingException {
        //解析html
        Document document = Jsoup.parse(html);
        //获取spu
        Elements elements = document.select("div#J_goodsList >ul >li");

        for (Element element : elements) {
            //获取商品的spu
            Long spu = StatusEnum.NOT_EXIST.getType();
            String spuStr = element.attr("data-spu");
            if (spuStr != "") {
                spu = Long.parseLong(spuStr);
                Long sku = Long.parseLong(element.attr("data-sku"));//sku
                //根据sku查询数据库这个商品,有的话跳过
                Wrapper<ItemPo> lambdaQueryWrapper = new LambdaQueryWrapper<ItemPo>().eq(ItemPo::getSpu, sku);
                List<ItemPo> itemList = itemService.list(lambdaQueryWrapper);
                if (itemList.size()>0){
                    continue;
                }


                //列表标题
                String title = element.select("div.p-name > a > em").text();
                //价格
                String price = element.select("div.p-price > strong > i").text();
                //店铺
                String store = element.select("div.p-shop > span > a").text();
                //商品的详情url
                String itemUrl= "https://item.jd.com/"+sku+".html";
                String imgUrl="https:"+element.select("img").attr("data-lazy-img");
                //标题
                String itemInfo = httpUtils.doGetHtml(itemUrl,true);
                if (StringUtils.isEmpty(title)) {
                    title = Jsoup.parse(itemInfo).select("div#itemName").text();
                }
                //促销活动
                Elements script = Jsoup.parse(itemInfo).getElementsByTag("script");
                String scriptStr = script.toString();
                StringBuilder stringBuilder = new StringBuilder();

                //获取19促销内容
                String patternStr19 = "\"19\":(.*?)(\",)";
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
                String patternStr60 = "\"60\":(.*?)(\",)";
                Pattern pattern60 = Pattern.compile(patternStr60);
                Matcher matcher60 = pattern60.matcher(scriptStr);
                if(matcher60.find()){
                    String promotions = matcher60.group().trim();
                    if (!StringUtils.isEmpty(promotions)){
                        stringBuilder.append(promotions.substring(6, promotions.length() - 2));
                        stringBuilder.append(";");
                    }
                }

                ItemPo item = new ItemPo();
                item.setSpu(spu);
                item.setSku(sku);
                item.setTitle(title);
                item.setStore(store);
                item.setPromotions(stringBuilder.toString());
                item.setUrl(itemUrl);
                item.setPic(imgUrl);
                item.setPrice(Double.valueOf(price));
                item.setCreated(LocalDateTime.now());
                itemService.save(item);
            }
        }
    }

    /**
     * 获取商品详情页信息
     *
     * @param
     * @return: void
     * @author: zhouxiaowen
     * @date: 2021-04-20 19:11
     */
    public void getItemDetails(){
        String itemUrl= "https://item.jd.com/39447237507.html";
        //标题
        String itemInfo = httpUtils.doGetHtml(itemUrl,false);
        String title = Jsoup.parse(itemInfo).select("div.sku-name").text();
        //促销活动
        Elements script = Jsoup.parse(itemInfo).getElementsByTag("script");
        String scriptStr = script.toString();
        StringBuilder stringBuilder = new StringBuilder();

        //优惠劵
        String cats = "";
        String patterCat = "(cat:)(.*?)(])";
        Pattern patternCat = Pattern.compile(patterCat);
        Matcher matcherCat = patternCat.matcher(scriptStr);
        if (matcherCat.find()){
            cats = matcherCat.group().trim();
            cats = cats.substring(6, cats.length() - 1);
        }
        String venderId = "";
        String patterVenderId = "(venderId:)(.*?)(,)";
        Pattern patternVenderId = Pattern.compile(patterVenderId);
        Matcher matcherVenderId = patternVenderId.matcher(scriptStr);
        if (matcherVenderId.find()){
            venderId = matcherVenderId.group().trim();
            venderId = venderId.substring(9, cats.length() - 1);
        }

        List<BasicNameValuePair> paramList = new ArrayList<>();
        paramList.add(new BasicNameValuePair("skuId","39447237507"));
        paramList.add(new BasicNameValuePair("cat",cats));
        paramList.add(new BasicNameValuePair("venderId",venderId));
        try {
            String params = EntityUtils.toString(new UrlEncodedFormEntity(paramList, Consts.UTF_8));
            String coupons = httpUtils.doGetHtml("https://cd.jd.com/coupon/service" + "?" + params, false);
            System.out.println("coupons = " + coupons);

        } catch (IOException e) {
            e.printStackTrace();
        }


        //获取19促销内容
        String patternStr19 = "\"19\":(.*?)(\",)";
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
        String patternStr60 = "\"60\":(.*?)(\",)";
        Pattern pattern60 = Pattern.compile(patternStr60);
        Matcher matcher60 = pattern60.matcher(scriptStr);
        if(matcher60.find()){
            String promotions = matcher60.group().trim();
            if (!StringUtils.isEmpty(promotions)){
                stringBuilder.append(promotions.substring(6, promotions.length() - 2));
                stringBuilder.append(";");
            }
        }

        log.info("stringBuilder:{}",stringBuilder.toString());

    }

}
