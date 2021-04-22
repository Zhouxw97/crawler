package com.wantwant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wantwant.dao.JdItemMapper;
import com.wantwant.entity.po.JdItemPo;
import com.wantwant.entity.vo.JdItemVo;
import com.wantwant.service.JdItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

	/**
	 * 根据sku获取商品品类
	 *
	 * @param sku
	 * @return: java.util.List<com.wantwant.entity.vo.JdItemVo>
	 * @author: zhouxiaowen
	 * @date: 2021-04-22 20:12
	 */
	@Override
	public List<JdItemVo> getItemBySku(String sku) {
		List<JdItemPo> jdItemPos = jdItemMapper.selectList(new LambdaQueryWrapper<JdItemPo>().eq(JdItemPo::getSku,sku));
		List<JdItemVo> vos = jdItemPos.stream().map(p -> {
			JdItemVo v = new JdItemVo();
			BeanUtils.copyProperties(p, v);
			return v;
		}).collect(Collectors.toList());
		return vos;
	}

	/**
	 * 保存商品品类
	 *
	 * @param jdItemPo
	 * @return: int
	 * @author: zhouxiaowen
	 * @date: 2021-04-22 20:15
	 */
	@Override
	public int saveJdItem(JdItemPo jdItemPo){
		return jdItemMapper.insert(jdItemPo);
	}

}
