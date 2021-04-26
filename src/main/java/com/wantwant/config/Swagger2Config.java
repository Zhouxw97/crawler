package com.wantwant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swagger2Config {

    @Bean
    public Docket getDocket(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(this.apiInfo()).select().build();
    }

    private ApiInfo apiInfo() {
        return (new ApiInfoBuilder()).title("商品信息爬虫").description("简单优雅的restfun风格，http://blog.csdn.net/saytime").termsOfServiceUrl("http://blog.csdn.net/saytime").version("1.0").build();
    }
}
