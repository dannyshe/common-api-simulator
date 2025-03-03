package com.payment.simulator.common.encry;


import org.jasypt.encryption.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * 描述信息
 *
 * @author Grayson
 * @createTime 2021-11-01
 */
public class DESEncrypt implements StringEncryptor {

    public static final String DES = "DES";

    private static final Logger LOG = LoggerFactory.getLogger(DESEncrypt.class);

    private String key;
    public DESEncrypt(String key){
        this.key = key;
    }
    public static byte[] encoder(byte[] src, byte[] key)
            throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        return cipher.doFinal(src);
    }


    public static byte[] decoder(byte[] src, byte[] key)
            throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(src);
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public final static String decoder(String data, String key) {
        try {
            return new String(
                    decoder(hex2byte(data.getBytes()), key.getBytes()));
        } catch (Exception e) {
            LOG.info(String.format("data:%s,key:%s", data, key));
            LOG.info("error,", e);
        }
        return null;
    }

    /**
     * 加密
     *
     * @param password
     * @param key
     * @return
     */
    public final static String encoder(String password, String key) {
        try {
            return byte2hex(encoder(password.getBytes(), key.getBytes()));
        } catch (Exception e) {
            LOG.info("encoder error,", e);
        }
        return null;
    }


    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();

    }


    private static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0) {
            throw new IllegalArgumentException("HAHA");
        }
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }


    @Override
    public String encrypt(String s) {
        return encoder(s, key);
    }

    @Override
    public String decrypt(String s) {
        return decoder(s, key);
    }
}
