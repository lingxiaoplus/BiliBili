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

    private val SECRET_KEY = "ea85624dfcf12d7cc7b2b3a94fac1f2c"
    val PARAM_SIGN = "sign"
    val APP_KEY = "c1b107428d337928"
    val BUILD = "51900"
    val MOBI_APP = "android"
    val PLATFORM = "android"
    val DEVICE = "android"
    val NETWORK_WIFI = "wifi"
    val SCALE = UIUtil.getDensityString()
    val SRC = "bili"
    val VERSION = "5.19.0.519000"
    var TAG = GlobalProperties::class.java.simpleName

    /**
     * 将所有参数（包括变量名和值及=&符号）排序后加上appsecret（只有值）之后做md5，
     * 得到返回结果即为所求sign值
     */
    fun getSign(url: HttpUrl): String {
        //拼接参数(按顺序)+SecretKey
        val set = url.queryParameterNames()
        val queryParams = StringBuilder()
        val it = set.iterator()
        while (it.hasNext()) {
            val str = it.next()
            queryParams.append(str)
            queryParams.append("=")
            queryParams.append(url.queryParameter(str))
            if (it.hasNext()) {
                queryParams.append("&")
            }
        }
        queryParams.append(SECRET_KEY)
        val orignSign = queryParams.toString()
        //进行MD5加密
        var sign = ""
        try {
            sign = MD5Util.getMD5(orignSign).trim()
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "sign encryption failed : " + e.message)
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
     * 将map转换成url
     *
     * @param map
     * @return
     */
    fun getUrlParamsByMap(map: Map<String, Any>): String {
        var sb =  StringBuffer()
        for ((key,value)in map){
            sb.append(key + "=")
            if (StringUtils.isEmpty(value.toString())) {
                sb.append("&")
            } else {
                var va = URLEncoder.encode(value.toString(), "UTF-8");
                sb.append(va + "&");
            }
        }
        return sb.toString()
    }

}
