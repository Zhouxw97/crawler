package com.wantwant.config;

import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Resopnse 返回结果配置Service
 *
 * @author: zhangxianchao
 * @since: 2018/9/18 0018
 * @updateDate:
 * @updateRemark:
 * @version:1.0 Copyright: Copyright (c) 2018
 */
public interface ResponseDtoConfig {

    /**
     * 当处理成功后的配置
     *
     * @param request
     * @param response
     * @param body
     * @param returnType
     * @return java.lang.Object
     * @author zhangxianchao
     * @since 2018/9/18 0018
     */
    Object sucessResponse(ServerHttpRequest request,
                          ServerHttpResponse response, Object body,
                          MethodParameter returnType);

    /**
     * 当处理异常时的返回值
     *
     * @param req
     * @param error
     * @return java.lang.Object
     * @author zhangxianchao
     * @since 2018/9/18 0018
     */
    ResponseEntity exceptionResponse(HttpServletRequest req, Throwable error, HttpServletResponse response);
}
