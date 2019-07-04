package com.ljyhust;

import com.alibaba.fastjson.JSONObject;
import com.ljyhust.utils.CryptoCipherUtils;
import com.ljyhust.utils.MD5Utils;
import com.ljyhust.utils.SecurityUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.tomcat.util.security.MD5Encoder;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Test
    public void testSendPost() {
        JSONObject data = new JSONObject();
        Map<String, Object> reqParams = new HashMap<>();
        data.put("account", String.valueOf("15261784632"));
        data.put("accountType", "1");
        data.put("pageIndex", 1);
        data.put("keyword", "lqc");
        String reqData = SecurityUtil.getEncryptDataForParam(data, "1.1.5").toString();
        reqParams.put("data", reqData);
        reqParams.put("token", SecurityUtil.md5(reqData));
        reqParams.put("version", "1.1.5");
        JSONObject resJson = getResFromHttpRestTemplate("http://172.16.140.75:10013/mobile-web/appImEmployeeBook/searchEmployeeList.do", reqParams);
        System.err.println(resJson);
        if (!"1".equals(String.valueOf(resJson.get("flag")))) {
            return;
        }
        Object resultMessage = resJson.get("resultMessage");
        String resStr = SecurityUtil.getDecryptDataForParam(resultMessage.toString(), "1.1.5", "imSearch");

        System.err.println(resStr);
    }

    @Test
    public void testSendHonorPost() {
        JSONObject data = new JSONObject();
        Map<String, Object> reqParams = new HashMap<>();
        data.put("queryEmployeeId263", "hemeiyi");
        data.put("pageIndex", 1);
        data.put("keyword", "huangyaqing");
        String reqData = SecurityUtil.getEncryptDataForParam(data, "1.1.5").toString();
        reqParams.put("data", reqData);
        reqParams.put("token", SecurityUtil.md5(reqData));
        reqParams.put("version", "1.1.5");
        JSONObject resJson = getResFromHttpRestTemplate("http://localhost:11085/mobile-web/appImEmployeeBook/getEmployeeHonorInfo.do", reqParams);
        System.err.println(resJson);
        if (!"1".equals(String.valueOf(resJson.get("flag")))) {
            return;
        }
        Object resultMessage = resJson.get("resultMessage");
        String resStr = SecurityUtil.getDecryptDataForParam(resultMessage.toString(), "1.1.5", "imSearch");

        System.err.println(resStr);
    }

    @Test
    public void testGetPersonUrl() {
        JSONObject data = new JSONObject();
        Map<String, Object> reqParams = new HashMap<>();
        /*data.put("account", String.valueOf("15261784632"));
        data.put("accountType", "1");
        data.put("pageIndex", 1);
        data.put("keyword", "黄亚庆");*/
        data.put("mobiles", "13419508920,\"456421\"");
        //String reqData = SecurityUtil.getEncryptDataForParam(data, "1.1.5").toString();
        reqParams.put("data", data);
        JSONObject resJson = getResFromHttpRestTemplate("http://localhost:8080/community/user/getPersonImgsByUserMobiles", reqParams);
        System.err.println(resJson);
        if (!"1".equals(String.valueOf(resJson.get("flag")))) {
            return;
        }
        Object resultMessage = resJson.get("resultMessage");
        String resStr = SecurityUtil.getDecryptDataForParam(resultMessage.toString(), "1.1.5", "imSearch");

        System.err.println(resStr);
    }

    @Test
    public void testSendDataMark() {
        JSONObject data = new JSONObject();
        Map<String, Object> reqParams = new HashMap<>();
        //data.put("account", String.valueOf("15261784632"));
        //data.put("accountType", "1");
        //data.put("keyword", "黄亚庆");
        data.put("event", "countRecordPlayTime");
        data.put("creator", "test-jinyang");
        data.put("data", "{\\\"callId\\\":\\\"1807112147011495000104580008a2e8\\\",\\\"imgUrl\\\":\\\"\\\",\\\"callDuration\\\":837,\\\"opportunityId\\\":0,\\\"fileName\\\":\\\"徐浩南_null_20180711_214703\\\",\\\"signTime\\\":\\\"\\\",\\\"totalTime\\\":\\\"Optional(398.45098673299998)\\\",\\\"chineseName\\\":\\\"徐浩南\\\",\\\"userName\\\":\\\"xuhaonan\\\",\\\"collect\\\":0,\\\"callTime\\\":\\\"2018-07-11 21:47:03\\\"}");
        //data.put("data", "{\\\"callId\\\":\\\"1807112147011495000104580008a2e8\\\",\\\"imgUrl\\\":\\\"\\\",\\\"callDuration\\\":837,\\\"opportunityId\\\":0,\\\"fileName\\\":\\\"徐浩南_null_20180711_214703\\\",\\\"signTime\\\":\\\"\\\",\\\"totalTime\\\":\\\"nil\\\",\\\"chineseName\\\":\\\"徐浩南\\\",\\\"userName\\\":\\\"xuhaonan\\\",\\\"collect\\\":0,\\\"callTime\\\":\\\"2018-07-11 21:47:03\\\"}");
        String reqData = SecurityUtil.getEncryptDataForParam(data, "1.1.5").toString();
        reqParams.put("data", reqData);
        reqParams.put("token", SecurityUtil.md5(reqData));
        reqParams.put("version", "1.1.5");
        JSONObject resJson = getResFromHttpRestTemplate("http://localhost:10093/mobile-web/callRecordAudio/dataMark.do", reqParams);
        System.err.println(resJson);
        if (!"1".equals(String.valueOf(resJson.get("flag")))) {
            return;
        }
        Object resultMessage = resJson.get("resultMessage");
        String resStr = SecurityUtil.getDecryptDataForParam(resultMessage.toString(), "1.1.5", "imSearch");

        System.err.println(resStr);
    }

    @Test
    public void testJson() {
        //JSONObject jsonObject = JSONObject.parseObject("{\\\"callId\\\":\\\"1807112147011495000104580008a2e8\\\",\\\"imgUrl\\\":\\\"\\\",\\\"callDuration\\\":837,\\\"opportunityId\\\":0,\\\"fileName\\\":\\\"徐浩南_null_20180711_214703\\\",\\\"signTime\\\":\\\"\\\",\\\"totalTime\\\":\\\"Optional(398.45098673299998)\\\",\\\"chineseName\\\":\\\"徐浩南\\\",\\\"userName\\\":\\\"xuhaonan\\\",\\\"collect\\\":0,\\\"callTime\\\":\\\"2018-07-11 21:47:03\\\"}");
        //String s = StringEscapeUtils.unescapeJavaScript("{\\\"callId\\\":\\\"1807112147011495000104580008a2e8\\\",\\\"imgUrl\\\":\\\"\\\",\\\"callDuration\\\":837,\\\"opportunityId\\\":0,\\\"fileName\\\":\\\"徐浩南_null_20180711_214703\\\",\\\"signTime\\\":\\\"\\\",\\\"totalTime\\\":\\\"Optional(398.45098673299998)\\\",\\\"chineseName\\\":\\\"徐浩南\\\",\\\"userName\\\":\\\"xuhaonan\\\",\\\"collect\\\":0,\\\"callTime\\\":\\\"2018-07-11 21:47:03\\\"}");
        String s = StringEscapeUtils.unescapeJavaScript("{\\\"callId\\\":\\\"1807112147011495000104580008a2e8\\\",\\\"imgUrl\\\":\\\"\\\",\\\"callDuration\\\":837,\\\"opportunityId\\\":0,\\\"fileName\\\":\\\"徐浩南_null_20180711_214703\\\",\\\"signTime\\\":\\\"\\\",\\\"totalTime\\\":\\\"Optional(398.45098673299998)\\\",\\\"chineseName\\\":\\\"徐浩南\\\",\\\"userName\\\":\\\"xuhaonan\\\",\\\"collect\\\":0,\\\"callTime\\\":\\\"2018-07-11 21:47:03\\\"}");
        System.err.println(s);
        System.err.println(JSONObject.parseObject(s));
    }

    private JSONObject getResFromHttpRestTemplate(String url, Map<String, Object> params) {
        JSONObject body = new JSONObject();
        // 请求
        try {
            RestTemplate restClient = new RestTemplate();
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            /*requestFactory.setReadTimeout(20000);
            requestFactory.setConnectTimeout(20000);*/
            restClient.setRequestFactory(requestFactory);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            //组装参数
            MultiValueMap<String, Object> reqParams= new LinkedMultiValueMap<String, Object>();
            Set<Map.Entry<String,Object>> entrySet = params.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                reqParams.add(entry.getKey(), entry.getValue());
            }
            org.springframework.http.HttpEntity<MultiValueMap<String, Object>> entity =
                    new org.springframework.http.HttpEntity<MultiValueMap<String, Object>>(reqParams, headers);
            ResponseEntity<String> upstreamRes = restClient.exchange(url, HttpMethod.POST, entity, String.class);
            body = JSONObject.parseObject(URLDecoder.decode(upstreamRes.getBody()));
            System.err.println(">>>>>> mobile url返回  {} <<<<<<" + body.toJSONString());
            return body;
        } catch (Exception e) {
            System.err.println(">>>>>> bobile接口请求失败   {}   {} <<<<<<" +  url + e);
            body.put("flag", 0);
            body.put("message", "网络异常");
            return body;
        }
    }

    @Test
    public void testStrSplit() {
        String str = "/34234.md";
        int start = str.indexOf("/");
        int end = str.lastIndexOf(".");
        System.err.println(str.substring(start + 1, end));
    }

    @Test
    public void testEmployeeId263() {
        String testStr = "yf-lijinyang";
        int start = testStr.lastIndexOf("-") + 1;
        int end = testStr.length();
        Matcher matcher = Pattern.compile("[0-9]").matcher(testStr);
        if (matcher.find()) {
            end = matcher.start();
        }
        System.err.println(testStr.substring(start, end));
        System.err.println(StringUtils.isBlank(String.valueOf(null)));
    }

    @Test
    public void testChinese() {
        String name = "cnjkhi";
        Pattern compile = Pattern.compile("[\\u4E00-\\u9FA5]");
        Matcher matcher = compile.matcher(name);
        if (matcher.find()) {
            System.err.println(true);
        }
        boolean chineseMatch = Pattern.matches("[\\u4E00-\\u9FA5]", name);
        System.err.println(chineseMatch);
    }

    @Test
    public void testUrlMatch() {
        String url = "localhost:11085/mobile-web/webImEmployeeBook/getImEmployeeInfo.do";
        Pattern compile = Pattern.compile("^((?!(webImEmployeeBook/getOrgChildInfoList|webImEmployeeBook/searchEmployeeList|webImEmployeeBook/getImEmployeeInfo)).)*.do$");
        //Pattern compile = Pattern.compile("/*.do");
        Matcher matcher = compile.matcher(url);
        System.err.println(matcher.find());

    }

    @Test
    public void testStrDec() {

    }

    @Test
    public void testGenerateSig() {
        String accountSid = "aaf98f8951af2ba80151c222100f467d";
        String accountAuthToken = "aa8fa1a029d34693b796be9bbc4c8a08";
        String appId = "8aaf070865796a570165844bb06608a3";

        String timestamp = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        try {
            String md5 = MD5Utils.encryptionMD5(accountSid + accountAuthToken + timestamp);
            String base64 = Base64Utils.encodeToString((accountSid + ":" + timestamp).getBytes());
            System.err.println("md5:" + md5.toUpperCase() + ",base64:" + base64);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
