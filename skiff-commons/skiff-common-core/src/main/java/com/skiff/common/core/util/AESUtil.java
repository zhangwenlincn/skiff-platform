package com.skiff.common.core.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES加密工具类
 * // 生成一个AES密钥（例如256位）
 * SecretKey key = generateKey(256);
 * String encodedKey = encodeKeyToBase64(key);
 * System.out.println("Encoded key: " + encodedKey);
 * <p>
 * // 原始文本
 * String originalText = "Hello, AES encryption!";
 * <p>
 * // 加密
 * String encryptedText = encrypt(originalText, key);
 * System.out.println("Encrypted: " + encryptedText);
 * <p>
 * // 使用相同的密钥解密
 * SecretKey decodedKey = decodeKeyFromBase64(encodedKey);
 * String decryptedText = decrypt(encryptedText, decodedKey);
 * System.out.println("Decrypted: " + decryptedText);
 */
public class AESUtil {

    // 生成AES密钥
    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n, new SecureRandom());
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey;
    }

    // 将密钥转换为字符串（Base64编码）
    public static String encodeKeyToBase64(SecretKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    // 从字符串（Base64编码）转换回密钥
    public static SecretKey decodeKeyFromBase64(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    // 加密数据
    public static String encrypt(String plainText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 解密数据
    public static String decrypt(String cipherText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }


}
