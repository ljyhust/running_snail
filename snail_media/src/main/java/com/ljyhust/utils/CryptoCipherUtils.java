package com.ljyhust.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 流加密工具类
 * Created by Administrator on 2018/5/6.
 */
public class CryptoCipherUtils {
    private static final int ENCRYPT_MODE = Cipher.ENCRYPT_MODE;
    private static final int DECRYPT_MODE = Cipher.DECRYPT_MODE;
    private static final int BLOCK_SIZE = 1024;

    /**
     * 密钥生成方法
     * @param Algorithm 加密算法类型  AES/RSA/RC4等
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String generateKey(final String Algorithm) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(Algorithm);
        SecureRandom random = new SecureRandom();
        keyGenerator.init(random);
        SecretKey key = keyGenerator.generateKey();
        byte[] bytes = key.getEncoded();
        return Base64.getEncoder().encodeToString(bytes);
    }
    private static void encodeOrDecode(final int MODE, InputStream is, OutputStream os, String keyStr, String algorithm) {
        try {
            byte[] bytes = Base64.getDecoder().decode(keyStr);
            SecretKey key = new SecretKeySpec(bytes, algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(MODE, key);
            CipherOutputStream out = new CipherOutputStream(os, cipher);
//            int c = 0;
//            while ((c = is.read()) != -1) {
//                out.write(c);
//            }
            byte[] buff = new byte[BLOCK_SIZE];
            int length;
            while ((length = is.read(buff, 0, buff.length)) != -1) {
                out.write(buff, 0, length);
                /*if (length == BLOCK_SIZE)
                    out.write(buff, 0, length);
                else {
                    byte[] finalBytes = cipher.doFinal(buff, 0, length);
                    os.write(buff, 0, length);
                }*/
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
        }
    }

    public static void encodeWithRC4(InputStream is, OutputStream os, String keyStr) {
        encodeOrDecode(ENCRYPT_MODE, is, os, keyStr, "RC4");
    }

    public static void decodeWithRC4(InputStream is, OutputStream os, String keyStr) {
        encodeOrDecode(DECRYPT_MODE, is, os, keyStr, "RC4");
    }

    public static void encode(InputStream is, OutputStream os, String keyStr) {
        encodeOrDecode(ENCRYPT_MODE, is, os, keyStr, "AES");
    }
    public static void decode(InputStream is, OutputStream os, String keyStr) {
        encodeOrDecode(DECRYPT_MODE, is, os, keyStr, "AES");
    }

    /**
     * 根据块大小解密
     * @param is 输入流
     * @param os 输出流
     * @param keyStr 密钥
     * @param size 块大小，单位：KB
     */
    public static void decodeWithBlock(InputStream is, OutputStream os, String keyStr, Integer size, String algorithm) {
        try {
            byte[] bytes = Base64.getDecoder().decode(keyStr);
            SecretKey key = new SecretKeySpec(bytes, algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(DECRYPT_MODE, key);
            CipherOutputStream out = new CipherOutputStream(os, cipher);
//            int c = 0;
//            while ((c = is.read()) != -1) {
//                out.write(c);
//            }
            byte[] buff = new byte[BLOCK_SIZE];
            int length;
            int sum = 0;
            while ((length = is.read(buff, 0, buff.length)) != -1) {
                if (sum > size) {
                    break;
                }
                out.write(buff, 0, length);
                sum++;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
        }
    }
}
