package com.camera.lingxiao.common.observer

import android.text.TextUtils

import com.camera.lingxiao.common.exception.ApiException
import com.camera.lingxiao.common.exception.ExceptionEngine
import com.camera.lingxiao.common.http.ParseHelper
import com.camera.lingxiao.common.http.request.HttpRequestListener
import com.camera.lingxiao.common.http.RxActionManagerImpl
import com.camera.lingxiao.common.utills.LogUtils
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException

import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable

abstract class HttpRxCallback<T> : Observer<T>, HttpRequestListener {
    private var mTag: String? = null//请求标识
    private var parseHelper: ParseHelper? = null//数据解析

    /**
     * 是否已经处理
     *
     * @author ZhongDaFeng
     */
    val isDisposed: Boolean?
        get() = if (TextUtils.isEmpty(mTag)) {
            true
        } else RxActionManagerImpl.instance?.isDisposed(this.mTag!!)

    constructor() {
        this.mTag = System.currentTimeMillis().toString()
    }

    constructor(tag: String) {
        this.mTag = tag
    }

    /**
     * 手动取消请求
     */
    override fun cancel() {
        if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.instance?.cancel(this.mTag!!)
        }
    }

    /**
     * 请求被取消
     */
    override fun onCanceled() {
        onCancel()
    }

    override fun onSubscribe(d: Disposable) {
        if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.instance?.add(this.mTag!!, d)
        }
    }

    override fun onNext(@NonNull t: T) {
        if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.instance?.remove(this.mTag!!)
        }
        try {
            val jsonElement = JsonParser().parse(t as String)
            val res = parseHelper?.parse(jsonElement)
            if (parseHelper != null){
                onSuccess(res)
            }else{
                onSuccess(jsonElement)
            }
        } catch (jsonException: JsonSyntaxException) {
            jsonException.printStackTrace()
            LogUtils.e("JsonSyntaxException:" + jsonException.message)
            onError(ExceptionEngine.ANALYTIC_SERVER_DATA_ERROR, "解析错误")
        }

    }




    override fun onError(e: Throwable) {
        RxActionManagerImpl.instance?.remove(this.mTag!!)
        if (e is ApiException) {
            val code = e.code
            val msg = e.msg
            if (code == 1001) { //系统公告(示例)
                //此处在UI主线程
            } else if (code == 1002) {//token失效
                //处理对应的逻辑
            } else {//其他错误回调
                onError(code, msg)
            }
        } else {
            onError(ExceptionEngine.UN_KNOWN_ERROR, "未知错误")
        }
    }

    override fun onComplete() {

    }

    /**
     * 设置解析回调
     * @param parseHelper
     */
    fun setParseHelper(parseHelper: ParseHelper) {
        this.parseHelper = parseHelper
    }

    /**
     * 成功回调
     *
     * @param object
     */
    abstract fun onSuccess(res: Any?)
    /**
     * 失败回调
     *
     * @param code
     * @param desc
     */
    abstract fun onError(code: Int, desc: String?)

    /**
     * 取消回调
     */
    abstract fun onCancel()
}
