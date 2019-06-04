package com.hqumath.androidmvvm.data;

import com.hqumath.androidmvvm.utils.SPUtils;

/**
 * ****************************************************************
 * 文件名称: MyRepository
 * 作    者: Created by gyd
 * 创建时间: 2019/6/3 10:29
 * 文件描述: MVVM的Model层，统一模块的数据仓库，可配合Room框架使用
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class MyRepository {
    private volatile static MyRepository INSTANCE = null;

    private MyRepository() {
        //数据库Helper构建

    }

    public static MyRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (MyRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MyRepository();
                }
            }
        }
        return INSTANCE;
    }

    public void saveUserName(String userName) {
        SPUtils.getInstance().put("UserName", userName);
    }

    public String getUserName() {
        return SPUtils.getInstance().getString("UserName");
    }

    public void savePassword(String password) {
        SPUtils.getInstance().put("password", password);
    }

    public String getPassword() {
        return SPUtils.getInstance().getString("password");
    }

    public void saveToken(String token) {
        SPUtils.getInstance().put("token", token);
    }

    public String getToken() {
        return SPUtils.getInstance().getString("token");
    }
}