package com.hqumath.demo.ui.fileupdown;

import androidx.lifecycle.MutableLiveData;

import com.hqumath.demo.base.BaseViewModel;
import com.hqumath.demo.net.HttpListener;
import com.hqumath.demo.net.download.DownloadListener;
import com.hqumath.demo.repository.MyModel;
import com.hqumath.demo.utils.FileUtil;

import java.io.File;

public class FileUpDownViewModel extends BaseViewModel {

    //下载
    public MutableLiveData<Boolean> isDownloading = new MutableLiveData<>();
    public MutableLiveData<Long> downloadProgress = new MutableLiveData<>();
    public long downloadMax;//总量
    public MutableLiveData<String> downloadResultCode = new MutableLiveData<>();//0成功；other失败
    public String downloadResultMsg;
    public File downloadResultData;

    //上传
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<String> uploadResultCode = new MutableLiveData<>();//0成功；other失败
    public String uploadResultMsg;
    public Object uploadResultData;

    public FileUpDownViewModel() {
        mModel = new MyModel();
    }

    public void download(String url) {
        isDownloading.setValue(true);
        File file = FileUtil.getFileFromUrl(url);
        ((MyModel) mModel).download(url, file, new DownloadListener() {
            @Override
            public void onSuccess(Object object) {
                isDownloading.postValue(false);
                downloadResultData = (File) object;
                downloadResultCode.postValue("0");
            }

            @Override
            public void onError(String errorMsg, String code) {
                isDownloading.postValue(false);
                downloadResultMsg = errorMsg;
                downloadResultCode.postValue(code);
            }

            @Override
            public void update(long read, long count) {
                downloadMax = count;
                downloadProgress.postValue(read);
            }
        });
    }

    public void upload(String key, File file) {
        isLoading.setValue(true);
        ((MyModel) mModel).upload(key, file, new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                isLoading.postValue(false);
                uploadResultData = object;
                uploadResultCode.postValue("0");
            }

            @Override
            public void onError(String errorMsg, String code) {
                isLoading.postValue(false);
                uploadResultMsg = errorMsg;
                uploadResultCode.postValue(code);
            }
        });
    }
}


