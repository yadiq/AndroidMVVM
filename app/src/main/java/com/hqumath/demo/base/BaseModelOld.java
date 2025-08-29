package com.hqumath.demo.base;

import androidx.annotation.NonNull;

import com.hqumath.demo.net.HandlerException;
import com.hqumath.demo.net.HttpListener;
import com.hqumath.demo.utils.FileUtil;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * ****************************************************************
 * 文件名称: BaseModel
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述: 防止MVP内存泄漏
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class BaseModelOld {
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();//管理订阅事件，用于主动取消网络请求

    //网络请求
    protected void sendRequest(Observable observable, HttpListener listener) {
        observable.subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread()) 在工作线程处理
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        if (compositeDisposable != null)
                            compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Object o) {
                        listener.onSuccess(o);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        HandlerException.ResponseThrowable throwable = HandlerException.handleException(e);
                        listener.onError(throwable.getMessage(), throwable.getCode());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    //下载请求
    protected void sendDownloadRequest(Observable observable, HttpListener listener, File file) {
        observable.subscribeOn(Schedulers.io())
                .map((Function<ResponseBody, File>) responseBody -> {
                    FileUtil.writeFile(responseBody, file);
                    return file;
                })
                //.observeOn(AndroidSchedulers.mainThread()) 在工作线程处理
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        if (compositeDisposable != null)
                            compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Object o) {
                        listener.onSuccess(o);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        HandlerException.ResponseThrowable throwable = HandlerException.handleException(e);
                        listener.onError(throwable.getMessage(), throwable.getCode());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //主动解除所有订阅者
    protected void dispose() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
    }
}
