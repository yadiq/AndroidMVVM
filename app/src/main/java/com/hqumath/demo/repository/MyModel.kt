package com.hqumath.demo.repository

import com.hqumath.demo.app.DataStoreKey
import com.hqumath.demo.base.BaseModel
import com.hqumath.demo.net.RetrofitClient
import com.hqumath.demo.utils.DataStoreUtil

/**
 * ****************************************************************
 * 文件名称: MyModel
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述: 数据层，数据的获取、存储和处理 https://juejin.cn/post/6844903812658888712
 * 注意事项:
 * 1.提供的数据业务层直接可用
 * 2.相同的接口不要多次实现
 * 3.方便自动化测试
 * ****************************************************************
 */
class MyModel : BaseModel() {
    /////////////////登录界面////////////////
//    fun login(
//        body: Map<String, String>,
//        onSuccess: (response: Any?) -> Unit,
//        onError: (errorMsg: String, code: String) -> Unit
//    ) {
//        sendRequest(
//            RetrofitClient.getInstance().apiService.login(body),
//            { response ->
//                val result = response as BaseResult<LoginBean>
//                //数据校验、处理
//                if (result.success) {
//                    DataStoreUtil.putData(DataStoreKey.USER_NAME, result.data.userInfo.username)
//                    DataStoreUtil.putData(DataStoreKey.TOKEN, result.data.token)
//                    onSuccess(response.data)
//                } else {
//                    onError(result.msg, result.code)
//                }
//            },
//            onError
//        )
//    }

    fun login(
        userName: String,
        onSuccess: (response: Any) -> Unit,
        onError: (errorMsg: String, code: String) -> Unit
    ) {
        sendRequest(
            RetrofitClient.getInstance().apiService.getUserInfo(userName),
            { response ->
                //数据校验、处理
                DataStoreUtil.putData(DataStoreKey.USER_NAME, userName)
                DataStoreUtil.putData(DataStoreKey.TOKEN, "token")
                onSuccess(response)
            },
            onError
        )
    }

    /////////////////主界面////////////////
    fun getFollowers(
        userName: String?,
        query: Map<String, String>,
        onSuccess: (response: Any?) -> Unit,
        onError: (errorMsg: String, code: String) -> Unit
    ) {
        sendRequest(
            RetrofitClient.getInstance().apiService.getFollowers(userName, query),
            { response ->
                //数据校验、处理
                onSuccess(response)
            },
            onError
        )
    }

    fun getMyRepos(
        userName: String,
        query: Map<String, String>,
        onSuccess: (response: Any) -> Unit,
        onError: (errorMsg: String, code: String) -> Unit
    ) {
        sendRequest(
            RetrofitClient.getInstance().apiService.getMyRepos(
                userName,
                query
            ),
            onSuccess,
            onError
        )
    }

    fun getStarred(
        userName: String,
        query: Map<String, String>,
        onSuccess: (response: Any) -> Unit,
        onError: (errorMsg: String, code: String) -> Unit
    ) {
        sendRequest(
            RetrofitClient.getInstance().apiService.getStarred(
                userName,
                query
            ),
            onSuccess,
            onError
        )
    }
}
