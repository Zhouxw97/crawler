package com.wantwant.config;

import com.alibaba.fastjson.JSONObject;
import com.wantwant.config.exception.AuthException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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

    private Logger log = LoggerFactory.getLogger(SignatureValidationAspect.class);

    @Autowired
    private HttpServletRequest request;

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
    @Around("pointcut()")
    public Object handle(ProceedingJoinPoint point) throws Throwable {
        String sign = request.getHeader("sign");
        if (StringUtils.isEmpty(sign)){
            throw new AuthException("签名参数为空！");
        }
        SortedMap<String, String> requesBody = getRequesBody(request);
        boolean verifySign = verifySign(requesBody, sign);
        if(verifySign){
            return point.proceed();
        }else {
            throw new AuthException("签名异常，请检查！");
        }

    }

    /**
     * 获取body参数
     *
     * @param request
     * @return: java.util.SortedMap<java.lang.String,java.lang.String>
     * @author: zhouxiaowen
     * @date: 2021-05-20 19:13
     */
    private SortedMap<String,String> getRequesBody(HttpServletRequest request) throws IOException {
        //SortedMap默认ASCII排序规则的封装的接口
        SortedMap<String,String> sMap = new TreeMap();
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String str = "";
        while ((str = reader.readLine())!= null){
            sb.append(str);
        }
        sb.trimToSize();
        String bodyStr= sb.toString();
        if (StringUtils.isNotEmpty(bodyStr)){
            Map<String,String> bodyMap = JSONObject.parseObject(bodyStr, Map.class);
            for (Map.Entry entry : bodyMap.entrySet()) {
                sMap.put((String)entry.getKey(), (String)entry.getValue());
            }
        }
        return sMap;
    }

    /**
     * 验证签名，请求参数都会在这里进行排序加密
     *
     * @param params 参数
     * @param sign 签名
     * @return: boolean
     * @author: zhouxiaowen
     * @date: 2021-05-20 19:53
     */
    private boolean verifySign(SortedMap<String, String> params,String sign) {
        if (StringUtils.isEmpty(sign)) {
            return false;
        }
        // 把参数加密
        String paramsSign = getParamsSign(params);
        log.info("Param Sign : {}", paramsSign);
        return !StringUtils.isEmpty(paramsSign) && sign.equals(paramsSign);
    }

    /**
     * 参数md5加密获取签名转大写
     *
     * @param params
     * @return: java.lang.String
     * @author: zhouxiaowen
     * @date: 2021-05-20 19:21
     */
    private String getParamsSign(SortedMap<String, String> params) {
        String paramsJsonStr = JSONObject.toJSONString(params);
        return DigestUtils.md5DigestAsHex(paramsJsonStr.getBytes()).toUpperCase();
    }

}
