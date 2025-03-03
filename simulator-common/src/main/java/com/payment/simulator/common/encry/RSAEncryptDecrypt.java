package com.payment.simulator.common.encry;

import java.util.Base64;
import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @description RSA 2048 encrypt and decrypt
 * @date 2021-12-31
 */
public class RSAEncryptDecrypt {
    /**
     * rsa encrypt
     * @param str encrypt source
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] str, String publicKey) throws Exception
    {
        // base64编码的公钥
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(decoded));
        // RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(str);
    }

    /**
     * rsa encrypt to string
     * @param str encrypt source
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encryptToStr(byte[] str, String publicKey) throws Exception
    {
        return new String(Base64.getEncoder().encode(encrypt(str,publicKey)));
    }

    /**
     *  rsa descryptToStr
     * @param str
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decryptToStr(byte[] str, String privateKey) throws Exception{
        return new String(decrypt(str,privateKey));
    }

    public static byte[] decrypt(byte[] str, String privateKey) throws Exception
    {
        // base64编码的私钥
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        // RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return cipher.doFinal(str);
    }
}
