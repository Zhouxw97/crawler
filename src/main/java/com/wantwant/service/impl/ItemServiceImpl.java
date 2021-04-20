package com.wantwant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wantwant.dao.ItemMapper;
import com.wantwant.entity.po.ItemPo;
import com.wantwant.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author zhouxiaowen
 * @date 2021-04-20 10:55
 * @version 1.0
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, ItemPo> implements ItemService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ItemMapper itemMapper;

}
