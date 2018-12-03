package cn.colorfuline.elderlylauncher.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密机
 * Created by CQDXP on 2016/5/7.
 */
public class Encryptor {
    /**
     * 加密
     *
     * @param input
     * @param key
     * @return
     */
    public static String encrypt(String input, String key) {
        byte[] crypted = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(Base64.encode(crypted, Base64.NO_WRAP)).replace("+", "-");
    }

    /**
     * 加密
     *
     * @param input
     * @param key
     * @return
     */
    public static String encrypt(byte[] input, String key) {
        byte[] crypted = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return new String(Base64.encode(crypted, Base64.NO_WRAP)).replace("+", "-");
    }

    /**
     * 解密
     *
     * @param input
     * @param key
     * @return
     */
    public static String decrypt(String input, String key) {
        input = input.replace("-", "+");
        byte[] output = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            output = cipher.doFinal(Base64.decode(input, Base64.NO_WRAP));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(output);
    }

    /**
     * 解密
     *
     * @param input
     * @param key
     * @return
     */
    public static String decrypt(byte[] input, String key) {
        byte[] output = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            output = cipher.doFinal(Base64.decode(input, Base64.NO_WRAP));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return new String(output);
    }
}
