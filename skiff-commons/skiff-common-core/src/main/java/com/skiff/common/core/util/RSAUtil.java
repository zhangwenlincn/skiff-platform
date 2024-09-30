package com.skiff.common.core.util;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * RSA加密工具类
 * <p>
 * KeyPair keyPair = RSAUtil.generateKeyPair();
 * PublicKey publicKey = keyPair.getPublic();
 * PrivateKey privateKey = keyPair.getPrivate();
 * <p>
 * // 打印公钥和私钥
 * System.out.println("Public Key: " + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
 * System.out.println("Private Key: " + Base64.getEncoder().encodeToString(privateKey.getEncoded()));
 * <p>
 * // 原始文本
 * String originalText = "Hello, RSA!";
 * <p>
 * // 加密
 * String encryptedText = RSAUtil.encrypt(originalText, publicKey);
 * System.out.println("Encrypted Text: " + encryptedText);
 * <p>
 * // 解密
 * String decryptedText = RSAUtil.decrypt(encryptedText, privateKey);
 * System.out.println("Decrypted Text: " + decryptedText);
 */
public class RSAUtil {

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024); // 可以选择1024, 2048, 4096等位数的密钥
        return keyGen.generateKeyPair();
    }

    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
