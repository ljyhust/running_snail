package com.ljyhust.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密处理类
 * Created by Administrator on 2018/5/5.
 */
public class MD5Utils {

    public static String encryptionMD5(String str){
        try {
            MessageDigest md =MessageDigest.getInstance("MD5");
            md.reset();
            md.update(str.getBytes("UTF-8"));
            byte[] byteArray = md.digest();
            StringBuffer md5StrBuff = new StringBuffer();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                else
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
            return md5StrBuff.toString().toLowerCase();
        }catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
