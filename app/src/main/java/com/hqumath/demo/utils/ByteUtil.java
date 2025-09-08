package com.hqumath.demo.utils;

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2022/3/8 13:28
 * 文件描述:
 * 注意事项: 用更大的存储空间来保存无符号类型
 * 大端模式：低地址存储高字节。网络通讯
 * 小端模式：低地址存储低字节。常见的x86 arm架构
 * ****************************************************************
 */
public class ByteUtil {
    //无法运行的问题
    //在.idea文件夹下的gradle.xml文件中<GradleProjectSettings>下添加<option name="delegatedBuild" value="false" />

    private static final String TAG = "ByteUtil1";

    public static void main(String[] args) {
        /*//byte[] b1= {0,0,0,1};//1
        byte[] b1 = {(byte) 0b10000000, 0, 0, 0};//2147483648
        //byte[] b1= {(byte) 0x80,0};

        //byte[] b2= {0,0,0,0,0,0,0,1};//1
        byte[] b2 = { (byte) 0b10000000, 0, 0, 0, 0, 0, 0, 0};//2147483648
        //byte[] b2 = { (byte) 0x80, 0, 0, 0, 0, 0, 0, 0};//2147483648

        *//*int r1 = bytes2Int(b1, 0, false);
        float r2 = bytes2Float(b1, 0, false);
        long r3 = bytes2Long(b2, 0, false);
        double r4 = bytes2Double(b2, 0, false);*//*
        long r1 = bytes2Int64(b2,0,false);
        float r2 = bytes2Float(b1,0,false);
        double r3 = bytes2Double(b2,0,false);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);*/

        //String uuid = "d04894a4-f658-8d7d-7405-15fff20b6377".replace("-", "");
        /*String hex = Integer.toHexString(100);

        //String 转 byte[]
        byte[] data1 = "wintop".getBytes();
        //16进制 转 byte[]
        byte[] data2 = ByteUtil.hexToByteArray(hex);//2Hex 1Byte
        Integer.parseInt(1)
        System.out.println(hex);*/
        byte[] b1= {(byte) 0x80,0};
        System.out.println(ByteUtil.bytesToHexWithSpace(b1));
    }

    /////////////////////////////数值类型转Hex///////////////////////////

    /**
     * 将int32_t转为hex
     */
    public static String int32ToHex(int n) {
        return Integer.toHexString(n);
    }

