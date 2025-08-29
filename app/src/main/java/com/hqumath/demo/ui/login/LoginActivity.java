package com.hqumath.demo.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.lifecycle.ViewModelProvider;

import com.hqumath.demo.R;
import com.hqumath.demo.base.BaseActivityOld;
import com.hqumath.demo.databinding.ActivityLoginBinding;
import com.hqumath.demo.ui.main.MainActivity;
import com.hqumath.demo.utils.CommonUtil;

/**
 * ****************************************************************
 * 文件名称: LoginActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/6/3 10:26
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class LoginActivity extends BaseActivityOld {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected View initContentView(Bundle savedInstanceState) {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
        binding.etPwd.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                loginRequest();
                return true;
            }
            return false;
        });
        binding.btnLogin.setOnClickListener(v -> {
            loginRequest();
        });
    }

    @Override
    protected void initData() {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setViewModel(viewModel);
    }

    @Override
    protected void initViewObservable() {
        viewModel.loginResultCode.observe(this, code -> {
            if (code.equals("0")) {
                if (viewModel.loginResultData != null) {
                    CommonUtil.toast(viewModel.loginResultData.getName() + "已登录");
                }
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
            } else {
                if (!TextUtils.isEmpty(viewModel.loginResultMsg)) {
                    CommonUtil.toast(viewModel.loginResultMsg);
                }
                binding.btnLogin.setEnabled(true);
            }
        });
        viewModel.isLoading.observe(this, b -> {
            if (b) {
                showProgressDialog("loading");
            } else {
                dismissProgressDialog();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (viewModel != null) {
            viewModel.dispose();
            viewModel = null;
        }
        super.onDestroy();
    }

    /**
     * 登录请求
     */
    private void loginRequest() {
        boolean valid = true;
        if (TextUtils.isEmpty(viewModel.userName.getValue())) {
            valid = false;
            binding.llName.setError(getString(R.string.user_name_warning));
        } else {
            binding.llName.setErrorEnabled(false);
        }
        if (TextUtils.isEmpty(viewModel.password.getValue())) {
            valid = false;
            binding.llPwd.setError(getString(R.string.password_warning));
        } else {
            binding.llPwd.setErrorEnabled(false);
        }
        if (valid) {
            binding.btnLogin.setEnabled(false);
            viewModel.login();
        }
    }
}
