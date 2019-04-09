package com.bilibili.lingxiao.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    /**
     * 返回unix时间戳 (1970年至今的秒数)
     * @return
     */
    fun getUnixStamp(): Long {
        return System.currentTimeMillis() / 1000
    }

    /**
     * 得到昨天的日期
     * @return
     */
    fun getYestoryDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -1)
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(calendar.getTime())
    }

    /**
     * 得到今天的日期
     * @return
     */
    fun getTodayDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(Date())
    }

    /**
     * 时间戳转化为时间格式
     * @param timeStamp
     * @return
     */
    fun timeStampToStr(timeStamp: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(timeStamp * 1000)
    }

    /**
     * 得到日期   yyyy-MM-dd
     * @param timeStamp  时间戳
     * @return
     */
    fun formatDate(timeStamp: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(timeStamp * 1000)
    }

    /**
     * 得到时间  HH:mm:ss
     * @param timeStamp   时间戳
     * @return
     */
    fun getTime(timeStamp: Long): String? {
        var time: String? = null
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = sdf.format(timeStamp * 1000)
        val split = date.split("\\s".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        if (split.size > 1) {
            time = split[1]
        }
        return time
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp
     * @return
     */
    fun convertTimeToFormat(timeStamp: Long): String {
        val curTime = System.currentTimeMillis() / 1000.toLong()
        val time = curTime - timeStamp

        return if (time < 60 && time >= 0) {
            "刚刚"
        } else if (time >= 60 && time < 3600) {
            (time / 60).toString() + "分钟前"
        } else if (time >= 3600 && time < 3600 * 24) {
            (time / 3600).toString() + "小时前"
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            (time / 3600 / 24).toString() + "天前"
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            (time / 3600 / 24 / 30).toString() + "个月前"
        } else if (time >= 3600 * 24 * 30 * 12) {
            (time / 3600 / 24 / 30 / 12).toString() + "年前"
        } else {
            "刚刚"
        }
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，(多少分钟)
     *
     * @param timeStamp
     * @return
     */
    fun timeStampToFormat(timeStamp: Long): String {
        val curTime = System.currentTimeMillis() / 1000.toLong()
        val time = curTime - timeStamp
        return (time / 60).toString() + ""
    }
}