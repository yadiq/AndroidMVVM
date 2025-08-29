package com.hqumath.demo.utils;

import static android.content.Context.TELEPHONY_SERVICE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.net.NetworkInterface;
import java.util.Enumeration;

public class DeviceUtil {

    /**
     * 获取imei SIM卡槽
     */
    public static String getIMEI() {
        Context context = CommonUtil.getContext();
        String imei = "";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }

    /**
     * 获取wifi网卡的mac地址，6.0以上特殊处理
     *
     * @return
     */
    public static String getMac() {
        Context context = CommonUtil.getContext();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String str = getMacMoreThanM();
                if (!TextUtils.isEmpty(str))
                    return str;
            } else {
                @SuppressLint("WifiManagerLeak")
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if (wifiInfo != null)
                    return wifiInfo.getMacAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * android 6.0+获取wifi的mac地址
     *
     * @return
     */
    private static String getMacMoreThanM() {
        try {
            //获取本机器所有的网络接口
            Enumeration enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) enumeration.nextElement();
                // wlan0:无线网卡 eth0：以太网卡 (机顶盒厂家贴的mac是以太网卡)
                if (!networkInterface.getName().equals("wlan0")) {
                    continue;
                }
                //获取硬件地址，一般是MAC
                byte[] arrayOfByte = networkInterface.getHardwareAddress();
                if (arrayOfByte == null || arrayOfByte.length == 0) {
                    continue;
                }
                return ByteUtil.byteToHex(arrayOfByte).toUpperCase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设备首次运行的时候，系统会随机生成一64位的数字，恢复出厂设置后改变
     * 支持获取oaid的，优先获取oaid，需要集成aar https://github.com/haoguibao/OaidDemo/tree/master
     */
    public static String getAndroidId() {
        return Settings.System.getString(CommonUtil.getContext().getContentResolver(), Settings.System.ANDROID_ID);
    }

    /**
     * 设备序列号，有些手机上会出现垃圾数据，比如红米手机返回的就是连续的非随机数
     */
    public static String getSN() {
        return Build.SERIAL;
    }
}
