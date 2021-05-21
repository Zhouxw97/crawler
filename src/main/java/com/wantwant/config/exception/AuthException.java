package com.wantwant.config.exception;



/**
 * 签名异常
 *
 * @author zhouxiaowen
 * @date 2021-05-20 18:20
 * @version 1.0
 */
public class AuthException extends RuntimeException {

    private static final long serialVersionUID = -7638041501183925225L;


    public AuthException(String msg) {
        super(msg);
    }

    public AuthException(String msg, Exception e) {
        super(msg, e);
    }

}
