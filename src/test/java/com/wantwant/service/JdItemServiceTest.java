package com.wantwant.service;

import com.wantwant.SpringBootTest;
import com.wantwant.entity.po.JdItemPo;
import com.wantwant.entity.vo.JdItemVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class JdItemServiceTest extends SpringBootTest {

    @Autowired
    private JdItemService jdItemService;

    @Test
    public void saveJdItemTest(){
        JdItemPo jdItemPo = new JdItemPo();
        jdItemPo.setSpu("123");
        jdItemPo.setSku("456");
        jdItemPo.setTitle("title");
        jdItemPo.setCurrentPrice("66");
        jdItemPo.setGuidePrice("88");
        jdItemPo.setStore("店铺");
        jdItemPo.setPromotions("活动");
        jdItemPo.setCoupon("coupon");
        jdItemPo.setPic("pic");
        jdItemPo.setUrl("url");
        jdItemPo.setCreated(LocalDateTime.now());
        jdItemService.saveJdItem(jdItemPo);

    }

    @Test
    public void getItemBySkuTest(){
        List<JdItemVo> itemBySku = jdItemService.getItemBySku("456");
        log.info("itemBySku:{}",itemBySku);
    }
}
