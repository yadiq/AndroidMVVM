package com.hqumath.demo.ui.follow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hqumath.demo.app.Constant;
import com.hqumath.demo.base.BaseViewModel;
import com.hqumath.demo.bean.UserInfoEntity;
import com.hqumath.demo.net.HttpListener;
import com.hqumath.demo.repository.AppDatabase;
import com.hqumath.demo.repository.MyModelOld;
import com.hqumath.demo.utils.SPUtil;

import java.util.List;

/**
 * ****************************************************************
 * 文件名称: LoginViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/6/3 10:27
 * 文件描述: 使用 Room 持久化存储列表数据，Network => DB => LiveData => RecyclerView
 * 注意事项: 自动加载数据库中全部数据，不能逐渐加载
 * 版权声明:
 * ****************************************************************
 */
public class FollowersViewModel extends BaseViewModel {

    private final int pageSize = 10;//分页
    private long pageIndex;//索引

    public MutableLiveData<String> followersResultCode = new MutableLiveData<>();//列表请求 0成功；other失败
    public String followersResultMsg;
    public boolean followersRefresh;//true 下拉刷新；false 上拉加载
    public boolean followersNewEmpty;//true 增量为空；false 增量不为空
    public LiveData<List<UserInfoEntity>> mData;//列表数据

    public FollowersViewModel() {
        mModel = new MyModelOld();
        mData = AppDatabase.getInstance().userInfoDao().loadAll();//UserInfoDao_Impl 内部做了线程切换
    }

    @Override
    public void dispose() {
        super.dispose();
        AppDatabase.getInstance().close();//关闭数据库
    }

    /**
     * 获取列表
     *
     * @param isRefresh true 下拉刷新；false 上拉加载
     */
    public void getFollowers(boolean isRefresh) {
        if (isRefresh) {
            pageIndex = 1;
        }
        String userName = SPUtil.getInstance().getString(Constant.USER_NAME);
        ((MyModelOld) mModel).getFollowers(userName, pageSize, pageIndex, new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                List<UserInfoEntity> list = (List<UserInfoEntity>) object;
                pageIndex++;//偏移量+1
                if (isRefresh) {//下拉覆盖，上拉增量
                    AppDatabase.getInstance().userInfoDao().deleteAll();
                }
                if (!list.isEmpty()) {
                    AppDatabase.getInstance().userInfoDao().insertAll(list);
                }
                followersRefresh = isRefresh;
                followersNewEmpty = list.isEmpty();
                followersResultCode.postValue("0");
            }

            @Override
            public void onError(String errorMsg, String code) {
                followersResultMsg = errorMsg;
                followersRefresh = isRefresh;
                followersResultCode.postValue(code);
            }
        });
    }
}
