package com.hqumath.demo.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.hqumath.demo.BuildConfig
import com.hqumath.demo.app.DataStoreKey
import com.hqumath.demo.base.BaseActivity
import com.hqumath.demo.databinding.ActivityLoginBinding
import com.hqumath.demo.ui.main.MainActivity
import com.hqumath.demo.utils.CommonUtil
import com.hqumath.demo.utils.DataStoreUtil

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun initContentView(savedInstanceState: Bundle?): View {
        //enableEdgeToEdge() 启用沉浸式布局
        binding = ActivityLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initListener() {
        binding.btnLogin.setOnClickListener {
            viewModel.login()
        }
    }

    override fun initData() {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        isLogin()
    }

    override fun initViewObservable() {
        viewModel.isLoading.observe(this) { b ->
            if (b) {
                showLoadingDialog()
            } else {
                dismissLoadingDialog()
            }
        }
        viewModel.loginResultCode.observe(this) { code ->
            when (code) {
                "0" -> {
                    CommonUtil.toast(viewModel.userName.value + "已登录")
                    startActivity(Intent(mContext, MainActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun isLogin() {
        val token = DataStoreUtil.getData(DataStoreKey.TOKEN, "")
        if (!TextUtils.isEmpty(token)) {
            startActivity(Intent(mContext, MainActivity::class.java))
            finish()
            return
        }
        val userName = DataStoreUtil.getData(DataStoreKey.USER_NAME, "")
        viewModel.userName.postValue(userName)

        //缺省的账号密码
        if (BuildConfig.DEBUG) {
            viewModel.userName.postValue("JakeWharton")
            viewModel.password.postValue("1234")
        }
    }
}