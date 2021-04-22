package com.wantwant.service;

import com.wantwant.entity.po.JdItemPo;
import com.wantwant.entity.vo.JdItemVo;

import java.util.List;

/**
 * 京东商品品类信息 service
 *
 * @author zhouxiaowen
 * @date 2021-04-22 19:53
 * @version 1.0
 */
public interface JdItemService{

    /**
     * 根据sku获取商品品类
     *
     * @param sku
     * @return: java.util.List<com.wantwant.entity.vo.JdItemVo>
     * @author: zhouxiaowen
     * @date: 2021-04-22 20:12
     */
    List<JdItemVo> getItemBySku(String sku);

    /**
     * 保存商品品类
     *
     * @param jdItemPo
     * @return: int
     * @author: zhouxiaowen
     * @date: 2021-04-22 20:15
     */
    int saveJdItem(JdItemPo jdItemPo);

}
