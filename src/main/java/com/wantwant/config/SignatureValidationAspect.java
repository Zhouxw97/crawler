package com.wantwant.config;

import com.wantwant.exception.AuthException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * SignatureValidationAspect
 *
 * @author zhouxiaowen
 * @date 2021-05-20 15:48
 * @version 1.0
 */
@Component
@Aspect
public class SignatureValidationAspect {

    /**
     * 切点
     *
     * @param
     * @return: void
     * @author: zhouxiaowen
     * @date: 2021-05-20 15:49
     */
    @Pointcut("@annotation(com.wantwant.annotation.SignatureValidation)")
    public void pointcut() {
    }

    /**
     * 鉴权
     *
     * @return: java.lang.Object
     * @author: zhouxiaowen
     * @date: 2021-05-20 15:50
     */
    @Before("pointcut()")
    public void handle() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String appId = request.getHeader("appId");
        if(!"123456".equals(appId)){
            throw new AuthException("签名异常，请检查！");
        }

    }

}
