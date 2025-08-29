package com.hqumath.demo.repository;

import com.hqumath.demo.app.Constant;
import com.hqumath.demo.base.BaseModel;
import com.hqumath.demo.net.CreateRequestBodyUtil;
import com.hqumath.demo.net.HttpListener;
import com.hqumath.demo.net.RetrofitClient;
import com.hqumath.demo.net.download.DownloadListener;
import com.hqumath.demo.utils.SPUtil;

import java.io.File;

import okhttp3.MultipartBody;

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
public class MyModel extends BaseModel {
    //模拟登陆接口
    public void login(String userName, String passWord, HttpListener listener) {
        sendRequest(RetrofitClient.getInstance().getApiService().getUserInfo(userName), new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                //数据校验、处理
                SPUtil.getInstance().put(Constant.USER_NAME, userName);
                listener.onSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                listener.onError(errorMsg, code);
            }
        });
    }

    public void getUserInfo(String userName, HttpListener listener) {
        sendRequest(RetrofitClient.getInstance().getApiService().getUserInfo(userName), new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                listener.onSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                listener.onError(errorMsg, code);
            }
        });
    }

    public void getFollowers(String userName, int pageSize, long pageIndex, HttpListener listener) {
        sendRequest(RetrofitClient.getInstance().getApiService().getFollowers(userName, pageSize, pageIndex), new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                listener.onSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                listener.onError(errorMsg, code);
            }
        });
    }

    public void getMyRepos(String userName, int pageSize, long pageIndex, HttpListener listener) {
        sendRequest(RetrofitClient.getInstance().getApiService().getMyRepos(userName, pageSize, pageIndex), new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                listener.onSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                listener.onError(errorMsg, code);
            }
        });
    }

    public void getStarred(String userName, int pageSize, long pageIndex, HttpListener listener) {
        sendRequest(RetrofitClient.getInstance().getApiService().getStarred(userName, pageSize, pageIndex), new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                listener.onSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                listener.onError(errorMsg, code);
            }
        });
    }

    public void getReposInfo(String userName, String reposName, HttpListener listener) {
        sendRequest(RetrofitClient.getInstance().getApiService().getReposInfo(userName, reposName), new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                listener.onSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                listener.onError(errorMsg, code);
            }
        });
    }

    public void getCommits(String userName, String reposName, int pageSize, long pageIndex, HttpListener listener) {
        sendRequest(RetrofitClient.getInstance().getApiService().getCommits(userName, reposName, pageSize, pageIndex), new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                listener.onSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                listener.onError(errorMsg, code);
            }
        });
    }

    /**
     * 下载文件
     * @param url 地址
     * @param file 位置
     * @param listener
     */
    public void download(String url, File file, DownloadListener listener) {
        sendDownloadRequest(RetrofitClient.getInstance().getDownloadService(listener).download(url), new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                listener.onSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                listener.onError(errorMsg, code);
            }
        }, file);
    }

    //文件上传
    public void upload(String key, File file, HttpListener listener) {
        MultipartBody.Part part = CreateRequestBodyUtil.createRequestBody(key, file);
        sendRequest(RetrofitClient.getInstance().getApiService().upload(part), new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                listener.onSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                listener.onError(errorMsg, code);
            }
        });
    }
}
