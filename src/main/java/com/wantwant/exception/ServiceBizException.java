/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : cmol.common.function
*
* @File name : ServiceBizException.java
*
* @Author : 
*
* @Date : 2016年2月24日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年2月24日                                   1.0
*
*
*
*
----------------------------------------------------------------------------------
*/


package com.wantwant.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Service层异常统一封装的业务异常，一般无法直接处理
 * ServiceBizException
 *
 * @author zhangxianchao
 */
public class ServiceBizException extends RuntimeException {

    private final transient List exceptionDataList = new ArrayList();

    private ServiceBizException() {
    }

    public ServiceBizException(Exception e) {
        super(e);
    }

    public ServiceBizException(String msg) {
        super(msg);
    }

    public ServiceBizException(String msg, Object exceptionData) {
        super(msg);
        exceptionDataList.add(exceptionData);
    }

    public ServiceBizException(String msg, Exception e) {
        super(msg, e);
    }

    public Object getExceptionData() {
        return exceptionDataList.get(0);
    }
}
