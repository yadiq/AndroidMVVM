package com.hqumath.demo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class StringUtil {

    /**
     * 文件大小格式化为 *MB
     * @param size
     * @return
     */
    public static String getSizeString(long size) {
        if (size < 1024) {
            return String.format(Locale.getDefault(), "%d B", size);
        } else if (size < 1024 * 1024) {
            float sizeK = size / 1024f;
            return String.format(Locale.getDefault(), "%.2f KB", sizeK);
        } else if (size < 1024 * 1024 * 1024) {
            float sizeM = size / (1024f * 1024f);
            return String.format(Locale.getDefault(), "%.2f MB", sizeM);
        } else if (size / 1024 < 1024 * 1024 * 1024) {
            float sizeG = size / (1024f * 1024f * 1024f);
            return String.format(Locale.getDefault(), "%.2f GB", sizeG);
        }
        return null;
    }

    /**
     * 判断相等，参数可以为空
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(String a, String b) {
        if (a == null && b == null)
            return true;
        if (a == null || b == null)
            return false;
        return a.equals(b);
    }

    /**
     * map转string
     *
     * @param map
     * @return
     */
    public static String MapToString(HashMap<String, String> map) {
        Iterator<Map.Entry<String, String>> i = map.entrySet().iterator();
        if (!i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (; ; ) {
            Map.Entry<String, String> e = i.next();
            String key = e.getKey();
            String value = e.getValue();
            sb.append(key);
            sb.append(':');
            sb.append(value);
            if (!i.hasNext())
                return sb.append('}').toString();
            sb.append(',');//.append(' ');
        }
    }

    /**
     * String 转 map
     *
     * @param data
     * @return
     */
    public static HashMap<String, String> String2HashMap(String data) {
        int start = data.indexOf("{");
        int end = data.indexOf("}");
        String data0 = data.substring(start + 1, end);
        HashMap<String, String> hashMap = new LinkedHashMap<>();
        String[] data1 = data0.split(",");
        for (String data2 : data1) {
            String[] data3 = data2.split(":");
            if (data3.length == 2) {
                hashMap.put(data3[0].trim().replace("\"", ""), data3[1].trim().replace("\"", ""));
            } else if (data3.length == 1) {
                hashMap.put(data3[0].trim().replace("\"", ""), "");
            }
        }
        return hashMap;
    }

    /**
     * Base64字符串转换成图片
     *
     * @param string
     * @return
     */
    public static Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 图片转换成base64字符串
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imgBytes = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    /**
     * 计算字符串的MD5
     */
    public static String md5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            // 32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
