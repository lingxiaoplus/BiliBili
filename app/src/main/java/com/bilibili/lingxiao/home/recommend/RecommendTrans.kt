package com.bilibili.lingxiao.home.recommend

import com.bilibili.lingxiao.GlobalProperties
import com.bilibili.lingxiao.home.recommend.model.RecommendData
import com.camera.lingxiao.common.app.BaseTransation
import com.camera.lingxiao.common.http.ParseHelper
import com.camera.lingxiao.common.http.request.HttpRequest
import com.camera.lingxiao.common.observer.HttpRxCallback
import com.camera.lingxiao.common.utills.LogUtils
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.trello.rxlifecycle2.LifecycleProvider

class RecommendTrans(mLifecycle: LifecycleProvider<*>) :BaseTransation(mLifecycle){
    private val STYLE = 2
    private var loginEvent: Int = 0
    private var openEvent: String = ""
    private var pull: Boolean = false
    private var idx: Int = 0
    val STATE_NORMAL = 0
    val STATE_INITIAL = 1  //初始化
    val STATE_REFRESHING = 2  //下拉刷新
    val STATE_LOAD_MORE = 3  //上拉加载   没有轮播图
    private val LOGIN_EVENT_NORMAL = 0
    private val LOGIN_EVENT_INITIAL = 1
    private val OPEN_EVENT_NULL = ""
    private val OPEN_EVENT_COLD = "cold"
    fun getRecommendList(operationState:Int,callback : HttpRxCallback<Any>){
        when (operationState) {
            STATE_INITIAL -> {
                loginEvent = LOGIN_EVENT_INITIAL
                openEvent = OPEN_EVENT_COLD
                pull = true
                idx = 0
            }
            STATE_REFRESHING -> {
                loginEvent = LOGIN_EVENT_NORMAL
                openEvent = OPEN_EVENT_NULL
                pull = true
                idx = 1499589063
            }
            STATE_LOAD_MORE -> {
                loginEvent = LOGIN_EVENT_NORMAL
                openEvent = OPEN_EVENT_NULL
                pull = false
                idx = 1499655142
            }
        }
        request.clear()
        request.put("appkey", GlobalProperties.APP_KEY)
        request.put("build", GlobalProperties.BUILD)
        request.put("idx",idx)
        request.put("login_event",loginEvent)
        request.put("mobi_app", GlobalProperties.MOBI_APP)
        request.put("network", GlobalProperties.NETWORK_WIFI)
        request.put("open_event", openEvent)
        request.put("platform", GlobalProperties.PLATFORM)
        request.put("pull",pull)
        request.put("style",STYLE)
        request.put("ts", GlobalProperties.getSystemTime())


        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {

                val type = object : TypeToken<List<RecommendData>>() {}.getType()
                var gson = Gson()
                var modle = gson.fromJson<List<RecommendData>>(element,type)
                //var modle = Gson().fromJson(element, type)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        var url = GlobalProperties.COMMEND_HOST + GlobalProperties.getUrlParamsByMap(request)
        LogUtils.d("拼接的url---->" + url)
        getRequest().requestFullPath(HttpRequest.Method.GET,GlobalProperties.COMMEND_HOST,request, mLifecycle,callback)
    }
}