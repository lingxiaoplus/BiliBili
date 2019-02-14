package com.camera.lingxiao.common.observer


import android.text.TextUtils

import com.camera.lingxiao.common.exception.ApiException
import com.camera.lingxiao.common.exception.ExceptionEngine
import com.camera.lingxiao.common.http.request.HttpRequestListener
import com.camera.lingxiao.common.http.RxActionManagerImpl

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class HttpRxObserver<T>(
    private val mTag: String//请求标识
) : Observer<T>, HttpRequestListener {

    val isDisposed: Boolean?
        get() = if (TextUtils.isEmpty(mTag)) {
            true
        } else RxActionManagerImpl.instance?.isDisposed(mTag)

    override fun cancel() {
        if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.instance?.cancel(mTag)
        }
    }

    override fun onCanceled() {

    }

    /**
     * 添加请求标识
     * @param d
     */
    override fun onSubscribe(d: Disposable) {
        if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.instance?.add(mTag, d)
        }
        onStart(d)
    }

    /**
     * 移除请求
     * @param t
     */
    override fun onNext(t: T) {
        if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.instance?.remove(mTag)
        }
        onSuccess(t)
    }

    /**
     * 封装错误/异常处理 移除请求
     * @param e
     */
    override fun onError(e: Throwable) {
        RxActionManagerImpl.instance?.remove(mTag)
        if (e is ApiException) {
            onError(e)
        } else {
            onError(ApiException(e, ExceptionEngine.UN_KNOWN_ERROR))
        }
    }

    override fun onComplete() {

    }

    protected abstract fun onStart(d: Disposable)

    /**
     * 错误/异常回调
     */
    protected abstract fun onError(e: ApiException)

    /**
     * 成功回调
     */
    protected abstract fun onSuccess(response: T)
}
