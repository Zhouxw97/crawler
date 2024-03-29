package com.wantwant.utils;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @Des httpclient封装工具类
 * @Author guyu
 * @Date 2020/7/10 7:23
 * @Param
 * @Return
 */
@Component
public class HttpUtils {
    private PoolingHttpClientConnectionManager cm;

    public HttpUtils() {
        this.cm = new PoolingHttpClientConnectionManager();
        //设置最大连接参数
        cm.setMaxTotal(100);
        //设置每个主机的最大连接参数
        cm.setDefaultMaxPerRoute(10);
    }

    /**
     * 根据请求地址下载页面数据
     *
     * @param url
     * @return 页面数据
     */
    public String doGetHtml(String url,Boolean isIPhone) {
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        //创建httpget对象，设置url地址
        HttpGet httpGet = new HttpGet(url);
        //模拟环境,*****摸拟浏览器浏览
        if (isIPhone){
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1");
        }else {
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:60.0) Gecko/20100101 Firefox/60.0");
        }
        //设置请求信息
        httpGet.setConfig(getConfig());

        CloseableHttpResponse response = null;

        try {
            //使用httpclient发起请求，获取响应
            response = httpClient.execute(httpGet);

            //解析响应返回结果

            if (response.getStatusLine().getStatusCode() == 200) {
                //判断响应实体是不是空的
                if (response.getEntity() != null) {
                    String content= EntityUtils.toString(response.getEntity(),"utf8");
                    return content;
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //返回空串
        return "";

    }

    /**
     * 下载图片
     *
     * @param url
     * @return 图片名称
     */
    public String doGetImage(String url) {
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        //创建httpget对象，设置url地址
        HttpGet httpGet = new HttpGet(url);
        //设置请求信息
        httpGet.setConfig(getConfig());

        CloseableHttpResponse response = null;

        try {
            //使用httpclient发起请求，获取响应
            response = httpClient.execute(httpGet);

            //解析响应返回结果

            if (response.getStatusLine().getStatusCode() == 200) {
                //判断响应实体是不是空的
                if (response.getEntity() != null) {
                    //下载图片
                    //获取图片的后缀
                    String extName = url.substring(url.lastIndexOf("."));

                    //创建图片名。重命名图片
                    String picName= UUID.randomUUID().toString()+extName;

                    //下载图片声明outputstream
                    OutputStream outputStream=new FileOutputStream(new File("src/main/resources/static/imgs/"+
                            picName));
                    response.getEntity().writeTo(outputStream);
                    //返回图片名称
                    return picName;

                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //返回空串
        return "";

    }

    /**
     * @return
     */
    private RequestConfig getConfig() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(100 * 1000)    //创建连接的最长时间
                .setConnectionRequestTimeout(100 * 1000)   //获取连接的最长时间
                .setSocketTimeout(600 * 1000)      //数据传输最长时间
                .build();
        return config;
    }
}
