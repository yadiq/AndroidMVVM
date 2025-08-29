package com.hqumath.demo.utils;
//

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 签名，认证
 */
public class SignatureUtil {

    /**
     * 获取随机数字、大小写字母
     *
     * @param len 长度
     * @return
     */
    public static String getRandomNumLetter(int len) {
        char[] str = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < len; i++) {
            builder.append(str[random.nextInt(62)]);
        }
        return builder.toString();
    }

    /**
     * 计算MD5
     * @param input
     * @return
     */
    public static String getMd5Value(String input) {
        if(input == null || input.length() == 0) {
            return "";
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(input.getBytes());
            byte[] byteArray = md5.digest();
            BigInteger bigInt = new BigInteger(1, byteArray);
            // 参数16表示16进制
            String result = bigInt.toString(16);
            // 不足32位高位补零
            while(result.length() < 32) {
                result = "0" + result;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
