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
        HttpPost httpPost = new HttpPost("http://172.17.38.134:11061/mediaCommon/downloadAudioFile");
        try {
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
    public void testGetKey() {
        try {
            System.err.println(CryptoCipherUtils.generateKey("RC4"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
