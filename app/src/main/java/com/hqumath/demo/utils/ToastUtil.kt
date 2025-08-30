package com.hqumath.demo.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

/**
 * 线程安全的 Toast 工具类
 */
object ToastUtils {

    // 全局 Handler，绑定主线程 Looper
    private val handler = Handler(Looper.getMainLooper())
    private var toast: Toast? = null

    /**
     * 线程安全的 Toast 显示
     * @param context 上下文
     * @param message 提示内容
     */
    fun show(context: Context, message: String) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            // 已经在主线程
            showInternal(context, message)
        } else {
            // 切回主线程
            handler.post {
                showInternal(context, message)
            }
        }
    }

    private fun showInternal(context: Context, message: String) {
        // 复用一个 Toast，避免多个 Toast 堆积
        if (toast == null) {
            toast = Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT)
        } else {
            toast?.setText(message)
        }
        toast?.show()
    }
}
