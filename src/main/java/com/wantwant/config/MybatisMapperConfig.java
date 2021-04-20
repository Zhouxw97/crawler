package com.wantwant.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis 扫描 mybatis
 *
 * @author zhouxiaowen
 * @date 2021-04-20 11:15
 * @version 1.0
 */
@Configuration
@MapperScan("com.wantwant.dao")
public class MybatisMapperConfig {

}
