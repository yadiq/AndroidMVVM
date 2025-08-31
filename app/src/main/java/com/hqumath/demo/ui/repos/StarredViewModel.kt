package com.hqumath.demo.ui.repos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hqumath.demo.app.DataStoreKey
import com.hqumath.demo.bean.ReposEntity
import com.hqumath.demo.repository.MyModel
import com.hqumath.demo.utils.CommonUtil
import com.hqumath.demo.utils.DataStoreUtil

class StarredViewModel(application: Application) : AndroidViewModel(application) {

    private var mModel: MyModel? = null
    var isLoading: MutableLiveData<Boolean> = MutableLiveData() //是否显示进度条

    private var pageIndex: Long = 0 //索引
    var listResultCode: MutableLiveData<String> = MutableLiveData() //网络请求错误号 0成功；other失败
    var listRefresh: Boolean = false //true 下拉刷新；false 上拉加载
    var listNewEmpty: Boolean = false //true 增量为空；false 增量不为空
    var list: MutableList<ReposEntity> = ArrayList() //列表数据

    companion object {
        private const val pageSize = 10 //分页
    }

    init {
        mModel = MyModel()
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
    fun getStarred(isRefresh: Boolean) {
        if (isRefresh) {
            pageIndex = 1
        }
        val userName = DataStoreUtil.getData(DataStoreKey.USER_NAME, "")
        val map = HashMap<String, String>()
        map["page"] = pageIndex.toString()
        map["per_page"] = pageSize.toString()
        mModel?.getStarred(
            userName,
            map,
            { response ->
                isLoading.postValue(false)
                val res = response as List<ReposEntity>
                pageIndex++ //偏移量+1
                if (isRefresh)  //下拉覆盖，上拉增量
                    list.clear()
                if (res.isNotEmpty())
                    list.addAll(res)

                listRefresh = isRefresh //是否下拉
                listNewEmpty = list.isEmpty() //增量是否为空
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