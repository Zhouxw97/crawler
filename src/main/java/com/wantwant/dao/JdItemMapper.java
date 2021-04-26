package com.wantwant.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wantwant.entity.dto.JdItemQueryDto;
import com.wantwant.entity.po.JdItemPo;
import com.wantwant.entity.vo.JdItemVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 京东商品品类信息 Mapper
 *
 * @author zhouxiaowen
 * @date 2021-04-22 19:52
 * @version 1.0
 */
public interface JdItemMapper extends BaseMapper<JdItemPo> {

    /**
     * 分页查询数据mapper
     *
     * @param page
     * @param jdItemQueryDto
     * @return: java.util.List<com.wantwant.entity.vo.JdItemVo>
     * @author: zhouxiaowen
     * @date: 2021-04-26 14:23
     */
    List<JdItemVo> selectPageBySql(Page<JdItemVo> page, @Param("params") JdItemQueryDto jdItemQueryDto);
}
