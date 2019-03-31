package com.camera.lingxiao.common.http

import com.camera.lingxiao.common.utills.LogUtils
import com.camera.lingxiao.common.exception.ServerException
import com.camera.lingxiao.common.http.response.HttpResponse
import com.google.gson.Gson

import io.reactivex.functions.Function

/**
 * @author lingxiao
 * 数据的处理（截取数据）、数据类型转换（由图片path转为Bitmap）等
 * 配合map操作符
 */
class ServerResultFunction : Function<HttpResponse, Any> {
    override fun apply(httpResponse: HttpResponse): Any {
        //打印服务器回传结果
        LogUtils.e("ServerResultFunction: " + httpResponse.toString())
        if (!httpResponse.isSuccess) {
            throw ServerException(httpResponse.code, httpResponse.msg!!)
        }
        return Gson().toJson(httpResponse.results)
    }

}
