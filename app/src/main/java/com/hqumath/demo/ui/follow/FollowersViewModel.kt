package com.hqumath.demo.ui.follow

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hqumath.demo.app.DataStoreKey
import com.hqumath.demo.bean.UserInfoEntity
import com.hqumath.demo.repository.AppDatabase
import com.hqumath.demo.repository.MyModel
import com.hqumath.demo.utils.CommonUtil
import com.hqumath.demo.utils.DataStoreUtil
import com.hqumath.demo.utils.LogUtil

class FollowersViewModel(application: Application) : AndroidViewModel(application) {
    private var mModel: MyModel? = null
    var isLoading: MutableLiveData<Boolean> = MutableLiveData() //是否显示进度条

    private var pageIndex: Long = 1 //索引
    var listResultCode: MutableLiveData<String> = MutableLiveData() //网络请求错误号 0成功；other失败
    var listRefresh: Boolean = false //true 下拉刷新；false 上拉加载
    var listNewEmpty: Boolean = false //true 增量为空；false 增量不为空

    //var list: MutableList<ReposEntity> = ArrayList() //列表数据
    var daoList: LiveData<List<UserInfoEntity>> //列表数据。从数据库读取

    companion object {
        private const val pageSize = 20 //分页
    }

    init {
        mModel = MyModel()
        daoList = AppDatabase.getInstance().userInfoDao().loadAll() //UserInfoDao_Impl 内部做了线程切换
    }

    override fun onCleared() {
        super.onCleared()
        mModel?.dispose()
        mModel = null
    }

    /**
     * 获取列表
     *
     * @param isRefresh true 下拉刷新；false 上拉加载
     */
    fun getFollowers(isRefresh: Boolean) {
        if (isRefresh) {
            pageIndex = 1
        }
        val username = DataStoreUtil.getData(DataStoreKey.USER_NAME, "")
        val map = HashMap<String, String>()
        map["page"] = pageIndex.toString()
        map["per_page"] = pageSize.toString()
        mModel?.getFollowers(
            username,
            map,
            { response ->
                isLoading.postValue(false)
                val res = response as List<UserInfoEntity>
                pageIndex++ //偏移量+1
                if (isRefresh) { //下拉覆盖，上拉增量
                    //list.clear()
                    AppDatabase.getInstance().userInfoDao().deleteAll()
                }
                if (res.isNotEmpty()) {
                    //list.addAll(res)
                    AppDatabase.getInstance().userInfoDao().insertAll(res)
                }
                listRefresh = isRefresh //是否下拉
                listNewEmpty = res.isEmpty() //增量是否为空
                listResultCode.postValue("0") //错误码 0成功
            },
            { errorMsg, code ->
                isLoading.postValue(false)
                listRefresh = isRefresh //是否下拉
                listResultCode.postValue(code) //错误码 0成功
                CommonUtil.toast("获取列表失败\n$errorMsg")
            }
        )
    }
}