package com.hqumath.demo.ui.login;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.hqumath.demo.app.Constant;
import com.hqumath.demo.base.BaseViewModel;
import com.hqumath.demo.bean.UserInfoEntity;
import com.hqumath.demo.net.HttpListener;
import com.hqumath.demo.repository.MyModel;
import com.hqumath.demo.utils.SPUtil;

/**
 * ****************************************************************
 * 文件名称: LoginViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/6/3 10:27
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class LoginViewModel extends BaseViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    //登录
    public MutableLiveData<String> loginResultCode = new MutableLiveData<>();//0成功；other失败
    public String loginResultMsg;
    public UserInfoEntity loginResultData;

    public LoginViewModel() {
        mModel = new MyModel();
        //自动登录
        String name = SPUtil.getInstance().getString(Constant.USER_NAME);
        if (!TextUtils.isEmpty(name)) {
            loginResultCode.setValue("0");
        } else {
            //测试数据
            userName.setValue("JakeWharton");
            password.setValue("1234");
        }
    }

    public void login() {
        //模拟登陆接口
        isLoading.setValue(true);
        ((MyModel) mModel).login(userName.getValue(), password.getValue(), new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                isLoading.postValue(false);
                loginResultData = (UserInfoEntity) object;
                loginResultCode.postValue("0");
            }

            @Override
            public void onError(String errorMsg, String code) {
                isLoading.postValue(false);
                loginResultMsg = errorMsg;
                loginResultCode.postValue(code);
            }
        });
    }
}
