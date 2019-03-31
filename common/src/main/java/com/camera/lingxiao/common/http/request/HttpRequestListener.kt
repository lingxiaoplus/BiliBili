package com.camera.lingxiao.common.http.request

/**
 * @author lingxiao
 * 请求监听接口
 */
interface HttpRequestListener {
    /**
     * 取消请求
     */
    fun cancel()

    /**
     * 请求被取消
     */
    fun onCanceled()
}
