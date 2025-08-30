package com.hqumath.demo.base

import com.hqumath.demo.net.HandlerException
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

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
open class BaseModel {
    protected var compositeDisposable: CompositeDisposable? =
        CompositeDisposable() //管理订阅事件，用于主动取消网络请求

    //网络请求
    protected fun sendRequest(
        observable: Observable<*>,
        onSuccess: (response: Any) -> Unit,
        onError: (errorMsg: String, code: String) -> Unit
    ) {
        observable.subscribeOn(Schedulers.io())
            //.observeOn(AndroidSchedulers.mainThread()) 在工作线程处理
            .subscribe(object : Observer<Any> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable?.add(d)
                }

                override fun onNext(o: Any) {
                    onSuccess(o)
                }

                override fun onError(e: Throwable) {
                    val throwable = HandlerException.handleException(e)
                    onError(throwable.message, throwable.getCode())
                }

                override fun onComplete() {
                }
            })
    }

    //下载请求
//    protected fun sendDownloadRequest(
//        observable: Observable<*>,
//        listener: HttpListener,
//        file: File?
//    ) {
//        observable.subscribeOn(Schedulers.io())
//            .map(Function { responseBody: ResponseBody? ->
//                FileUtil.writeFile(responseBody, file)
//                file
//            })
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : Observer<Any> {
//                override fun onSubscribe(d: Disposable) {
//                    if (compositeDisposable != null) compositeDisposable!!.add(d)
//                }
//
//                override fun onNext(o: Any) {
//                    listener.onSuccess(o)
//                }
//
//                override fun onError(e: Throwable) {
//                    val throwable = HandlerException.handleException(e)
//                    listener.onError(throwable.message, throwable.getCode())
//                }
//
//                override fun onComplete() {
//                }
//            })
//    }

    //主动解除所有订阅者
    fun dispose() {
        compositeDisposable?.dispose()
        compositeDisposable = null
    }
}
