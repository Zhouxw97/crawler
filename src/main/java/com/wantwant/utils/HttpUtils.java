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
 * httpclient封装工具类
 *
 * @author zhouxiaowen
 * @date 2021-04-21 00:08
 * @version 1.0
 */
@Component
public class HttpUtils {

    //连接池管理器
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
     * @param isIPhone
     * @return: java.lang.String
     * @author: zhouxiaowen
     * @date: 2021-04-21 00:10
     */
    public String doGetHtml(String url,Boolean isIPhone) {
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        //创建httpget对象，设置url地址
        HttpGet httpGet = new HttpGet(url);
        //模拟环境,*****摸拟浏览器浏览
        if (isIPhone){
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1");
            //httpGet.setHeader("cookie","__jdu=1617152923322852786013; shshshfpa=07c39f72-1e50-4916-e76f-75a80aa2fefc-1617152925; shshshfpb=kmhtcVhIFVrYccQ7r1aehRQ==; areaId=2; ipLoc-djd=2-2815-51975-0; user-key=3134f006-b838-4e45-b56a-89d1dc66f06a; PCSYCityID=CN_310000_310100_310105; unpl=V2_ZzNtbUJRRBwgW0FSfkkJBWIKGg5KX0UdcwFPVCtNDldiChRaclRCFnUUR1FnGl0UZwcZWEBcQxdFCEdkfh1eAmUzIlxyVEMlfThGU3sfXAFkCxFfQFRAHHELQF18GFwHVzMVbXIMFntwD0AHKxkMBWcAQApKZ0MQdwhBVX4bXw1XAiJdR1JLFHUKR1dyKRdrZk4SWkJRQxF2AEVWeRpfDGMAFFRFVkMXRQl2Vw==; __jdv=76161171|www.infinitynewtab.com|t_45363_|tuiguang|1768db674ad1489b99797881aecc4876|1618904594115; __jdc=122270672; wlfstk_smdl=zmkbxvnvq5llbr4bsnlem9osfhhzoufn; TrackID=13EYt4L9GYP6wJegFuvwe3p4a8llJwRgyQ-crvEWyU6ITnl-kfv0pTgBFxrLNBcjIINnTIeEI0FZixbB0e4YFCNwSJiXQ-eHMkSKPUuDCObg7JwVLe7zmKcNevLek1feC; pinId=8JRXEd57cBprhqGA5s4-bLV9-x-f3wj7; pin=jd_6b477358395c6; unick=sdfffdewfb; ceshi3.com=103; _tp=eOjJ0E/fDZVstItSzoez0saijcvCijfzgLMS7FxuJLE=; _pst=jd_6b477358395c6; wxa_level=1; retina=1; cid=9; jxsid=16189054286105613611; webp=1; mba_muid=1617152923322852786013; visitkey=39731785739053686; __jda=122270672.1617152923322852786013.1617152923.1618903452.1618905429.28; jxsid_s_u=https://item.m.jd.com/product/100012361510.html; sc_width=414; equipmentId=7Q4C2Z5Q7UQDQKQ4BGMOQU35WPCPZOSCLV5ZYOGJ4QD662DUMR7RHTBNHFMPBP4HSNKBF5B5KZ36S7N4DATC2UEI74; sk_history=100012361510,; wq_ug=11; wq_prior=1; fingerprint=5f35f923a7cebbb7a448d2bafc285c8e; deviceVersion=90.0.4430.72; deviceOS=; deviceOSVersion=; deviceName=Chrome; wq_logid=1618905591.1419793804; wqmnx1=MDEyNjM1MXQvbWQvdTAzMGw3NTVNbDBjc25NUzA3cGI1NlRsRylvMDQyYTMxRmZhYUI0UUVTKUYpSA==; mba_sid=16189054288637063712009841987.4; __wga=1618905593992.1618905429614.1618905429614.1618905429614.3.1; PPRD_P=UUID.1617152923322852786013-LOGID.1618905594015.2119371626; jxsid_s_t=1618905594104; __jdb=122270672.30.1617152923322852786013|28.1618905429; shshshfp=b7101337e4481188e35b3236cacf9b74; shshshsID=674a053b1189f1eaa7825493b7b170ee_12_1618905612857; thor=0F9D0EB884B4B244023E90E769EA48D0B3022336B5C3FAA2AA55B07F679481C3B28C5F2F39932422D719338FB365F464D56DA71AEFC500CE1B8B59BB016FA4DBD2A6968CCC6E6040824229F2971D6C308246C081929B8CE625AE08600C267CF5447DE04D2692499784A15195C564FEFBCBB8843098AFF29F258BFD7352DA82F0E18D2AE7EB2AC723D7A5DE1EA3E958A2DA98DB9DA6D6578D33B8E6A1A0C2AD9A; 3AB9D23F7A4B3C9B=7Q4C2Z5Q7UQDQKQ4BGMOQU35WPCPZOSCLV5ZYOGJ4QD662DUMR7RHTBNHFMPBP4HSNKBF5B5KZ36S7N4DATC2UEI74; RT=\"z=1&dm=jd.com&si=gohiv67t00j&ss=knpq1otf&sl=6&tt=0&obo=6&ld=sq90&r=6629ab9caab00f25021a9f436178818b&ul=sq97\"");
        }else {
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:60.0) Gecko/20100101 Firefox/60.0");
            //httpGet.setHeader("cookie","__jdu=1617152923322852786013; shshshfpa=07c39f72-1e50-4916-e76f-75a80aa2fefc-1617152925; shshshfpb=kmhtcVhIFVrYccQ7r1aehRQ==; areaId=2; ipLoc-djd=2-2815-51975-0; user-key=3134f006-b838-4e45-b56a-89d1dc66f06a; PCSYCityID=CN_310000_310100_310105; unpl=V2_ZzNtbUJRRBwgW0FSfkkJBWIKGg5KX0UdcwFPVCtNDldiChRaclRCFnUUR1FnGl0UZwcZWEBcQxdFCEdkfh1eAmUzIlxyVEMlfThGU3sfXAFkCxFfQFRAHHELQF18GFwHVzMVbXIMFntwD0AHKxkMBWcAQApKZ0MQdwhBVX4bXw1XAiJdR1JLFHUKR1dyKRdrZk4SWkJRQxF2AEVWeRpfDGMAFFRFVkMXRQl2Vw==; __jdv=76161171|www.infinitynewtab.com|t_45363_|tuiguang|1768db674ad1489b99797881aecc4876|1618904594115; __jdc=122270672; wlfstk_smdl=zmkbxvnvq5llbr4bsnlem9osfhhzoufn; TrackID=13EYt4L9GYP6wJegFuvwe3p4a8llJwRgyQ-crvEWyU6ITnl-kfv0pTgBFxrLNBcjIINnTIeEI0FZixbB0e4YFCNwSJiXQ-eHMkSKPUuDCObg7JwVLe7zmKcNevLek1feC; pinId=8JRXEd57cBprhqGA5s4-bLV9-x-f3wj7; pin=jd_6b477358395c6; unick=sdfffdewfb; ceshi3.com=103; _tp=eOjJ0E/fDZVstItSzoez0saijcvCijfzgLMS7FxuJLE=; _pst=jd_6b477358395c6; wxa_level=1; retina=1; cid=9; jxsid=16189054286105613611; webp=1; mba_muid=1617152923322852786013; visitkey=39731785739053686; __jda=122270672.1617152923322852786013.1617152923.1618903452.1618905429.28; jxsid_s_u=https://item.m.jd.com/product/100012361510.html; sc_width=414; equipmentId=7Q4C2Z5Q7UQDQKQ4BGMOQU35WPCPZOSCLV5ZYOGJ4QD662DUMR7RHTBNHFMPBP4HSNKBF5B5KZ36S7N4DATC2UEI74; sk_history=100012361510,; wq_ug=11; wq_prior=1; fingerprint=5f35f923a7cebbb7a448d2bafc285c8e; deviceVersion=90.0.4430.72; deviceOS=; deviceOSVersion=; deviceName=Chrome; wq_logid=1618905591.1419793804; wqmnx1=MDEyNjM1MXQvbWQvdTAzMGw3NTVNbDBjc25NUzA3cGI1NlRsRylvMDQyYTMxRmZhYUI0UUVTKUYpSA==; mba_sid=16189054288637063712009841987.4; __wga=1618905593992.1618905429614.1618905429614.1618905429614.3.1; PPRD_P=UUID.1617152923322852786013-LOGID.1618905594015.2119371626; jxsid_s_t=1618905594104; __jdb=122270672.30.1617152923322852786013|28.1618905429; shshshfp=b7101337e4481188e35b3236cacf9b74; shshshsID=674a053b1189f1eaa7825493b7b170ee_12_1618905612857; thor=0F9D0EB884B4B244023E90E769EA48D0B3022336B5C3FAA2AA55B07F679481C3B28C5F2F39932422D719338FB365F464D56DA71AEFC500CE1B8B59BB016FA4DBD2A6968CCC6E6040824229F2971D6C308246C081929B8CE625AE08600C267CF5447DE04D2692499784A15195C564FEFBCBB8843098AFF29F258BFD7352DA82F0E18D2AE7EB2AC723D7A5DE1EA3E958A2DA98DB9DA6D6578D33B8E6A1A0C2AD9A; 3AB9D23F7A4B3C9B=7Q4C2Z5Q7UQDQKQ4BGMOQU35WPCPZOSCLV5ZYOGJ4QD662DUMR7RHTBNHFMPBP4HSNKBF5B5KZ36S7N4DATC2UEI74; RT=\"z=1&dm=jd.com&si=gohiv67t00j&ss=knpq1otf&sl=6&tt=0&obo=6&ld=sq90&r=6629ab9caab00f25021a9f436178818b&ul=sq97\"");
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
                .setSocketTimeout(600* 1000)      //数据传输最长时间
                .build();
        return config;
    }
}
