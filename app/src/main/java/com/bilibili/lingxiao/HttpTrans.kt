package com.bilibili.lingxiao

import android.util.Log
import com.bilibili.lingxiao.home.find.model.HotWordsData
import com.bilibili.lingxiao.home.find.model.RankListData
import com.bilibili.lingxiao.home.find.model.SearchResultData
import com.bilibili.lingxiao.home.find.model.TopicCardData
import com.bilibili.lingxiao.home.live.model.*
import com.bilibili.lingxiao.home.mikan.model.MiKanFallData
import com.bilibili.lingxiao.home.mikan.model.MiKanRecommendData
import com.bilibili.lingxiao.home.region.model.*
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
    fun getDetailInfo(page:Int,id:String,callback : HttpRxCallback<Any>) {
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
            Log.d(TAG,"拼接的获取视频详情的url---->$url")
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
                val modle = Gson().fromJson(element, VideoRecoData::class.java)
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
     * @param ps 每次请求获取多少个
     * @param next 获取对应的评论楼层  根据all_count获取，如果为0就是带热评的
     * 比如all_count为80 ps为20， 那么next为0 则获取到 60-80之间的楼层 next为80 一样是60-80之间的楼层，但是没有热评
     */
    fun getComment(oid:String,next:Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("appkey",GlobalProperties.APP_KEY)
        request.put("build",GlobalProperties.BUILD)
        request.put("device",GlobalProperties.DEVICE)
        request.put("mobi_app",GlobalProperties.MOBI_APP)
        request.put("platform",GlobalProperties.PLATFORM)
        //request.put("pn",page)
        request.put("next",next)
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
     * 楼中楼评论
     * http://api.bilibili.com/x/v2/reply/reply/cursor?oid=46996647&plat=2&root=1473740845&size=20&sort=0&type=1
     */
    fun getDoubleComment(oid:Int,root:Int,size:Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("oid",oid)
        request.put("plat",2)
        request.put("root",root)
        request.put("size",size)
        request.put("sort",0)
        request.put("type",1)
        request.put("ts",GlobalProperties.getSystemTime())

        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                var jsonData = element.asJsonObject
                var jsonRoot = jsonData.getAsJsonObject("root")
                var modle = Gson().fromJson(jsonRoot, CommentData.Reply::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.COMMENT_DOUBLE_HOST + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"拼接的楼中楼评论url---->$url")
        }
        getRequest().requestFullPath(HttpRequest.Method.GET,GlobalProperties.COMMENT_DOUBLE_HOST,request, mLifecycle,callback)
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
     * 这个接口必须要使用sign签名
     */
    fun getLiveUpInfo(roomId:Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("actionKey","appkey")
        request.put("appkey",GlobalProperties.APP_KEY)
        request.put("build",GlobalProperties.BUILD)
        request.put("channel","bilibiil140")
        request.put("device",GlobalProperties.DEVICE)
        request.put("mobi_app",GlobalProperties.MOBI_APP)
        request.put("platform",GlobalProperties.PLATFORM)
        request.put("room_id",roomId)
        request.put("ts",GlobalProperties.getSystemTime())
        request.put("sign",GlobalProperties.getSign(request))  //计算签名，然后作为参数  这里其实可以写一个拦截器，对所有请求进行签名
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                var modle = Gson().fromJson(element,LiveUpData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            Log.d(TAG,"获取直播up信息的url---->"+
                    GlobalProperties.LIVE_UP_INFO + GlobalProperties.getUrlParamsByMap(request))
        }
        getRequest().requestFullPath(HttpRequest.Method.GET, GlobalProperties.LIVE_UP_INFO, request,mLifecycle, callback)
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
                var modle = Gson().fromJson(element,LiveUserData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.LIVE_USER_INFO + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"获取直播用户信息的url---->$url")
        }
        getRequest().requestFullPath(HttpRequest.Method.GET, GlobalProperties.LIVE_USER_INFO, request,mLifecycle, callback)
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

    /**
     * 获取所有的直播列表
     * @param page 从1开始
     * @param pageSize 30
     * @param areaId  分区的id
     * @param parentAreaId 大分区的id   这两个为0 就是获取所有的tab
     * @param type 类型  live_time 最新   online 热门 还有其他类型
     */
    fun getLiveAllList(page:Int,pageSize:Int,type:String,areaId:Int,parentAreaId:Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("page",page)
        request.put("page_size",pageSize)
        request.put("sort_type",type)
        request.put("area_id",areaId)
        request.put("parent_area_id",parentAreaId)
        request.put("platform",GlobalProperties.PLATFORM)
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                var modle = Gson().fromJson(element, LiveAllData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        getRequest().requestFullPath(HttpRequest.Method.GET, GlobalProperties.LIVE_ALL_URL, request,mLifecycle, callback)
    }

    /**
     * 获取所有的直播分类tab
     * @param parent_id  最外层的分类
     */
    fun getLiveTabList(parent_id:Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("parent_id",parent_id)
        request.put("platform",GlobalProperties.PLATFORM)
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                val type = object : TypeToken<List<LiveTabData.Tab>>() {}.getType()
                var modle:List<LiveTabData.Tab> = Gson().fromJson<List<LiveTabData.Tab>>(element, type)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        getRequest().requestFullPath(HttpRequest.Method.GET, GlobalProperties.LIVE_TAB_LIST_URL, request,mLifecycle, callback)
    }

    /**
     * 获取分区详情
     * https://app.bilibili.com/x/v2/region/dynamic?build=5410000&mobi_app=android&platform=android&rid=167
     */
    fun getRegionList(tid:Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("build",GlobalProperties.BUILD)
        request.put("mobi_app",GlobalProperties.MOBI_APP)
        request.put("platform",GlobalProperties.PLATFORM)
        request.put("rid",tid)
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                var modle = Gson().fromJson(element, RegionDetailData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.REGION_DETAIL_URL + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"获取分区详情的url---->$url")
        }
        getRequest().requestFullPath(HttpRequest.Method.GET, GlobalProperties.REGION_DETAIL_URL, request,mLifecycle, callback)
    }

    /**
     * 分区加载更多
     */
    fun getRegionMoreList(rid:Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("build",GlobalProperties.BUILD)
        request.put("pull",false)
        request.put("platform",GlobalProperties.PLATFORM)
        request.put("rid",rid)
        request.put("ctime",GlobalProperties.getSystemTime())
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                var modle = Gson().fromJson(element, RegionDetailData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        getRequest().requestFullPath(HttpRequest.Method.GET, GlobalProperties.REGION_DETAIL_LOADMORE_URL, request,mLifecycle, callback)
    }

    /**
     * 分区局部刷新
     */
    fun refreshRegionLocality(type:String,rand:Int,rid:Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("rand",rand)
        request.put("rid",rid)
        request.put("platform",GlobalProperties.PLATFORM)
        var url = "${GlobalProperties.REGION_LOCALITY_URL}$type?"
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                val type = object : TypeToken<List<RegionRecommendData.Data.Body>>() {}.getType()
                var modle:List<RegionRecommendData.Data.Body> = Gson().fromJson<List<RegionRecommendData.Data.Body>>(element, type)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        getRequest().requestFullPath(HttpRequest.Method.GET, url, request,mLifecycle, callback)
    }

    /**
     * 番剧详情
     */
    fun getBangumiDetail(season_id:String,type:String,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("appkey",GlobalProperties.APP_KEY)
        request.put("build",GlobalProperties.BUILD)
        request.put("mobi_app",GlobalProperties.MOBI_APP)
        request.put("platform",GlobalProperties.PLATFORM)
        request.put("season_id",season_id)
        request.put("ts",GlobalProperties.getSystemTime())
        request.put("type",type)
        request.put("sign",GlobalProperties.getSign(request))  //计算签名，然后作为参数  这里其实可以写一个拦截器，对所有请求进行签名
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                var modle = Gson().fromJson(element, BangumiDetailData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.BANGUMI_DETAIL + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"获取番剧详情的url---->$url")
        }
        getRequest().requestFullPathWithoutCheck(HttpRequest.Method.GET, GlobalProperties.BANGUMI_DETAIL, request,mLifecycle, callback)
    }


    /**
     * 番剧详情下面的推荐
     * https://bangumi.bilibili.com/api/season/recommend/rnd/24618.json
     */
    fun getBangumiDetailRecommend(season_id:String,callback: HttpRxCallback<Any>){
        request.clear()
        var url = GlobalProperties.BANGUMI_RECOMMEND + "$season_id.json"
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                var modle = Gson().fromJson(element, BangumiRecommendData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            Log.d(TAG,"获取番剧详情下面的推荐的url---->$url")
        }
        getRequest().requestFullPathWithoutCheck(HttpRequest.Method.GET, url, request,mLifecycle, callback)
    }

    fun getHotSearchWords(limit :Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("build",GlobalProperties.BUILD)
        request.put("limit",limit)
        request.put("platform",GlobalProperties.PLATFORM)
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                var modle = Gson().fromJson(element, HotWordsData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.SEARCH_HOT + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"获取大家都在搜的url---->$url")
        }
        getRequest().requestFullPath(HttpRequest.Method.GET, GlobalProperties.SEARCH_HOT, request,mLifecycle, callback)
    }

    /**
     * 根据关键字获取搜索结果
     * @param word 关键字
     * @param page 从1开始
     * @param pageSize 20
     */
    fun getSearchResult(word :String, page:Int,pageSize:Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("build",GlobalProperties.BUILD)
        request.put("duration",0)
        request.put("keyword",word)
        request.put("platform",GlobalProperties.PLATFORM)
        request.put("pn",page)
        request.put("ps",pageSize)
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                var modle = Gson().fromJson(element, SearchResultData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.SEARCH_KEYWORD + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"获取搜索结果url---->$url")
        }
        getRequest().requestFullPath(HttpRequest.Method.GET, GlobalProperties.SEARCH_KEYWORD, request,mLifecycle, callback)
    }

    /**
     * 话题中心
     */
    fun getTopicCenter(page:Int,pageSize:Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("page",page)
        request.put("pageSize",pageSize)
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                var modle = Gson().fromJson(element, TopicCardData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.TOPIC_CENTER + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"获取话题中心url---->$url")
        }
        getRequest().requestFullPathWithoutCheck(HttpRequest.Method.GET, GlobalProperties.TOPIC_CENTER, request,mLifecycle, callback)
    }

    /**
     * 活动中心
     */
    fun getActivityCenter(page:Int,pageSize:Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("page",page)
        request.put("pageSize",pageSize)
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                var modle = Gson().fromJson(element, TopicCardData::class.java)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.ACTIVITY_CENTER + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"获取活动中心url---->$url")
        }
        getRequest().requestFullPathWithoutCheck(HttpRequest.Method.GET, GlobalProperties.ACTIVITY_CENTER, request,mLifecycle, callback)
    }


    /**
     * 原创排行榜
     * //原创
     * order = bangumi 番剧
     * order = all 全站
     * order = origin 原创
     */
    fun getOriginRankingList(type :String, page:Int,pageSize:Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("order",type)
        request.put("page",page)
        request.put("pageSize",pageSize)
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                val type = object : TypeToken<List<RankListData.Item>>() {}.getType()
                var modle :List<RankListData.Item> = Gson().fromJson(element, type)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.ORIGIN_RANKING_LIST + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"获取原创排行榜url---->$url")
        }
        getRequest().requestFullPath(HttpRequest.Method.GET, GlobalProperties.ORIGIN_RANKING_LIST, request,mLifecycle, callback)
    }

    /**
     * 全区排行榜
     * @param rid  分区获取到的rid
     */
    fun getAllRegionRankingList(rid :Int, page:Int,pageSize:Int,callback: HttpRxCallback<Any>){
        request.clear()
        request.put("rid",rid)
        request.put("page",page)
        request.put("pageSize",pageSize)
        callback.setParseHelper(object : ParseHelper {
            override fun parse(element: JsonElement): Any? {
                val type = object : TypeToken<List<RankListData.Item>>() {}.getType()
                var modle :List<RankListData.Item> = Gson().fromJson(element, type)
                val obj = arrayOfNulls<Any>(1)
                obj[0] = modle
                return obj
            }
        })
        if (debug){
            var url = GlobalProperties.ALL_REGION_RANKING_LIST + GlobalProperties.getUrlParamsByMap(request)
            Log.d(TAG,"获取全区排行榜url---->$url")
        }
        getRequest().requestFullPath(HttpRequest.Method.GET, GlobalProperties.ALL_REGION_RANKING_LIST, request,mLifecycle, callback)
    }
}