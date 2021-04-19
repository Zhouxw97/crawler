package com.wantwant.service;

import com.wantwant.pojo.Item;

import java.util.List;

public interface ItemService {

    /**
     * 保存商品
     *
     * @param item
     * @return: void
     * @author: zhouxiaowen
     * @date: 2021-04-19 10:21
     */
    public void save(Item item);

    /**
     * 根据条件查询物品
     *
     * @param item
     * @return: java.util.List<Item>
     * @author: zhouxiaowen
     * @date: 2021-04-19 10:21
     */
    public List<Item> findAll(Item item);
}
