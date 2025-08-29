package com.hqumath.demo.utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class ByteUtil {

    private static final String HEX = "0123456789ABCDEF";

    public static byte charToByte(char c) {
        return (byte) HEX.indexOf(c);
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (ByteUtil.charToByte(hexChars[pos]) << 4 | ByteUtil.charToByte(
                    hexChars[pos + 1]));
        }
        return d;
    }

    public static int byteToHexInt(byte b) {
        return b & 0xff;
    }

    public static byte intToHexByteHigh(int i) {
        return (byte) ((i >> 8) & 0xff);
    }

    public static byte intToHexByteLow(int i) {
        return (byte) (i & 0xff);
    }

    public static long bytesToLong(byte[] b) {
        if (b.length != 4) {
            return -1;
        }
        return bytesToLong(b[0], b[1], b[2], b[3]);
    }

    public static long bytesToLong(byte b1, byte b2, byte b3, byte b4) {
        return (b1 & 0xFF) | ((b2 & 0xFF) << 8) | ((b3 & 0xFF) << 8 * 2) | ((b4 & 0xFF) << 8 * 3);
    }

    public static byte[] longToBytes(long l) {
        byte[] result = new byte[4];
        result[0] = (byte) (l & 0xFF);
        result[1] = (byte) (l >> 8 & 0xFF);
        result[2] = (byte) (l >> 16 & 0xFF);
        result[3] = (byte) (l >> 24 & 0xFF);
        return result;
    }

    public static int bytesToInt(byte high, byte low) {
        return (low & 0xFF) | ((high & 0xFF) << 8);
    }

    public static byte[] intToBytes(int value) {
        byte[] src = new byte[4];
        src[3] = (byte) ((value >> 24) & 0xFF);
        src[2] = (byte) ((value >> 16) & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[0] = (byte) (value & 0xFF);
        return src;
    }

    // char[]转byte[]
    public static byte[] getBytes(char[] chars) {
        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);

        return bb.array();
    }

    public static byte getByte(char c) {
        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = CharBuffer.allocate(1);
        cb.put(c);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);

        byte[] tmp = bb.array();
        return tmp[0];
    }

    // byte转char
    public static char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);

        return cb.array();
    }

    // byte转char
    public static char getChar(byte bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(1);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);

        char[] tmp = cb.array();

        return tmp[0];
    }

    /**
     * 字符串转字节
     *
     * @param s 字符串
     * @return 字节
     */
    public static byte[] stringToBytes(String s) {
        return stringToBytes(s, 0);
    }

    /**
     * 字符串转字节
     *
     * @param s      字符串
     * @param length 字节长度，不够补0
     */
    public static byte[] stringToBytes(String s, int length) {
        byte[] src = ByteUtil.getBytes((s == null || s.length() == 0) ? new char[0] : s.toCharArray());

        if (length < src.length) {
            length = src.length;
        }

        byte[] bytes = new byte[length];
        for (int i = 0; i < src.length; i++) {
            bytes[i] = src[i];
        }
        return bytes;
    }

    public static byte[] copy(byte[] src, int srcStart, int length) {
        byte[] result = new byte[length];
        System.arraycopy(src, srcStart, result, 0, length);
        return result;
    }

    //倒转
    public static byte[] reverse(byte[] src) {
        for (int i = 0; i <= src.length / 2 - 1; i++) {
            byte temp1 = src[i];
            byte temp2 = src[src.length - i - 1];
            src[i] = temp2;
            src[src.length - i - 1] = temp1;
        }
        return src;
    }

    public static String bytesToAscii(byte[] bytes, int offset, int dateLen) {
        if ((bytes == null) || (bytes.length == 0) || (offset < 0) || (dateLen <= 0)) {
            return null;
        }
        if ((offset >= bytes.length) || (bytes.length - offset < dateLen)) {
            return null;
        }

        String asciiStr = null;
        byte[] data = new byte[dateLen];
        System.arraycopy(bytes, offset, data, 0, dateLen);
        try {
            asciiStr = new String(data, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
        }
        return asciiStr;
    }

    public static String bytesToAscii(byte[] bytes, int dateLen) {
        return bytesToAscii(bytes, 0, dateLen);
    }

    public static String bytesToAscii(byte[] bytes) {
        return bytesToAscii(bytes, 0, bytes.length);
    }

    /**
     * 字符串 转 ascii bytes
     * ISO-8859-1 码表兼容大部分码表，因此适合做中间码表
     */
    public static byte[] stringToAsciiBytes(String s) {
        try {
            return s.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    /**
     * byteBuffer 转 byte数组
     *
     * @param buffer
     * @return
     */
    public static byte[] bytebuffer2ByteArray(ByteBuffer buffer) {
        //重置 limit 和postion 值
        buffer.flip();
        //获取buffer中有效大小
        int len = buffer.limit() - buffer.position();

        byte[] bytes = new byte[len];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = buffer.get();
        }
        return bytes;
    }

    /**
     * byte数组转hex 16进制
     *
     * @param bytes
     * @return
     */
    public static String byteToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {//每个字节由两个字符表示，位数不够，高位补0
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * byte数组转hex
     */
    public static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() < 2) {//每个字节由两个字符表示，位数不够，高位补0
            hex = "0" + hex;
        }
        return hex;
    }

    /**
     * hex字符串转byte数组
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex) {
        if ("1".equals(inHex)) {
            inHex = "01";
        }
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    /**
     * Hex字符串转byte
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    //数组合并
    public static byte[] byteMerger(byte[] bt1, byte[] bt2) {
        byte[] bt3 = new byte[bt1.length + bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }

    /**
     * 截取byte数组   不改变原数组
     *
     * @param b      原数组
     * @param off    偏差值（索引）
     * @param length 长度
     * @return 截取后的数组
     */
    public static byte[] subByte(byte[] b, int off, int length) {
        byte[] b1 = new byte[length];
        System.arraycopy(b, off, b1, 0, length);
        return b1;
    }
}
