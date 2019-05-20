package com.bilibili.lingxiao

import android.util.Log
import com.bilibili.lingxiao.utils.MD5Util
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.oss.StringUtils
import okhttp3.HttpUrl
import java.net.URLEncoder
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*

object GlobalProperties {
    val LIVE_HOST = "http://live.bilibili.com/AppIndex/home/?"   //直播api
    val COMMEND_HOST = "http://app.bilibili.com/x/feed/index?"   //推荐api
    val DETAIL_HOST = "http://api.bilibili.cn/view?"  //视频详情
    val COMMEND_VIDEO_HOST = "http://api.bilibili.cn/recommend?"  //视频详情下面的推荐
    val COMMENT_HOST = "http://api.bilibili.com/x/v2/reply/main?"  //评论
    val COMMENT_DOUBLE_HOST = "http://api.bilibili.com/x/v2/reply/reply/cursor?" //楼中楼评论
    val BANGUMI_CN_AND_JP_HOST = "http://bangumi.bilibili.com/appindex/follow_index_page?" //国内外推荐番剧
    val BANGUMI_FALL_HOST = "http://bangumi.bilibili.com/appindex/follow_index_fall?" //编辑推荐番剧
    val CATEGORY_HOST = "http://app.bilibili.com/x/v2/region?" //分区
    val CATEGORY_RECOMMEND_HOST = "http://app.bilibili.com/x/v2/show/index?" //分区推荐
    var USER_AGENT = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Mobile Safari/537.36"

    //var LIVE_UP_INFO = "https://api.live.bilibili.com/room/v1/Room/get_info" //获取up主的信息
    var LIVE_UP_INFO = "http://api.live.bilibili.com/xlive/app-room/v1/index/getInfoByRoom?"
    var LIVE_USER_INFO = "http://api.live.bilibili.com/live_user/v1/card/card_user?" //获取直播间用户的信息
    var LIVE_DANMAKU_URL = "wss://broadcastlv.chat.bilibili.com:2245/sub"  //直播弹幕 websocket
    var LIVE_UP_GOLD_LIST = "http://api.live.bilibili.com/rankdb/v1/RoomRank/tabRanks?"  //金瓜子榜  礼物榜
    var LIVE_FANS_LIST = "http://api.live.bilibili.com/rankdb/v2/RoomRank/mobileMedalRank?"  //粉丝榜
    var LIVE_FLEET_LIST = "http://api.live.bilibili.com/live_user/v1/Guard/topList?"  //大航海
    var LIVE_UP_VIDEO_LIST = "http://api.live.bilibili.com/bili-api/x/internal/v2/archive/up/passed?" //直播up主的视频投稿
    var LIVE_UP_CHAT_HISTORY = "http://api.live.bilibili.com/xlive/app-room/v1/dM/gethistory?" //直播评论
    var LIVE_ALL_URL = "http://api.live.bilibili.com/room/v3/Area/getRoomList"  //获取全部直播列表
    var LIVE_TAB_LIST_URL = "http://api.live.bilibili.com/room/v1/Area/getList"  //直播分类所有tab

    var REGION_DETAIL_URL = "https://app.bilibili.com/x/v2/region/dynamic?"  //分区详情
    var REGION_DETAIL_LOADMORE_URL = "https://app.bilibili.com/x/v2/region/dynamic/list?"  //分区加载更多

    val IMAGE_RULE_480_300 = "@480w_300h_1e_1c.webp"  //图片尺寸
    val IMAGE_RULE_240_150 = "@240w_150h_1e_1c.webp"
    val IMAGE_RULE_160_100 = "@160w_100h_1e_1c.webp"
    val IMAGE_RULE_90_90 = "@90w_90h_1e_1c.webp"
    var IMAGE_RULE_60_60 = "@60w_60h_1e_1c.webp"
    private val SECRET_KEY = "ea85624dfcf12d7cc7b2b3a94fac1f2c"
    val PARAM_SIGN = "sign"
    val APP_KEY = "c1b107428d337928"
    val BUILD = "5400000"
    val MOBI_APP = "android"
    val PLATFORM = "android"
    val DEVICE = "android"
    val NETWORK_WIFI = "wifi"
    val SCALE = UIUtil.getDensityString()
    val SRC = "bili"
    val VERSION = "5.19.0.519000"
    var TAG = GlobalProperties::class.java.simpleName

    /**
     * 将所有参数（包括变量名和值及=&符号）排序后加上appsecret（只有值）之后做md5，md5 按照32位小写加密
     * 得到返回结果即为所求sign值
     */
    fun getSign(map: Map<String, Any>): String {
        //拼接参数(按顺序) + SecretKey
        val orignSign = getUrlParamsByMap(map) + SECRET_KEY
        //进行MD5加密
        var sign = ""
        try {
            sign = MD5Util.getMD5(orignSign).trim()
            Log.i(TAG, "加密后的sign: $sign")
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "sign encryption failed: ${e.printStackTrace()}")
        }
        return sign
    }

    fun getTraceId(): String {
        val df = SimpleDateFormat("yyyyMMddHHmm", Locale.getDefault())
        val df2 = SimpleDateFormat("s", Locale.getDefault())
        val s = StringBuilder()
        s.append(df.format(Date()))
        s.append("000")
        s.append(df2.format(Date()))
        return s.toString()
    }

    /**
     * 获取当前Unix时间戳
     * @return
     */
    fun getSystemTime(): String {
        val ts = System.currentTimeMillis() / 1000
        return ts.toString()
    }

    /**
     * 将map转换成url参数
     * @param map
     * @return
     */
    fun getUrlParamsByMap(map: Map<String, Any>): String {
        var params =  StringBuffer()
        val it = map.iterator()
        while (it.hasNext()) {
            val str = it.next()
            params.append(str.key)
            params.append("=")
            params.append(str.value)
            if (it.hasNext()) {
                params.append("&")
            }
        }
        return params.toString()
    }

}
