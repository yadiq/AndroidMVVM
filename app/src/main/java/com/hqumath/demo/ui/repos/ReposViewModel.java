package com.hqumath.demo.ui.repos;

import androidx.lifecycle.MutableLiveData;

import com.hqumath.demo.app.Constant;
import com.hqumath.demo.base.BaseViewModel;
import com.hqumath.demo.bean.ReposEntity;
import com.hqumath.demo.bean.UserInfoEntity;
import com.hqumath.demo.net.HttpListener;
import com.hqumath.demo.repository.MyModel;
import com.hqumath.demo.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************************
 * 文件名称: ReposViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/6/3 10:27
 * 文件描述: MyReposFragment和StarredFragment共用
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class ReposViewModel extends BaseViewModel {

    private final int pageSize = 10;//分页
    //MyRepos
    private long myReposPageIndex;//索引
    public MutableLiveData<String> myReposResultCode = new MutableLiveData<>();//0成功；other失败
    public String myReposResultMsg;
    public boolean myReposRefresh;//true 下拉刷新；false 上拉加载
    public boolean myReposNewEmpty;//true 增量为空；false 增量不为空
    public List<ReposEntity> myReposData = new ArrayList<>();//列表数据
    //Starred
    private long starredPageIndex;//索引
    public MutableLiveData<String> starredResultCode = new MutableLiveData<>();//0成功；other失败
    public String starredResultMsg;
    public boolean starredRefresh;//true 下拉刷新；false 上拉加载
    public boolean starredNewEmpty;//true 增量为空；false 增量不为空
    public List<ReposEntity> starredData = new ArrayList<>();//列表数据

    public ReposViewModel() {
        mModel = new MyModel();
    }

    /**
     * 获取列表
     *
     * @param isRefresh true 下拉刷新；false 上拉加载
     */
    public void getMyRepos(boolean isRefresh) {
        if (isRefresh) {
            myReposPageIndex = 1;
        }
        String userName = SPUtil.getInstance().getString(Constant.USER_NAME);
        ((MyModel) mModel).getMyRepos(userName, pageSize, myReposPageIndex, new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                List<ReposEntity> list = (List<ReposEntity>) object;
                myReposPageIndex++;//偏移量+1
                if (isRefresh) //下拉覆盖，上拉增量
                    myReposData.clear();
                if (!list.isEmpty())
                    myReposData.addAll(list);
                myReposRefresh = isRefresh;
                myReposNewEmpty = list.isEmpty();
                myReposResultCode.postValue("0");
            }

            @Override
            public void onError(String errorMsg, String code) {
                myReposResultMsg = errorMsg;
                myReposRefresh = isRefresh;
                myReposResultCode.postValue(code);
            }
        });
    }

    /**
     * 获取列表
     *
     * @param isRefresh true 下拉刷新；false 上拉加载
     */
    public void getStarred(boolean isRefresh) {
        if (isRefresh) {
            starredPageIndex = 1;
        }
        String userName = SPUtil.getInstance().getString(Constant.USER_NAME);
        ((MyModel) mModel).getStarred(userName, pageSize, starredPageIndex, new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                List<ReposEntity> list = (List<ReposEntity>) object;
                starredPageIndex++;//偏移量+1
                if (isRefresh) //下拉覆盖，上拉增量
                    starredData.clear();
                if (!list.isEmpty())
                    starredData.addAll(list);
                starredRefresh = isRefresh;
                starredNewEmpty = list.isEmpty();
                starredResultCode.postValue("0");
            }

            @Override
            public void onError(String errorMsg, String code) {
                starredResultMsg = errorMsg;
                starredRefresh = isRefresh;
                starredResultCode.postValue(code);
            }
        });
    }
}
