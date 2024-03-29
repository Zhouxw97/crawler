package com.wantwant.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wantwant.enums.StatusEnum;
import com.wantwant.pojo.Item;
import com.wantwant.service.ItemService;
import com.wantwant.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
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
public class ItemTask {
    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private ItemService itemService;
    //用于解析json
    @Autowired
    private ObjectMapper MAPPER ;

    //当下载任务完成后，间隔多长时间进行下次任务。
    //@Scheduled(fixedDelay = 100 * 1000)
    public void itemTask() throws JsonProcessingException {
        //1.声明地址
        String url = "https://search.jd.com/Search?keyword=%E6%97%BA%E6%97%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&suggest=1.def.0.V18--12s0%2C20s0%2C38s0%2C97s0&wq=shouji&s=122&click=0&page=";
        //2.遍历页面
        for (int i = 19; i < 30; i = i + 2) {
            System.out.println("页数 = " + i);
            String html = httpUtils.doGetHtml(url + i,false );
            //解析页面，获取商品数据并存储
            parse(html);
        }
    }

    public void test(){

        String itemInfo = httpUtils.doGetHtml("https://item.m.jd.com/product/1355606.html",true);
        //促销活动
        Elements script = Jsoup.parse(itemInfo).getElementsByTag("script");
        String scriptStr = script.toString();
        //获取19内容
        String patternStr19 = "\"19\":(.*?)(\",)";
        Pattern pattern19 = Pattern.compile(patternStr19);
        Matcher matcher19 = pattern19.matcher(scriptStr);
        if(matcher19.find()){
            String promotions = matcher19.group().trim();
            if (!StringUtils.isEmpty(promotions)){
                promotions = promotions.substring(6, promotions.length() - 2);
                System.out.println("promotions = " + promotions);
            }
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
                Item item = new Item();
                item.setSpu(sku);
                List<Item> itemList = itemService.findAll(item);
                if (itemList.size()>0){
                    continue;
                }
                //价格
                String price = element.select("div.p-price > strong > i").text();
                //店铺
                String store = element.select("div.p-shop > span > a").text();
                //商品的详情url
                String imgUrl="https:"+element.select("img").attr("data-lazy-img");
                String itemUrl= "https://item.jd.com/"+sku+".html";
                //标题
                String itemInfo = httpUtils.doGetHtml(itemUrl,true);
                String title = Jsoup.parse(itemInfo).select("div#itemName").text();
                //促销活动
                Elements script = Jsoup.parse(itemInfo).getElementsByTag("script");
                String scriptStr = script.toString();
                //获取promov2
                /*String patternStr = "\"promov2\"(.*?)(}],\\n)";
                Pattern pattern = Pattern.compile(patternStr);
                Matcher matcher = pattern.matcher(scriptStr);
                if (matcher.find()) {
                    String promov2 = matcher.group();
                    if (!StringUtils.isEmpty(promov2)) {
                        //获取19内容
                        String patternStr19 = "\"19\":(.*?)(\",)";
                        Pattern pattern19 = Pattern.compile(patternStr19);
                        Matcher matcher19 = pattern19.matcher(promov2);
                        if(matcher19.find()){
                            String promotions = matcher19.group().trim();
                            if (!StringUtils.isEmpty(promotions)){
                                promotions = promotions.substring(6, promotions.length() - 2);
                                item.setPromotions(promotions);
                            }
                        }
                    }
                }*/

                StringBuilder stringBuilder = new StringBuilder();
                //获取19内容
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

                //获取60内容
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

                item.setTitle(title);
                item.setStore(store);//店铺
                item.setSpu(spu);//设置商品spu
                item.setSku(sku);
                item.setUrl(itemUrl);
                item.setPic(imgUrl);//商品图片
                item.setPrice(Double.valueOf(price));//价格
                item.setPromotions(stringBuilder.toString());//促销活动
                item.setCreated(new Date());
                itemService.save(item);
            }
        }
    }

}
