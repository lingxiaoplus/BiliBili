package com.bilibili.lingxiao.ijkplayer

import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import com.bilibili.lingxiao.ijkplayer.NetworkUtil.NetworkType
import android.support.v4.content.ContextCompat.getSystemService





object  NetworkUtil{

    enum class NetworkType {
        // wifi
        NETWORK_WIFI,
        // 4G 网
        NETWORK_4G,
        // 3G 网
        NETWORK_3G,
        // 2G 网
        NETWORK_2G,
        // 未知网络
        NETWORK_UNKNOWN,
        // 没有网络
        NETWORK_NO,
        //网络断开或关闭
        NETWORK_OFFLINE,
        //以太网网络
        NETWORK_ETHERNET
    }
    /**
     * 获取当前网络类型
     */
    fun getNetworkType(context: Context):NetworkType {
        val manager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        var netType = NetworkType.NETWORK_NO
        if (networkInfo == null){
            /** 没有任何网络  */
            netType = NetworkType.NETWORK_NO
        }
        if (!networkInfo.isConnected) {
            /** 网络断开或关闭  */
            netType = NetworkType.NETWORK_OFFLINE
        }
        if (networkInfo.type == ConnectivityManager.TYPE_ETHERNET) {
            /** 以太网网络  */
            netType = NetworkType.NETWORK_ETHERNET
        } else if (networkInfo.type == ConnectivityManager.TYPE_WIFI) {
            /** wifi网络，当激活时，默认情况下，所有的数据流量将使用此连接  */
            netType = NetworkType.NETWORK_WIFI
        } else if (networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
            /** 移动数据连接,不能与连接共存,如果wifi打开，则自动关闭  */
            when (networkInfo.subtype) {
                TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN ->
                    /** 2G网络  */
                    netType = NetworkType.NETWORK_2G
                TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP ->
                    /** 3G网络  */
                    netType = NetworkType.NETWORK_3G
                TelephonyManager.NETWORK_TYPE_LTE ->
                    /** 4G网络  */
                    netType = NetworkType.NETWORK_4G
            }
        }
        /** 未知网络 */
        return netType
    }


    /**
     * 判断移动数据是否打开
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    fun isWifiState(context: Context): Boolean {
        try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val getMobileDataEnabledMethod = tm.javaClass.getDeclaredMethod("getDataEnabled")
            return getMobileDataEnabledMethod.invoke(tm) as Boolean
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 判断网络是否可用
     *
     * 需要异步 ping，如果 ping 不通就说明网络不可用
     *
     * @param ip ip 地址（自己服务器 ip），如果为空，ip 为阿里巴巴公共 ip
     * @return `true`: 可用<br></br>`false`: 不可用
     */
    /*fun isAvailableByPing(ip: String?): Boolean {
        var ip = ip
        if (ip == null || ip.length <= 0) {
            // 阿里巴巴公共 ip
            ip = "223.5.5.5"
        }
        val result = Runtime.getRuntime().exec(String.format("ping -c 1 %s", ip))

        //val result = ShellUtil.execCmd(String.format("ping -c 1 %s", ip), false)
        val ret = result.result === 0
        if (result.successMsg != null) {
            Log.d("NetUtil", "isAvailableByPing() called" + result.successMsg)
        }
        if (result.errorMsg != null) {
            Log.d("NetUtil", "isAvailableByPing() called" + result.errorMsg)
        }
        return ret
    }*/
}