package com.wantwant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动类
 *
 * @author zhouxiaowen
 * @date 2021-04-18 22:13
 * @version 1.0
 */
@SpringBootApplication
@EnableScheduling  //开启定时任务的注解
@EnableTransactionManagement
@EnableSwagger2
@ComponentScan(basePackages = "com.wantwant")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}