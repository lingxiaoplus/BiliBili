package com.bilibili.lingxiao

import android.util.Log
import com.bilibili.lingxiao.home.category.model.RegionData
import com.bilibili.lingxiao.home.category.model.RegionRecommendData
import com.bilibili.lingxiao.home.live.model.*
import com.bilibili.lingxiao.home.mikan.model.MiKanFallData
import com.bilibili.lingxiao.home.mikan.model.MiKanRecommendData
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
import com.google.gson.reflect.TypeToken
import com.trello.rxlifecycle2.LifecycleProvider

class HttpTrans(mLifecycle: LifecycleProvider<*>) : BaseTransation(mLifecycle) {
    private var debug = true
    private val TAG = HttpTrans::class.java.simpleName
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


        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                LogUtils.d("获取到的数据" + element)
                var modle = Gson().fromJson(element, LiveData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.LIVE_HOST + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"拼接的url---->$url, 屏幕像素---->${GlobalProperties.SCALE}" )
        }
        getRequest().requestFullPath(HttpRequest.Method.GET,GlobalProperties.LIVE_HOST, request, mLifecycle,callback)
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
        if (debug){
            var url = GlobalProperties.DETAIL_HOST + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"拼接的url---->$url")
        }
        getRequest().requestFullPathWithoutCheck(HttpRequest.Method.GET,GlobalProperties.DETAIL_HOST,request, mLifecycle,callback)
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
        if (debug){
            var url = GlobalProperties.COMMEND_VIDEO_HOST + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"拼接视频播放详情下面的推荐列表的url---->" + url)
        }
        getRequest().requestFullPathWithoutCheck(HttpRequest.Method.GET,GlobalProperties.COMMEND_VIDEO_HOST,request, mLifecycle,callback)
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
        if (debug){
            var url = GlobalProperties.COMMENT_HOST + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"拼接的评论url---->$url")
        }
        getRequest().requestFullPath(HttpRequest.Method.GET,GlobalProperties.COMMENT_HOST,request, mLifecycle,callback)
    }

    /**
     * 获取国内，日本推荐的番剧
     * https://bangumi.bilibili.com/appindex/follow_index_page?appkey=1d8b6e7d45233436&build=502000&mobi_app=android&platform=android&ts=1493967208000&sign=3eff79d895af9cf800016%20fe8f6bc6ce0
     */
    fun getBanGumiRecommend(callback: HttpRxCallback<Any>){
        request.clear()
        request.put("appkey",GlobalProperties.APP_KEY)
        request.put("build",GlobalProperties.BUILD)
        request.put("device",GlobalProperties.DEVICE)
        request.put("mobi_app",GlobalProperties.MOBI_APP)
        request.put("platform",GlobalProperties.PLATFORM)
        request.put("ts",GlobalProperties.getSystemTime())
        request.put("version",GlobalProperties.VERSION)
        //request.put("sign",GlobalProperties.)
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                var modle = Gson().fromJson(element, MiKanRecommendData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.BANGUMI_CN_AND_JP_HOST + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"拼接番剧的url---->$url")
        }
        getRequest().requestFullPathWithoutCheck(HttpRequest.Method.GET,GlobalProperties.BANGUMI_CN_AND_JP_HOST, request,mLifecycle,callback)
    }

    /**
     * 获取编辑推荐的番剧
     * https://bangumi.bilibili.com/appindex/follow_index_fall?appkey=1d8b6e7d45233436&build=509000&cursor=0&mobi_app=android&platform=android&ts=1499937514&sign=2dae626fed99d43abbc9d09cfd124641
     */
    fun getBanGumiFall(cursor:Long,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("appkey",GlobalProperties.APP_KEY)
        request.put("build",GlobalProperties.BUILD)
        request.put("cursor",cursor)
        request.put("mobi_app",GlobalProperties.MOBI_APP)
        request.put("platform",GlobalProperties.PLATFORM)
        request.put("ts",GlobalProperties.getSystemTime())
        request.put("version",GlobalProperties.VERSION)
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                var modle = Gson().fromJson(element, MiKanFallData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.BANGUMI_FALL_HOST + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"拼接编辑推荐的番剧的url---->$url")
        }
        getRequest().requestFullPathWithoutCheck(HttpRequest.Method.GET,GlobalProperties.BANGUMI_FALL_HOST,request, mLifecycle,callback)
    }


    fun getRegion(callback: HttpRxCallback<Any>){
        request.clear()
        request.put("build",GlobalProperties.BUILD)
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                val type = object : TypeToken<List<RegionData.Data>>() {}.getType()
                var modle:List<RegionData.Data> = Gson().fromJson<List<RegionData.Data>>(element, type)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.CATEGORY_HOST + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"拼接分区的url---->$url")
        }
        getRequest().requestFullPath(HttpRequest.Method.GET,GlobalProperties.CATEGORY_HOST,request, mLifecycle,callback)
    }

    /**
     * 分区推荐信息
     */
    fun getRegionRecommend(callback: HttpRxCallback<Any>){
        request.clear()
        request.put("appkey",GlobalProperties.APP_KEY)
        request.put("build",GlobalProperties.BUILD)
        request.put("mobi_app",GlobalProperties.MOBI_APP)
        request.put("platform",GlobalProperties.PLATFORM)
        request.put("ts",GlobalProperties.getSystemTime())
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                val type = object : TypeToken<List<RegionRecommendData.Data>>() {}.getType()
                var modle:List<RegionRecommendData.Data> = Gson().fromJson<List<RegionRecommendData.Data>>(element, type)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.CATEGORY_RECOMMEND_HOST + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"拼接分区推荐的url---->$url")
        }

        getRequest().requestFullPath(HttpRequest.Method.GET, GlobalProperties.CATEGORY_RECOMMEND_HOST,request, mLifecycle, callback)
    }

    /**
     * 获取直播间up的信息，主要是uid的获取
     */
    fun getLiveUpInfo(roomId:Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("id",roomId)
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                var modle = Gson().fromJson(element,LiveUpData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.LIVE_UP_INFO + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"获取直播up信息的url---->$url")
        }
        getRequest().requestFullPath(HttpRequest.Method.POST, GlobalProperties.LIVE_UP_INFO, request,mLifecycle, callback)
    }


    /**
     * 获取直播间用户的信息
     * @param ruid  直播间up主的uid
     * @param uid 直播间用户的uid
     */
    fun getLiveUserInfo(ruid:Int,uid :Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("ruid",ruid)
        request.put("uid",uid)
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                var modle = Gson().fromJson(element,LiveUpData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.LIVE_USER_INFO + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"获取直播用户信息的url---->$url")
        }
        getRequest().requestFullPath(HttpRequest.Method.POST, GlobalProperties.LIVE_USER_INFO, request,mLifecycle, callback)
    }

    /**
     * 获取金瓜榜
     */
    fun getLiveGoldList(roomId:Int,ruid:Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("next_offset","0")
        request.put("room_id",roomId)
        request.put("ruid",ruid)
        request.put("rank_type","gold-rank")
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                //val type = object : TypeToken<List<FansGoldListData.FansInfo>>() {}.getType()
                //var modle:List<FansGoldListData.FansInfo> = Gson().fromJson<List<FansGoldListData.FansInfo>>(element, type)
                var modle: FansGoldListData = Gson().fromJson(element, FansGoldListData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.LIVE_UP_GOLD_LIST + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"获取直播up金瓜榜的url---->$url")
        }
        getRequest().requestFullPath(HttpRequest.Method.GET, GlobalProperties.LIVE_UP_GOLD_LIST, request,mLifecycle, callback)
    }

    /**
     * 获取礼物榜
     */
    fun getLiveToDayList(roomId:Int,ruid:Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("next_offset",0)
        request.put("room_id",roomId)
        request.put("ruid",ruid)
        request.put("rank_type","today-rank")
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                //val type = object : TypeToken<List<FansGoldListData.FansInfo>>() {}.getType()
                //var modle:List<FansGoldListData.FansInfo> = Gson().fromJson<List<FansGoldListData.FansInfo>>(element, type)
                var modle: FansGoldListData = Gson().fromJson(element, FansGoldListData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.LIVE_UP_GOLD_LIST + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"获取直播up礼物榜的url---->$url")
        }
        getRequest().requestFullPath(HttpRequest.Method.GET, GlobalProperties.LIVE_UP_GOLD_LIST, request,mLifecycle, callback)
    }


    /**
     * 获取粉丝榜
     */
    fun getLiveFansList(roomId:Int,ruid:Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("page",1)
        request.put("roomid",roomId)
        request.put("ruid",ruid)
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                //val type = object : TypeToken<List<FansGoldListData.FansInfo>>() {}.getType()
                //var modle:List<FansGoldListData.FansInfo> = Gson().fromJson<List<FansGoldListData.FansInfo>>(element, type)
                var modle: FansGoldListData = Gson().fromJson(element, FansGoldListData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.LIVE_FANS_LIST + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"获取直播up粉丝榜的url---->$url")
        }
        getRequest().requestFullPath(HttpRequest.Method.GET, GlobalProperties.LIVE_FANS_LIST, request,mLifecycle, callback)
    }

    /**
     * 获取大航海
     * @param page 1 页码
     * @param pageSize 20 个数
     */
    fun getLiveFleetList(page:Int,pageSize:Int,ruid:Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("page",page)
        request.put("page_size",pageSize)
        request.put("ruid",ruid)
        request.put("mobi_app",GlobalProperties.MOBI_APP)
        request.put("ts",GlobalProperties.getSystemTime())
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                var modle: FleetListData = Gson().fromJson(element, FleetListData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.LIVE_FLEET_LIST + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"获取直播up大航海的url---->$url")
        }
        getRequest().requestFullPath(HttpRequest.Method.GET, GlobalProperties.LIVE_FLEET_LIST, request,mLifecycle, callback)
    }

    /**
     * 直播up主的视频投稿
     */
    fun getLiveUpVideoList(page:Int,pageSize:Int,ruid:Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("pn",page)
        request.put("ps",pageSize)
        request.put("mid",ruid)
        request.put("mobi_app",GlobalProperties.MOBI_APP)
        request.put("ts",GlobalProperties.getSystemTime())
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                val type = object : TypeToken<List<UpInfoData>>() {}.getType()
                var modle:List<UpInfoData> = Gson().fromJson<List<UpInfoData>>(element, type)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.LIVE_UP_VIDEO_LIST + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"获取直播up视频投稿的url---->$url")
        }
        getRequest().requestFullPath(HttpRequest.Method.GET, GlobalProperties.LIVE_UP_VIDEO_LIST, request,mLifecycle, callback)
    }

    /**
     * 获取聊天的历史记录
     */
    fun getHistoryChat(roomId: Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("room_id",roomId)
        request.put("mobi_app",GlobalProperties.MOBI_APP)
        request.put("ts",GlobalProperties.getSystemTime())
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                var modle = Gson().fromJson(element, LiveChatData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.LIVE_UP_CHAT_HISTORY + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"获取直播up聊天的历史记录的url---->$url")
        }
        getRequest().requestFullPath(HttpRequest.Method.GET, GlobalProperties.LIVE_UP_CHAT_HISTORY, request,mLifecycle, callback)
    }
}