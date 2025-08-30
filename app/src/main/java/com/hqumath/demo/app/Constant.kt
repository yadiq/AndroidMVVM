package com.hqumath.demo.app

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2025/7/27 22:27
 * 文件描述: APP常量
 * 注意事项:
 * ****************************************************************
 */
class Constant {
    companion object {
        const val BASE_API: String = "https://api.github.com/" //API服务器
        const val DOWNLOAD_API: String = "http://cps.yingyonghui.com/" //下载线路

        //请求通用参数
//        fun getBaseMap(): HashMap<String, String> {
//            val token = DataStoreUtil.getData(DataStoreKey.TOKEN, "")
//            val map = HashMap<String, String>()
//            map["token"] = token
//            return map
//        }
    }
}