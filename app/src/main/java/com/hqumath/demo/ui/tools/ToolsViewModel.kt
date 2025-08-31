package com.tgdz.belt.ui.mine

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hqumath.demo.repository.MyModel
import com.hqumath.demo.utils.CommonUtil
import com.king.app.updater.AppUpdater
import com.king.app.updater.UpdateConfig
import com.king.app.updater.callback.UpdateCallback
import com.king.app.updater.http.OkHttpManager
import java.io.File

class ToolsViewModel(application: Application) : AndroidViewModel(application) {
    private var mModel: MyModel? = null
    var isLoading: MutableLiveData<Boolean> = MutableLiveData() //是否显示进度条

    private var mAppUpdater: AppUpdater? = null
    var showDownloadDialog = MutableLiveData(false) //是否显示下载弹窗
    var mProgress = MutableLiveData(0L) //下载进度
    var mProgressTotal = 0L //总下载数量

    init {
        mModel = MyModel()
    }

    override fun onCleared() {
        super.onCleared()
        mModel?.dispose()
        mModel = null
    }

    /**
     * app升级
     */
    fun appUpdate() {
        if (mAppUpdater == null) {
            val sbUrl = "http://cps.yingyonghui.com/cps/yyh/channel/ac.union.m2/com.yingyonghui.market_1_30063293.apk";
            val updateConfig = UpdateConfig()
            with(updateConfig){
                filename = "demo.apk"
                url = sbUrl
                isShowNotification = false
            }
            mAppUpdater = AppUpdater(getApplication(), updateConfig)
            mAppUpdater?.setHttpManager(OkHttpManager.getInstance())
            mAppUpdater?.setUpdateCallback(object : UpdateCallback {
                override fun onDownloading(isDownloading: Boolean) {
                    if (isDownloading) {
                        CommonUtil.toast("已经在下载中,请勿重复下载。")
                    } else {
                        showDownloadDialog.postValue(true)
                    }
                }

                override fun onStart(url: String) {
                }

                override fun onProgress(progress: Long, total: Long, isChanged: Boolean) {
                    if (isChanged) {
                        mProgressTotal = total
                        mProgress.postValue(progress)
                    }
                }

                override fun onFinish(file: File) {
                    showDownloadDialog.postValue(false)
                    //CommonUtil.toast("下载完成")
                }

                override fun onError(e: Exception) {
                    showDownloadDialog.postValue(false)
                    CommonUtil.toast("下载失败")
                }

                override fun onCancel() {
                    showDownloadDialog.postValue(false)
                    //CommonUtil.toast("取消下载")
                }
            })
        }
        mAppUpdater?.start()
    }
}