package com.ljyhust;

import com.ljyhust.utils.AESUtils;
import com.ljyhust.utils.CryptoCipherUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2018/5/6.
 */
public class JunitTestMain {
    @Test
    public void testDownloadAudioFile() {
        System.err.println(">>>>> 测试 <<<<<");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:11061/mediaCommon/downloadAudioFile");
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            InputStream input = response.getEntity().getContent();
            File file = new File("E:\\data\\test_file\\output01.mp4");
            FileOutputStream fos = new FileOutputStream(file);
            CryptoCipherUtils.decode(input, fos, "edBfDgOJ0VTEvhDOiO7rug==");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetKey() {
        try {
            System.err.println(CryptoCipherUtils.generateKey("AES"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
