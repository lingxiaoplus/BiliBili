package com.bilibili.lingxiao.home.live

import com.bilibili.lingxiao.GlobalProperties
import com.camera.lingxiao.common.app.BaseTransation
import com.camera.lingxiao.common.example.HttpModle
import com.camera.lingxiao.common.http.ParseHelper
import com.camera.lingxiao.common.http.request.HttpRequest
import com.camera.lingxiao.common.observer.HttpRxCallback
import com.camera.lingxiao.common.utills.LogUtils
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.trello.rxlifecycle2.LifecycleProvider

class LiveTrans(mLifecycle: LifecycleProvider<*>) : BaseTransation(mLifecycle) {
    fun getLiveList(roomId: Int,callback : HttpRxCallback<Any>){
        request.clear()
        request.put(HttpRequest.API_URL,"/AppIndex/home")
        request.put("_device",GlobalProperties.DEVICE)
        request.put("appkey",GlobalProperties.APP_KEY)
        request.put("build",GlobalProperties.BUILD)
        request.put("device",GlobalProperties.DEVICE)
        request.put("mobi_app",GlobalProperties.MOBI_APP)
        request.put("platform",GlobalProperties.PLATFORM)
        request.put("room_id",roomId)
        request.put("scale",GlobalProperties.SCALE)
        request.put("src",GlobalProperties.SRC)
        request.put("trace_id",GlobalProperties.getTraceId())
        request.put("ts",GlobalProperties.getSystemTime())
        request.put("version",GlobalProperties.VERSION)

        LogUtils.d("屏幕像素---->" + GlobalProperties.SCALE)
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                LogUtils.d("获取到的数据" + element)
                var modle = Gson().fromJson(element,LiveData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })

        getRequest().request(HttpRequest.Method.GET,request,callback)
    }
}