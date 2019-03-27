package com.camera.lingxiao.common.example

import com.camera.lingxiao.common.app.BaseActivity
import com.camera.lingxiao.common.app.BaseTransation
import com.camera.lingxiao.common.http.ParseHelper
import com.camera.lingxiao.common.http.request.HttpRequest
import com.camera.lingxiao.common.observer.HttpRxCallback
import com.camera.lingxiao.common.utills.LogUtils
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.trello.rxlifecycle2.LifecycleProvider

class HttpTrans(mLifecycle: LifecycleProvider<*>) : BaseTransation(mLifecycle) {
    //http://gank.io/api/random/data/Android/20
    fun getResult(callback : HttpRxCallback<Any>){
        request.clear()
        request.put(HttpRequest.API_URL,"data/Android/20")
        callback.setParseHelper(object : ParseHelper{
            override fun parse(element: JsonElement): Any? {
                LogUtils.d("获取到的数据" + element)
                var modle = Gson().fromJson(element,Array<HttpModle>::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        getRequest().request(HttpRequest.Method.GET,request,callback)
    }
}