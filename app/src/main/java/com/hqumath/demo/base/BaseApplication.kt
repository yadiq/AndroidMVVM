package com.hqumath.demo.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.hqumath.demo.app.ActivityManager
import com.hqumath.demo.utils.CommonUtil
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.onAdaptListener
import me.jessyan.autosize.utils.ScreenUtils
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
