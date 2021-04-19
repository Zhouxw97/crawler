package com.wantwant.task;

import com.wantwant.enums.StatusEnum;
import com.wantwant.pojo.Item;
import com.wantwant.service.ItemService;
import com.wantwant.utils.HttpUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

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
    private static final ObjectMapper MAPPER = new ObjectMapper();

    //当下载任务完成后，间隔多长时间进行下次任务。
    //@Scheduled(fixedDelay = 100 * 1000)
    public void itemTask() throws JsonProcessingException {
        //1.声明地址
        String url = "https://search.jd.com/Search?keyword=%E6%97%BA%E6%97%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&suggest=1.def.0.V18--12s0%2C20s0%2C38s0%2C97s0&wq=shouji&s=122&click=0&page=";
        //2.遍历页面
        for (int i = 1; i < 5; i = i + 2) {
            String html = httpUtils.doGetHtml(url + i);
            //解析页面，获取商品数据并存储
            parse(html);
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

                //商品的详情url
                String itemUrl= "https://item.jd.com/"+sku+".html";
                String imgUrl="https:"+element.select("img").attr("data-lazy-img");
                //价格
                String price = element.select("div.p-price > strong > i").text();
                //标题
                String itemInfo = httpUtils.doGetHtml(itemUrl);
                String title = Jsoup.parse(itemInfo).select("div.sku-name").text();
                System.out.println("title = " + title);
                item.setTitle(title);
                item.setSpu(spu);//设置商品spu
                item.setSku(sku);
                item.setUrl(itemUrl);
                item.setPic(imgUrl);//商品图片
                item.setPrice(Double.valueOf(price));//价格
                item.setCreated(new Date());
                item.setUpdated(new Date());
                itemService.save(item);
            }
        }
    }

}
