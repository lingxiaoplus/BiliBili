package com.bilibili.lingxiao

import com.bilibili.lingxiao.home.live.LiveData
import com.bilibili.lingxiao.play.model.CommentData
import com.bilibili.lingxiao.play.model.VideoDetailData
import com.bilibili.lingxiao.play.model.VideoRecoData
import com.camera.lingxiao.common.app.BaseTransation
import com.camera.lingxiao.common.http.ParseHelper
import com.camera.lingxiao.common.http.request.HttpRequest
import com.camera.lingxiao.common.observer.HttpRxCallback
import com.camera.lingxiao.common.utills.LogUtils
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.trello.rxlifecycle2.LifecycleProvider

class HttpTrans(mLifecycle: LifecycleProvider<*>) : BaseTransation(mLifecycle) {
    fun getLiveList(callback : HttpRxCallback<Any>){
        request.clear()
        request.put("_device",GlobalProperties.DEVICE)
        request.put("appkey",GlobalProperties.APP_KEY)
        request.put("build",GlobalProperties.BUILD)
        request.put("device",GlobalProperties.DEVICE)
        request.put("mobi_app",GlobalProperties.MOBI_APP)
        request.put("platform",GlobalProperties.PLATFORM)
        //request.put("room_id",roomId)
        request.put("scale",GlobalProperties.SCALE)
        request.put("src",GlobalProperties.SRC)
        request.put("trace_id",GlobalProperties.getTraceId())
        request.put("ts",GlobalProperties.getSystemTime())
        request.put("version",GlobalProperties.VERSION)

        LogUtils.d("屏幕像素---->" + GlobalProperties.SCALE)
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                LogUtils.d("获取到的数据" + element)
                var modle = Gson().fromJson(element, LiveData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        var url = GlobalProperties.LIVE_HOST + GlobalProperties.getUrlParamsByMap(request)
        LogUtils.d("拼接的url---->" + url)
        getRequest().requestFullPath(HttpRequest.Method.GET,url, mLifecycle,callback)
    }


    /**
     * @param page 页码
     * @param id 用户的av号
     * fav 是否读取会员收藏状态 (默认 false)
     */
    fun getDetailInfo(page:Int,id:String,callback : HttpRxCallback<Any>){
        request.clear()
        request.put("_device",GlobalProperties.DEVICE)
        request.put("appkey",GlobalProperties.APP_KEY)
        request.put("build",GlobalProperties.BUILD)
        request.put("device",GlobalProperties.DEVICE)
        request.put("mobi_app",GlobalProperties.MOBI_APP)
        request.put("platform",GlobalProperties.PLATFORM)
        request.put("scale",GlobalProperties.SCALE)
        request.put("src",GlobalProperties.SRC)
        request.put("trace_id",GlobalProperties.getTraceId())
        request.put("ts",GlobalProperties.getSystemTime())
        request.put("version",GlobalProperties.VERSION)
        request.put("page",page)
        request.put("id",id)

        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                LogUtils.d("获取到的数据" + element)
                var modle = Gson().fromJson(element, VideoDetailData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        var url = GlobalProperties.DETAIL_HOST + GlobalProperties.getUrlParamsByMap(request)
        LogUtils.d("拼接的url---->" + url)
        getRequest().requestFullPathWithoutCheck(HttpRequest.Method.GET,url, mLifecycle,callback)
    }

    /**
     * 获取视频播放详情下面的推荐列表
     * @param tid 分类编号 new排序为必填 其他为可选
     * @param page 结果分页选择 默认为第1页
     * @param pagesize 单页返回的记录条数，最大不超过100，默认为30
     * @param order 排序方式 default new review hot damku comment promote
     */
    fun getRecommendList(tid:Int,page:Int,pagesize:Int,order:String,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("tid",tid)
        request.put("page",page)
        request.put("pagesize",pagesize)
        request.put("order",order)
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                LogUtils.d("获取到的数据" + element)
                var modle = Gson().fromJson(element, VideoRecoData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        var url = GlobalProperties.COMMEND_VIDEO_HOST + GlobalProperties.getUrlParamsByMap(request)
        LogUtils.d("拼接的url---->" + url)
        getRequest().requestFullPathWithoutCheck(HttpRequest.Method.GET,url, mLifecycle,callback)
    }

    /**
     * @param oid av号
     * @param page 第几页
     */
    fun getComment(oid:String,page:Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("appkey",GlobalProperties.APP_KEY)
        request.put("build",GlobalProperties.BUILD)
        request.put("device",GlobalProperties.DEVICE)
        request.put("mobi_app",GlobalProperties.MOBI_APP)
        request.put("platform",GlobalProperties.PLATFORM)
        request.put("pn",page)
        request.put("ps",20)
        request.put("sort",0)
        request.put("type",1)
        request.put("oid",oid)
        request.put("trace_id",GlobalProperties.getTraceId())
        request.put("ts",GlobalProperties.getSystemTime())
        request.put("version",GlobalProperties.VERSION)

        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                var modle = Gson().fromJson(element, CommentData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        var url = GlobalProperties.COMMENT_HOST + GlobalProperties.getUrlParamsByMap(request)
        LogUtils.d("拼接的评论url---->" + url)
        getRequest().requestFullPath(HttpRequest.Method.GET,url, mLifecycle,callback)
    }
}