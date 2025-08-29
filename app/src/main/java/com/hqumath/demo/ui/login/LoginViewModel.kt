package com.hqumath.demo.ui.login

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hqumath.demo.repository.MyModel
import com.hqumath.demo.repository.MyModelOld
import com.hqumath.demo.utils.CommonUtil

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private var mModel: MyModel? = null

    var isLoading: MutableLiveData<Boolean> = MutableLiveData() //是否显示进度条
    var loginResultCode: MutableLiveData<String> = MutableLiveData() //登录结果 code 0成功; other失败
    var userName: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

    init {
        mModel = MyModel()
    }

    override fun onCleared() {
        super.onCleared()
        mModel?.dispose()
        mModel = null
    }

    /**
     * 登录
     */
    fun login() {
        if (TextUtils.isEmpty(userName.value)) {
            CommonUtil.toast("请输入用户名")
            return
        }
        if (TextUtils.isEmpty(password.value)) {
            CommonUtil.toast("请输入密码")
            return
        }
        //val body = HashMap<String, String>()
        //body["username"] = userName.value!!
        //body["password"] = AESUtils.encrypt(password.value) //aes加密密码

        isLoading.postValue(true)
        mModel?.login(
            userName.value!!,
            { response ->
                isLoading.postValue(false)
                loginResultCode.postValue("0")
            },
            { errorMsg, code ->
                isLoading.postValue(false)
                loginResultCode.postValue(code)
                CommonUtil.toast("登录失败\n$errorMsg")
            }
        )
    }

}