    /**
     * byte转hex
     */
    public static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        return hex.length() == 1 ? "0" + hex : hex;//每个字节由两个字符表示，位数不够，高位补0
    }

    /**
     * byte[]转hex
     */
    public static String bytesToHex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            sb.append(byteToHex(b[i]));
        }
        return sb.toString();
    }

    /**
     * byte[]转hex，以空格间隔
     */
    public static String bytesToHexWithSpace(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            sb.append(byteToHex(b[i])).append(" ");
        }
        return sb.toString();
    }
    /////////////////////////////数值类型转byte[]///////////////////////////

    /**
     * int8_t转byte,n∈[-128,127]
     * uint8_t转byte,n∈[0,255]
     */
    public static byte int8ToByte(int n) {
        return (byte) (n & 0xff);
    }

    /**
     * int8_t unit8_t 转byte[1]
     * int16_t uint16_t 转byte[2]
     * int32_t 转byte[4]
     *
     * @param n            数值
     * @param length       转出字节长度 [1,4]
     * @param littleEndian 是否小端
     */
    public static byte[] intToBytes(int n, int length, boolean littleEndian) {
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            int shift = (littleEndian ? i : (length - 1 - i)) * 8;//需要移位的数量 i*8
            bytes[i] = (byte) ((n >> shift) & 0xff);
        }
        return bytes;
    }

    /**
     * int8_t unit8_t 转byte[1]
     * int16_t uint16_t 转byte[2]
     * int32_t uint32_t 转byte[4]
     * int64_t 转byte[8]
     *
     * @param n            数值
     * @param length       转出字节长度 [1,8]
     * @param littleEndian 是否小端
     */
    public static byte[] longToBytes(long n, int length, boolean littleEndian) {
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            int shift = (littleEndian ? i : (length - 1 - i)) * 8;//需要移位的数量 i*8
            bytes[i] = (byte) ((n >> shift) & 0xff);
        }
        return bytes;
    }

    public static byte[] int8ToBytes(int n, boolean littleEndian) {
        return intToBytes(n, 1, littleEndian);
    }

    public static byte[] int16ToBytes(int n, boolean littleEndian) {
        return intToBytes(n, 2, littleEndian);
    }

    public static byte[] int32ToBytes(int n, boolean littleEndian) {
        return intToBytes(n, 4, littleEndian);
    }

    public static byte[] int64ToBytes(long n, boolean littleEndian) {
        return longToBytes(n, 8, littleEndian);
    }

    /**
     * 将hex转为byte
     *
     * @param hex char[2], 两个十六进制数
     */
    public static byte hexToByte(String hex) {
        byte b = 0;
        try {
            b = (byte) Integer.parseInt(hex, 16);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 将hex转为byte[]
     *
     * @param hex, 2n个十六进制数，注意不要有空格
     */
    public static byte[] hexToBytes(String hex) {
        hex = hex.replace(" ", "");
        if (hex.length() % 2 == 1) {//每两个hex描述一个字节
            hex = "0" + hex;
        }
        int byteLen = hex.length() / 2;
        byte[] result = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            result[i] = hexToByte(hex.substring(i * 2, i * 2 + 2));
        }
        return result;
    }

    /**
     * 将binary转为byte
     *
     * @param binary char[8], 八个二进制数
     */
    public static byte binaryToByte(String binary) {
        return (byte) Integer.parseInt(binary, 2);
    }

    /**
     * 将binary转为byte, 8n个二进制数
     */
    public static byte[] binaryToBytes(String binary) {
        binary.replace(" ", "");
        int byteLen = binary.length() / 8;
        byte[] result = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            result[i] = binaryToByte(binary.substring(i * 8, (i + 1) * 8));
        }
        return result;
    }


    /**
     * 将一个长度为8的boolean数组（每bit代表一个boolean值）转换为byte。数组正向
     * 0x01 二进制为 0000 0001, bit7:0
     * @param array boolean[8]
     */
    public static byte booleansToByte(boolean[] array) {
        byte b = 0;
        if (array != null && array.length == 8) {
            for (int i = 0; i < 8; i++) {
                if (array[i]) {
                    b += (1 << i);
                }
            }
        }
        return b;
    }
    /////////////////////////////byte[]转数值类型////////////////////////////

    /**
     * 将byte转为boolean[8]。数组正向
     * 0x01 二进制为 0000 0001, bit7:0
     * @param b 1个字节
     */
    public static boolean[] byteToBooleans(byte b) {
        boolean[] array = new boolean[8];
        for (int i = 0; i < 8; i++) { //对于byte的每bit进行判定
            array[i] = (b & 1) == 1;  //判定byte的最后一位是否为1，若为1，则是true；否则是false
            b = (byte) (b >> 1);    //将byte右移一位
        }
        return array;
    }

    /**
     * byte转bit[]，打印bit0:7，以空格间隔,
     */
    public static String byteToBitWithSpace(byte b) {
        StringBuilder sb = new StringBuilder();
        boolean[] booleans = byteToBooleans(b);
        for (int i = 0; i < booleans.length; i++) {
            sb.append(" " + (booleans[i] ? "1" : "0"));
        }
        return sb.toString();
    }

    /**
     * 将byte转为int8_t，short
     *
     * @param b 1个字节
     */
    public static short byteToInt8(byte b) {
        return (short) (b);
    }

    /**
     * 将byte转为uint8_t，short
     *
     * @param b 1个字节
     * @return
     */
    public static short byteToUint8(byte b) {
        return (short) (b & 0xff);
    }

    /**
     * 将byte[2]转为int16_t short
     *
     * @param b            2个字节
     * @param offset       起始偏移量
     * @param littleEndian 输入数组是否小端模式
     */
    public static short bytesToInt16(byte[] b, int offset, boolean littleEndian) {
        short value = 0;
        for (int i = 0; i < 2; i++) {
            int shift = (littleEndian ? i : (1 - i)) * 8;//需要移位的数量 i*8
            value |= ((b[offset + i] & 0xff) << shift);
        }
        return value;
    }

    /**
     * 将byte[2]转为uint16_t int
     *
     * @param b            2个字节
     * @param offset       起始偏移量
     * @param littleEndian 输入数组是否小端模式
     */
    public static int bytesToUint16(byte[] b, int offset, boolean littleEndian) {
        int value = 0;
        for (int i = 0; i < 2; i++) {
            int shift = (littleEndian ? i : (1 - i)) * 8;//需要移位的数量 i*8
            value |= ((b[offset + i] & 0xff) << shift);
        }
        return value;
    }


    /**
     * 将byte[4]转为int32_t int
     *
     * @param b            4个字节
     * @param offset       起始偏移量
     * @param littleEndian 输入数组是否小端模式
     */
    public static int bytesToInt32(byte[] b, int offset, boolean littleEndian) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (littleEndian ? i : (3 - i)) * 8;//需要移位的数量 i*8
            value |= ((b[offset + i] & 0xff) << shift);
        }
        return value;
    }

    /**
     * 将byte[4]转为uint32_t long
     *
     * @param b            4个字节
     * @param offset       起始偏移量
     * @param littleEndian 输入数组是否小端模式
     */
    public static long bytesToUint32(byte[] b, int offset, boolean littleEndian) {
        long value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (littleEndian ? i : (3 - i)) * 8;//需要移位的数量 i*8
            value |= ((long) (b[offset + i] & 0xff) << shift);
        }
        return value;
    }

    /**
     * 将byte[8]转为int64_t long
     * 将byte[8]转为uint64_t long long  无法表示,用bytes2Int64代替
     *
     * @param b            8个字节
     * @param offset       起始偏移量
     * @param littleEndian 输入数组是否小端模式
     */
    public static long bytesToInt64(byte[] b, int offset, boolean littleEndian) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            int shift = (littleEndian ? i : (7 - i)) * 8;//需要移位的数量 i*8
            value |= ((long) (b[offset + i] & 0xff) << shift);
        }
        return value;
    }

    /**
     * 将byte[4]转为float32_t float
     *
     * @param b            4个字节
     * @param offset       起始偏移量
     * @param littleEndian 输入数组是否小端模式
     */
    public static float bytesToFloat(byte[] b, int offset, boolean littleEndian) {
        int value = bytesToInt32(b, offset, littleEndian);
        return Float.intBitsToFloat(value);
    }

    /**
     * 将byte[8]转为double
     *
     * @param b            8个字节
     * @param offset       起始偏移量
     * @param littleEndian 输入数组是否小端模式
     */
    public static double bytesToDouble(byte[] b, int offset, boolean littleEndian) {
        long value = bytesToInt64(b, offset, littleEndian);
        return Double.longBitsToDouble(value);
    }
    /////////////////////////////数组操作////////////////////////////////

    /**
     * 数组合并
     */
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
    /////////////////////////////其它////////////////////////////////

    /**
     * 累加和校验，字节相加取最后8位
     *
     * @param bytes
     * @return
     */
    public static byte makeChecksum(byte[] bytes) {
        int total = 0;
        for (byte aByte : bytes) {
            total += byteToUint8(aByte);
        }
        return (byte) (total & 0xff);//取后8位
    }

    //报文前两字节长度
    public static int getMessageLen(byte[] data) {
        return (short) ((data[1] & 0xFF) | (data[0] << 8));
    }

}
