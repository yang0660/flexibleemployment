package com.flexibleemployment.utils;

import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;

/**
 * 微信工具类
 */
public class WechatDecryptDataUtil {

    /**
     * 解密并且获取用户手机号码
     *
     * @param encrypdata
     * @param ivdata
     * @param sessionkey
     * @param
     * @return
     * @throws Exception 
     */

    public String deciphering(String encrypdata, String ivdata, String sessionkey) {
        byte[] encrypData = Base64.decode(encrypdata);
        byte[] ivData = Base64.decode(ivdata);
        byte[] sessionKey = Base64.decode(sessionkey);
        String str = "";
        try {
            str = decrypt(sessionKey, ivData, encrypData);
        } catch (Exception e) {

            e.printStackTrace();
        }
        System.out.println(str);
        return str;

    }

    public static String decrypt(byte[] key, byte[] iv, byte[] encData) throws Exception {
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        //解析解密后的字符串 
        return new String(cipher.doFinal(encData), "UTF-8");
    }

}
