package com.camera.lingxiao.common.http

import com.camera.lingxiao.common.exception.ExceptionEngine
import com.camera.lingxiao.common.utills.LogUtils

import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * @author lingxiao
 * 使用onErrorResumeNext(new HttpResultFunction<>())操作符
 * 对Retrofit网络请求抛出的Exception进行处理
 * @param <T>
</T> */
class HttpResultFunction<T> : Function<Throwable, Observable<T>> {

    @Throws(Exception::class)
    override fun apply(throwable: Throwable): Observable<T> {
        //打印具体错误
        LogUtils.e("HttpResultFunction:$throwable")
        return Observable.error(ExceptionEngine.handlerException(throwable))
    }
}
