/**
 *
 */
package com.wantwant.config;

import com.wantwant.config.exception.AuthException;
import com.wantwant.config.exception.ServiceBizException;
import com.wantwant.entity.dto.framework.RestResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author zhangxc
 */
@Component
@AutoConfigureAfter(ResponseDtoConfig.class)
public class ExceptionControllerAdviceHandel {


    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdviceHandel.class);

    @Autowired(required = false)
    ResponseDtoConfig responseDtoConfig;

    /**
     * 异常的默认处理类
     *
     * @param req
     * @param error
     * @return org.springframework.http.ResponseEntity
     * @author zhangxianchao
     * @since 2018/7/29 0029
     */
    public ResponseEntity getDefaultExceptionResponse(HttpServletRequest req, HttpServletResponse resp, Throwable
            error) {
        //如果存在responseDtoConfigService
        if (responseDtoConfig != null) {
            ResponseEntity resoponseObj = responseDtoConfig.exceptionResponse(req, error, resp);
            if (resoponseObj != null) {
                return resoponseObj;
            }
        }

        //处理ServiceBizException
        if (error instanceof ServiceBizException) {
            return bizExceptionAdvice((ServiceBizException) error);
        }
        //处理MaxUploadSizeExceededException
        if (error instanceof MaxUploadSizeExceededException) {
            handleMaxUploadSizeException((MaxUploadSizeExceededException) error, resp);
            return null;
        }
        //处理MethodArgumentNotValidException
        if (error instanceof MethodArgumentNotValidException) {
            return methodArgumentExceptionAdvice((MethodArgumentNotValidException) error);
        }
        //处理BindException
        if (error instanceof BindException) {
            return bindException((BindException) error);
        }
        if (error instanceof AuthException) {
            return authException((AuthException) error);
        }
        return throwableAdvice((Throwable) error);
    }

    /**
     * 处理Throwable
     *
     * @param error
     * @return
     * @author zhangxc
     * @date 2016年10月25日
     */
    private ResponseEntity throwableAdvice(Throwable error) {
        logger.error("未知错误：" + error.getMessage(), error);
        return new ResponseEntity<>(new RestResultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "系统异常，可能存在同步操作，请尽快与管理员联系"), HttpStatus.OK);
    }

    /**
     * 处理ServiceBizException
     *
     * @param error
     * @return
     * @author zhangxc
     * @date 2016年10月25日
     */
    private ResponseEntity bizExceptionAdvice(ServiceBizException error) {
        if (StringUtils.isEmpty(error.getCause())) {
            logger.error("业务错误：{}", error.getMessage());
        } else {
            logger.error("业务错误：{}", error.getMessage(), error);
        }
        return new ResponseEntity<>(new RestResultResponse(HttpStatus.BAD_REQUEST.value(), error.getMessage()),
                HttpStatus.OK);
    }


    /**
     * 处理MethodArgumentNotValidException
     *
     * @param error
     * @return
     * @author zhangxc
     * @date 2016年10月25日
     */
    private ResponseEntity methodArgumentExceptionAdvice(MethodArgumentNotValidException error) {
        logger.error("数据格式不正确：{}", error.getMessage());
        StringBuilder sb = new StringBuilder();
        for (FieldError errorInfo : error.getBindingResult().getFieldErrors()) {
            appendExceptionInfo(sb, errorInfo);
        }
        String errorMsg = sb.toString();
        return new ResponseEntity<>(new RestResultResponse(HttpStatus.BAD_REQUEST.value(), errorMsg), HttpStatus.OK);
    }

    /**
     * 抽取出公共的异常处理部分
     *
     * @param sb sb
     * @param errorInfo errorInfo
     * @author renwd
     * @since 2019/3/19
     */
    private void appendExceptionInfo(StringBuilder sb, FieldError errorInfo) {
        sb.append(errorInfo.getField()).append(":").append(errorInfo.getDefaultMessage()).append("<br/>");
    }

    /**
     * 处理BindException
     *
     * @param e
     * @return org.springframework.http.ResponseEntity
     * @author renwd
     * @since 2019/3/19
     */
    private ResponseEntity bindException(BindException e) {
        logger.error("数据格式不正确：{}", e.getMessage());
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder sb = new StringBuilder();
        fieldErrors.stream().forEach(exception -> {
            appendExceptionInfo(sb, exception);
        });
        String errorMsg = sb.toString();
        return new ResponseEntity<>(new RestResultResponse(HttpStatus.BAD_REQUEST.value(), errorMsg), HttpStatus.OK);
    }

    /**
     * 处理AuthException
     *
     * @param e
     * @return: org.springframework.http.ResponseEntity
     * @author: zhouxiaowen
     * @date: 2021-05-20 17:01
     */
    private ResponseEntity authException(AuthException e) {
        logger.error("签名异常：{}", e.getMessage());
        String errorMsg = e.getMessage();
        return new ResponseEntity<>(new RestResultResponse(HttpStatus.UNAUTHORIZED.value(), errorMsg), HttpStatus.OK);
    }

    /**
     * 处理上传文件超过最大大小
     *
     * @param ex
     * @param response
     * @author zhangxc
     * @date 2016年10月25日
     */
    private void handleMaxUploadSizeException(MaxUploadSizeExceededException ex, HttpServletResponse response) {
        logger.error("上传文件大小超过最大限制：{}", ex.getMessage());
        try {
            response.sendError(HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE.value(), "文件大小不应大于" + (
                    ex).getMaxUploadSize() / 1000 + "kb");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
