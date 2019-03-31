package com.camera.lingxiao.common.app

import com.camera.lingxiao.common.http.request.HttpRequest
import com.trello.rxlifecycle2.LifecycleProvider

import java.util.TreeMap

open class BaseTransation(protected var mLifecycle: LifecycleProvider<*>?) {
    protected var mHttpRequest: HttpRequest? = null
    protected var request: TreeMap<String, Any>

    init {
        mHttpRequest = HttpRequest()
        request = TreeMap()
    }

    protected fun getRequest(): HttpRequest {
        if (mHttpRequest == null) {
            mHttpRequest = HttpRequest()
        }
        return mHttpRequest as HttpRequest
    }
}
