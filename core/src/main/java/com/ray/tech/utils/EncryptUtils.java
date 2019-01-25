package com.ray.tech.utils;


import com.ray.tech.exception.InternalException;
import org.apache.commons.codec.digest.DigestUtils;


import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 *
 */
public class EncryptUtils {


    private static final String UTF8 = "UTF-8";

    /**
     * 简易版
     *
     * @param args
     */
    public static void main(String[] args) {
        String md5Hex = DigestUtils.md5Hex("123");
        String shaHex = DigestUtils.shaHex("123");
        System.out.println(md5Hex);
        System.out.println(shaHex);
    }

    /**
     * 获取AES加密后的 十六进制字符串
     *
     * @param content
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static String aesEncryptHexString(String content, String encryptKey) throws Exception {
        return parseByte2HexString(aesEncrypt(content, encryptKey));
    }


    /**
     * 用SHA1算法生成安全签名
     *
     * @param token     票据
     * @param timestamp 时间戳
     * @param nonce     随机字符串
     * @return 安全签名
     */
    public static String getSHA1(String token, String timestamp, String nonce) throws Exception {
        try {
            String[] array = new String[]{token, timestamp, nonce};
            StringBuilder sb = new StringBuilder();
            // 字符串排序
            Arrays.sort(array);
            for (int i = 0; i < 3; i++) {
                sb.append(array[i]);
            }
            String str = sb.toString();
            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuilder hexStr = new StringBuilder();
            String shaHex;
            for (byte aDigest : digest) {
                shaHex = Integer.toHexString(aDigest & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }
            return hexStr.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    /**
     * 解密AES加密后的 十六进制字符串
     *
     * @param hexContent
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static String aesDecryptHexString(String hexContent, String encryptKey) {
        try {
            return aesDecrypt(parseHexStr2Byte(hexContent), encryptKey);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | BadPaddingException | NoSuchPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            throw new InternalException("解密失败");
        } catch (NumberFormatException e) {
            throw new InternalException("加密数据有误，无法参与解密");
        }
    }


    /**
     * 将二进制转换成16进制
     *
     * @param buffer
     * @return
     */
    private static String parseByte2HexString(byte buffer[]) {
        StringBuilder sb = new StringBuilder();
        for (byte aBuffer : buffer) {
            String hex = Integer.toHexString(aBuffer & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexString
     * @return
     */
    private static byte[] parseHexStr2Byte(String hexString) {
        if (hexString.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length() / 2; i++) {
            int high = Integer.parseInt(hexString.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexString.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    /**
     * AES 加密
     *
     * @param content    加密内容
     * @param encryptKey 加密秘钥
     * @return 加密数组
     * @throws Exception
     */
    private static byte[] aesEncrypt(String content, String encryptKey) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(encryptKey.getBytes(UTF8));
        keyGen.init(128, secureRandom);
        SecretKey secretKey = keyGen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("AES");
        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(content.getBytes(UTF8));
    }

    /**
     * AES 解密
     *
     * @param content    解密内容
     * @param encryptKey 解密秘钥
     * @return
     * @throws Exception
     */
    private static String aesDecrypt(byte[] content, String encryptKey) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(encryptKey.getBytes(UTF8));
        keyGen.init(128, secureRandom);
        SecretKey secretKey = keyGen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("AES");
        // 初始化
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] result = cipher.doFinal(content);
        return new String(result, UTF8);
    }
}
