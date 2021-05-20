package com.wantwant.annotation;

import java.lang.annotation.*;

/**
 * 接口鉴权注解
 *
 * @author zhouxiaowen
 * @date 2021-05-20 15:37
 * @version 1.0
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SignatureValidation {

}
