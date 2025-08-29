package com.hqumath.demo.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hqumath.demo.dialog.LoadingDialog

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2025/7/27 20:51
 * 文件描述:
 * 注意事项:
 * ****************************************************************
 */
abstract class BaseActivity : AppCompatActivity() {

    lateinit var mContext: BaseActivity
    private var loadingDialog: LoadingDialog? = null

    private var isActive = false //是否在前台

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        setContentView(initContentView(savedInstanceState))
        //事件监听
        initListener()
        //初始化数据
        initData()
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable()
    }

    override fun onResume() {
        super.onResume()
        isActive = true
    }

    override fun onPause() {
        super.onPause()
        isActive = false
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoadingDialog()
    }

    abstract fun initContentView(savedInstanceState: Bundle?): View?

    abstract fun initListener()

    abstract fun initData()

    abstract fun initViewObservable()

    fun showLoadingDialog(msg: String? = null) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(context = mContext)
        }
        msg?.let { loadingDialog?.setMessage(it) }
        loadingDialog?.show()
    }

    fun dismissLoadingDialog() {
        loadingDialog?.dismiss()
    }
}