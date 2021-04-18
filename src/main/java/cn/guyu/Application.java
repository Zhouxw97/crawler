package cn.guyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 *
 * @author zhouxiaowen
 * @date 2021-04-18 22:13
 * @version 1.0
 */
@SpringBootApplication
@EnableScheduling  //开启定时任务的注解
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
