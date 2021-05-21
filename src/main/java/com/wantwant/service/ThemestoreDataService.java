package com.wantwant.service;

import com.wantwant.entity.dto.ThemestoreDataDto;

/**
 * 主题门店业绩 服务类
 *
 * @author zhouxiaowen
 * @since 2021-05-21
 */
public interface ThemestoreDataService {

    /**
     * 新增
     *
     * @param themestoreDataDto
     * @return: int
     * @author: zhouxiaowen
     * @date: 2021-05-21 14:48
     */
    int insert(ThemestoreDataDto themestoreDataDto);
}
