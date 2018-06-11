package com.ljyhust;

import com.ljyhust.utils.AESUtils;
import com.ljyhust.utils.CryptoCipherUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2018/5/6.
 */
public class JunitTestMain {
    @Test
    public void testDownloadAudioFile() {
        System.err.println(">>>>> 测试 <<<<<");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://172.17.38.134:11061/mediaCommon/downloadAudioFile");
        try {
            httpPost.setHeader("Range","51200");
            CloseableHttpResponse response = httpClient.execute(httpPost);
            InputStream input = response.getEntity().getContent();
            File file = new File("E:\\data\\test_files\\GEM_test_out01.mp3");
            FileOutputStream fos = new FileOutputStream(file);
            //CryptoCipherUtils.decode(input, fos, "edBfDgOJ0VTEvhDOiO7rug==");
            CryptoCipherUtils.decodeWithBlock(input, fos, "SLe8h5yjmVzYnHNKV1K20g==", 1024, "RC4");
            if (null != fos) {
                fos.flush();
                fos.close();
            }
            if (null != input) {
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDownloadRestTemplateAudioFile() {
        System.err.println(">>>>> 测试 <<<<<");
        String url = "http://172.17.38.113:10080/ehr_files/GEM_paomo.mp3";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Range","bytes=51200-");
        try {
            ResponseEntity<byte[]> res = restTemplate.exchange(url, HttpMethod.GET, new org.springframework.http.HttpEntity<byte[]>(headers), byte[].class);
            System.err.println("test...." + res.getHeaders().toSingleValueMap());
            InputStream input = new ByteArrayInputStream(res.getBody());
            File file = new File("E:\\data\\test_files\\GEM_test_out03.mp3");
            FileOutputStream fos = new FileOutputStream(file);

            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = input.read(buff, 0, buff.length)) != -1) {
                fos.write(buff, 0, len);
            }
            //CryptoCipherUtils.decode(input, fos, "edBfDgOJ0VTEvhDOiO7rug==");
            //CryptoCipherUtils.decodeWithBlock(input, fos, "SLe8h5yjmVzYnHNKV1K20g==", 1024, "RC4");
            if (null != fos) {
                fos.flush();
                fos.close();
            }
            if (null != input) {
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetKey() {
        try {
            System.err.println(CryptoCipherUtils.generateKey("RC4"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
