package com.hqumath.demo.app

import android.app.Activity
import java.lang.ref.WeakReference
import java.util.Stack

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2025/7/27 21:37
 * 文件描述: Activity管理
 * 注意事项:
 * ****************************************************************
 */
object ActivityManager {
    private val activityStack = Stack<WeakReference<Activity>>()

    /**
     * 添加一个activity
     *
     * @param activity
     */
    fun addActivity(activity: Activity) {
        // 添加前先清理无效引用
        cleanUp()
        activityStack.push(WeakReference(activity))
    }

    /**
     * 删除指定activity
     *
     * @param activity
     */
    fun removeActivity(activity: Activity) {
        activityStack.removeAll { it.get() == null || it.get() == activity }
    }

    /**
     * 获取当前activity
     */
    fun currentActivity(): Activity? {
        cleanUp()
        return activityStack.lastOrNull()?.get()
    }

    /**
     * 清空栈
     */
    fun finishAllActivities() {
        for (ref in activityStack) {
            ref.get()?.let { activity ->
                if (!activity.isFinishing) {
                    activity.finish()
                }
            }
        }
        activityStack.clear()
    }

    private fun cleanUp() {
        activityStack.removeAll { it.get() == null }
    }
}
