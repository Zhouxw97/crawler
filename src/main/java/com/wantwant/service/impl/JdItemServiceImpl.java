package com.wantwant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wantwant.dao.JdItemMapper;
import com.wantwant.entity.po.JdItemPo;
import com.wantwant.service.JdItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 京东商品品类信息impl
 *
 * @author zhouxiaowen
 * @date 2021-04-22 19:54
 * @version 1.0
 */
@Service
public class JdItemServiceImpl extends ServiceImpl<JdItemMapper, JdItemPo> implements JdItemService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	JdItemMapper jdItemMapper;


}
