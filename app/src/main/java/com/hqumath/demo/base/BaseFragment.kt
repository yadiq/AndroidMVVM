package com.hqumath.demo.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2025/7/27 22:12
 * 文件描述:
 * 注意事项:
 * ****************************************************************
 */
abstract class BaseFragment : Fragment() {
    lateinit var mContext: Activity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = initContentView(inflater, container, savedInstanceState)
        //事件监听
        initListener()
        //初始化数据
        initData()
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable()
        return rootView
    }

    protected abstract fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View

    protected abstract fun initListener()

    protected abstract fun initData()

    protected abstract fun initViewObservable()

    protected fun showLoadingDialog(msg: String? = null) {
        val baseActivity = mContext as? BaseActivity
        baseActivity?.showLoadingDialog(msg)
    }

    protected fun dismissLoadingDialog() {
        val baseActivity = mContext as? BaseActivity
        baseActivity?.dismissLoadingDialog()
    }
}