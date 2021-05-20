package com.wantwant.interceptor;

import com.alibaba.fastjson.JSON;
import com.wantwant.config.ResponseDtoConfig;
import com.wantwant.entity.dto.framework.RestResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 进行消息返回结果处理的类
 *
 * @author zhangxianchao
 * @since 2018/9/19 0019
 */
@ControllerAdvice
@AutoConfigureAfter(ResponseDtoConfig.class)
public class ResponseAdvisor implements ResponseBodyAdvice<Object> {

    @Autowired(required = false)
    ResponseDtoConfig responseDtoConfig;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * 在真正返回结果之前，修改返回值的格式
     *
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return java.lang.Object
     * @author zhangxianchao
     * @since 2018/9/19 0019
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof RestResultResponse) {
            return body;
        }

        //判断是否存在responseConfig 的实现类，如果存在实现类，则先使用实现类进行判断
        if (responseDtoConfig != null) {
            Object resoponseObj = responseDtoConfig.sucessResponse(request, response, body, returnType);
            if (resoponseObj != null) {
                return resoponseObj;
            }
        }

        if (returnType.getMethod().getReturnType().equals(String.class)) {
            return JSON.toJSONString(new RestResultResponse<>().data(body));
        }
        if (request.getURI().toString().contains("/swagger") || request.getURI().toString().contains("/v2/api-docs")|| request.getURI().toString().contains("/actuator")) {
            return body;
        }
        return new RestResultResponse<>().data(body);
    }


}