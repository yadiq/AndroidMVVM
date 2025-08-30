package com.hqumath.demo.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.hqumath.demo.app.ActivityManager
import com.hqumath.demo.utils.CommonUtil
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.onAdaptListener
import me.jessyan.autosize.utils.ScreenUtils
import xcrash.XCrash
import kotlin.math.max
import kotlin.math.min

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2025/7/27 20:41
 * 文件描述:
 * 注意事项:
 * ****************************************************************
 */
class BaseApplication  : Application() {

    override fun onCreate() {
        super.onCreate()
        //初始化工具类
        CommonUtil.init(this)
        //生命周期监听回调
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)

        //屏幕适配方案，根据ui图修改,屏幕最小宽度360dp
        AutoSizeConfig.getInstance() //全局调节 APP 字体大小 1sp=1dp
            .setPrivateFontScale(1.0f) //屏幕适配监听器
            .setOnAdaptListener(object : onAdaptListener {
                override fun onAdaptBefore(target: Any, activity: Activity) {
                    //使用以下代码, 可以解决横竖屏切换时的屏幕适配问题
                    //使用以下代码, 可支持 Android 的分屏或缩放模式, 但前提是在分屏或缩放模式下当用户改变您 App 的窗口大小时
                    //系统会重绘当前的页面, 经测试在某些机型, 某些情况下系统不会重绘当前页面, ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application!!!
                    val widthPixels = ScreenUtils.getScreenSize(activity)[0]
                    val heightPixels = ScreenUtils.getScreenSize(activity)[1]
                    AutoSizeConfig.getInstance().setScreenWidth(min(widthPixels.toDouble(), heightPixels.toDouble()).toInt()) //使用宽高中的最小值计算最小宽度
                    AutoSizeConfig.getInstance().setScreenHeight(max(widthPixels.toDouble(), heightPixels.toDouble()).toInt())
                    //AutoSizeLog.d(String.format(Locale.ENGLISH, "%s onAdaptBefore!", target.getClass().getName()));
                }

                override fun onAdaptAfter(target: Any, activity: Activity) {
                    //AutoSizeLog.d(String.format(Locale.ENGLISH, "%s onAdaptAfter!", target.getClass().getName()));
                }
            })
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        //异常捕获初始化
        // 存储位置: /data/data/PACKAGE_NAME/files/tombstones
        // 捕获类型: native java anr
        // 模拟异常: XCrash.testNativeCrash(false) XCrash.testJavaCrash(false)
        XCrash.init(this)
        /*XCrash.init(this, new XCrash.InitParameters()
            .setAppVersion("1.2.3-beta456-patch789")//版本号，默认versionName 1.0.0
            .setJavaRethrow(true)//处理后是否应向系统重新抛出Java异常，默认true
            .setJavaLogCountMax(10)//保存在日志目录中的Java崩溃日志文件的最大数量。默认10
            .setJavaDumpAllThreadsWhiteList(new String[]{"^main$", "^Binder:.*", ".*Finalizer.*"})//需要转储线程名称的白名单
            .setJavaDumpAllThreadsCountMax(10)//设置发生Java异常时要转储的其他线程的最大数量。
            .setJavaCallback(callback)//java异常回调
            .setNativeRethrow(true)
            .setNativeLogCountMax(10)
            .setNativeDumpAllThreadsWhiteList(new String[]{"^xcrash\\.sample$", "^Signal Catcher$", "^Jit thread pool$", ".*(R|r)ender.*", ".*Chrome.*"})
            .setNativeDumpAllThreadsCountMax(10)
            .setNativeCallback(callback)
            //.setAnrCheckProcessState(false)
            .setAnrRethrow(true)//设置进程错误状态是否是ANR的必要条件。（默认值：true）
            .setAnrLogCountMax(10)
            .setAnrCallback(callback)
            .setAnrFastCallback(anrFastCallback)
            .setPlaceholderCountMax(3)//占位符文件的最大数量 设置为0表示禁用占位符功能
            .setPlaceholderSizeKb(512)//设置日志目录中每个占位符文件的KB
            //.setLogDir(getExternalFilesDir("xcrash").toString())//存储目录。默认 Context. getFilesDir() + "/ tombstones"
            .setLogFileMaintainDelayMs(1000));//在执行日志文件维护任务之前设置延迟（毫秒）。（默认值：5000）*/
    }

    private val activityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            //注册监听每个activity的生命周期,便于堆栈式管理
            ActivityManager.addActivity(activity)
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityDestroyed(activity: Activity) {
            ActivityManager.removeActivity(activity)
        }
    }
}
