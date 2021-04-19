# crawler

```java
private void parse(String html) throws JsonProcessingException {
        //1.解析html
        Document document = Jsoup.parse(html);
        //获取spu
        Elements elements = document.select("div#J_goodsList >ul >li");
//

        for (Element element : elements) {

            //获取商品的spu
            Long spu = StatusEnum.NOT_EXIST.getType();
            String spuStr = element.attr("data-spu");
            if (spuStr != "") {
                spu = Long.parseLong(spuStr);
            }

            //获取sku
            Elements elSkus = element.select("li.ps-item");
            for (Element skus : elSkus) {
                Long sku = Long.parseLong(skus.select("img").attr("data-sku"));

                //根据sku查询数据库中有没有这个商品有的话，跳过
                Item item = new Item();
                item.setSku(sku);
                List<Item> itemList = itemService.findAll(item);
                if (itemList.size()>0){
                    continue;
                }
                //设置商品的spu
                item.setSpu(spu);
                //设置商品的详情url
                String itemUrl= "https://item.jd.com/"+sku+".html";
                item.setUrl(itemUrl);
                //设置商品图片
                String imgUrl="https:"+skus.select("img").attr("data-lazy-img");
                String imgName=httpUtils.doGetImage(imgUrl);
                item.setPic(imgName);
                //设置商品价格
                String priceJson = httpUtils.doGetHtml("https://p.3.cn/prices/mgets?skuIds=J_" + sku);
                double price = MAPPER.readTree(priceJson).get(0).get("p").asDouble();
                item.setPrice(price);
                //设置商品的标题
                String itemInfo = httpUtils.doGetHtml(item.getUrl());
                String title = Jsoup.parse(itemInfo).select("div.sku-name").text();
                item.setTitle(title);

                //设置商品爬取时间
                item.setCreated(new Date());
                item.setUpdated(item.getCreated());

                itemService.save(item);

            }
        }
    }

```