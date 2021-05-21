/**
 *
 */
package com.wantwant.config.interceptor;

import com.wantwant.config.ExceptionControllerAdviceHandel;
import com.wantwant.config.exception.AuthException;
import com.wantwant.config.exception.ServiceBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangxc
 */
@ControllerAdvice
@AutoConfigureAfter(ExceptionControllerAdviceHandel.class)
public class ExceptionControllerAdvice {

    @Autowired(required = false)
    ExceptionControllerAdviceHandel exceptionControllerAdviceHandel;

    /**
     * 处理Throwable
     *
     * @param error
     * @return
     * @author zhangxc
     * @date 2016年10月25日
     */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity throwableAdvice(HttpServletRequest req, Throwable error) {
        return exceptionControllerAdviceHandel.getDefaultExceptionResponse(req, null, error);
    }

    /**
     * 处理ServiceBizException
     *
     * @param error
     * @return
     * @author zhangxc
     * @date 2016年10月25日
     */
    @ExceptionHandler(ServiceBizException.class)
    @ResponseBody
    public ResponseEntity bizExceptionAdvice(HttpServletRequest req, ServiceBizException error) {
        return exceptionControllerAdviceHandel.getDefaultExceptionResponse(req, null, error);
    }

    /**
     * 处理MethodArgumentNotValidException
     *
     * @param error
     * @return
     * @author zhangxc
     * @date 2016年10月25日
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity authExceptionAdvice(HttpServletRequest req, MethodArgumentNotValidException error) {
        return exceptionControllerAdviceHandel.getDefaultExceptionResponse(req, null, error);
    }

    /**
     * 处理BindException
     *
     * @param error
     * @return
     * @author zhangxc
     * @date 2016年10月25日
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity bindExceptionAdvice(HttpServletRequest req, BindException error) {
        return exceptionControllerAdviceHandel.getDefaultExceptionResponse(req, null, error);
    }

    /**
     * 处理上传文件超过最大大小
     *
     * @param ex
     * @param response
     * @author zhangxc
     * @date 2016年10月25日
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public void handleMaxUploadSizeException(MaxUploadSizeExceededException ex, HttpServletResponse response) {
        exceptionControllerAdviceHandel.getDefaultExceptionResponse(null, null, ex);
    }

    /**
     * 处理AuthException
     *
     * @param error
     * @return
     * @author zhangxc
     * @date 2016年10月25日
     */
    @ExceptionHandler(AuthException.class)
    @ResponseBody
    public ResponseEntity signatureExceptionAdvice(HttpServletRequest req, AuthException error) {
        return exceptionControllerAdviceHandel.getDefaultExceptionResponse(req, null, error);
    }
}
