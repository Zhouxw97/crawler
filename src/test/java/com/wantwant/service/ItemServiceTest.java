package com.wantwant.service;

import com.wantwant.SpringBootTest;
import com.wantwant.dao.ItemMapper;
import com.wantwant.entity.po.ItemPo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ItemServiceTest extends SpringBootTest {

    @Autowired
    private ItemMapper itemMapper;

    @Test
    public void getById(){
        ItemPo itemPo = itemMapper.selectById(390);
        log.info("itemPo:{}",itemPo);
    }
}
