package com.wantwant.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Druid config
 *
 * @author zhouxiaowen
 * @date 2021-04-20 11:14
 * @version 1.0
 */
@Configuration
@Import({DruidDataSourceAutoConfigure.class})
public class DruidDataSourceConfig {}
