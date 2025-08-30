package com.hqumath.demo.app;

import com.hqumath.demo.utils.SPUtil;

import java.util.HashMap;

/**
 * ****************************************************************
 * 文件名称: AppNetConfig
 * 作    者: Created by gyd
 * 创建时间: 2019/1/22 14:30
 * 文件描述: 网络地址
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class Constant {
    public static String BASE_API = "https://api.github.com/"; //API服务器
    public static String downloadHost = "http://cps.yingyonghui.com/"; //下载线路

    //请求通用参数
//    public static HashMap<String, String> getBaseMap() {
//        String token = SPUtil.getInstance().getString(TOKEN);
//        HashMap<String, String> map = new HashMap<>();
//        map.put("token", token);
//        return map;
//    }

    //SP Key
    public static final String USER_NAME = "USER_NAME";//用户名
    public static final String TOKEN = "TOKEN";
    public static final String APK_URL = "APK_URL";//apk下载地址
    public static final String APK_NAME = "APK_NAME";//apk文件名称

}
