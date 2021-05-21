package com.wantwant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wantwant.dao.ThemestoreDataMapper;
import com.wantwant.entity.dto.ThemestoreDataDto;
import com.wantwant.entity.po.ThemestoreDataPo;
import com.wantwant.service.ThemestoreDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* 主题门店业绩 服务实现类
*
* @author zhouxiaowen
* @since 2021-05-21
*/
@Service
public class ThemestoreDataServiceImpl extends ServiceImpl<ThemestoreDataMapper, ThemestoreDataPo> implements ThemestoreDataService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ThemestoreDataMapper themestoreDataMapper;


	/**
	 * 根据dto新增
	 *
	 * @param themestoreDataDto
	 * @return: int
	 * @author: zhouxiaowen
	 * @date: 2021-05-21 14:48
	 */
	@Override
	public int insert(ThemestoreDataDto themestoreDataDto) {
		ThemestoreDataPo po = new ThemestoreDataPo();
		BeanUtils.copyProperties(themestoreDataDto,po);
		return themestoreDataMapper.insert(po);
	}


}
