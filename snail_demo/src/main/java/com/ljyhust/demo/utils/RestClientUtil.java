package com.ljyhust.demo.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * RestTemplate请求类封装
 */
public class RestClientUtil {

    private RestClientUtil() {}

    private static RestTemplate restClient;

    static {
        restClient = RestTemplateConfiguration.restTemplate();
    }

    public static RestTemplate getRestTemplate() {
        return restClient;
    }

    public static JSONObject getRequestForData(String url, Map<String, Object> params) {
        JSONObject resJson = restClient.getForObject(url, JSONObject.class, params);
        return resJson;
    }

    /**
     * 表单请求
     * @return
     */
    public static JSONObject postFormData(String url, Map<String, Object> params) {
        JSONObject body = new JSONObject();
        // 请求
        HttpHeaders headers = new HttpHeaders();
        //组装参数
        MultiValueMap<String, Object> reqParams = new LinkedMultiValueMap<String, Object>();
        Set<Map.Entry<String, Object>> entrySet = params.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            reqParams.add(entry.getKey(), entry.getValue());
        }
        org.springframework.http.HttpEntity<MultiValueMap<String, Object>> entity =
                new org.springframework.http.HttpEntity<MultiValueMap<String, Object>>(reqParams, headers);
        ResponseEntity<JSONObject> upstreamRes = restClient.exchange(url, HttpMethod.POST, entity, JSONObject.class);
        return upstreamRes.getBody();

    }

    public static JSONObject postForApplicationJson(String url, Map<String, Object> params) {
        return restClient.postForObject(url, params,
                JSONObject.class);
    }

    /**
     * 配置类：内部静态类
     */
    static class RestTemplateConfiguration {

        /**
         * 单例创建restTemplate
         * @return
         */
        private static RestTemplate restTemplate() {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setRequestFactory(clientHttpRequestFactory());
            // 使用 utf-8 编码集的 conver 替换默认的 conver（默认的 string conver 的编码集为"ISO-8859-1"）
            List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
            Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
            while (iterator.hasNext()) {
                HttpMessageConverter<?> converter = iterator.next();
                if (converter instanceof StringHttpMessageConverter) {
                    iterator.remove();
                }
            }
            messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
            return restTemplate;
        }

        private static HttpClient httpClient() {

            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", SSLConnectionSocketFactory.getSocketFactory())
                    .build();
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
            connectionManager.setMaxTotal(1000);
            connectionManager.setDefaultMaxPerRoute(20);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(60000) //服务器返回数据(response)的时间，超过抛出read timeout
                    .setConnectTimeout(60000) //连接上服务器(握手成功)的时间，超出抛出connect timeout
                    .setConnectionRequestTimeout(10000)//从连接池中获取连接的超时时间，超时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
                    .build();
            return HttpClientBuilder.create()
                    .setDefaultRequestConfig(requestConfig)
                    .setConnectionManager(connectionManager)
                    .build();
        }

        private static ClientHttpRequestFactory clientHttpRequestFactory() {
            HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient());
            return clientHttpRequestFactory;
        }
    }
}
