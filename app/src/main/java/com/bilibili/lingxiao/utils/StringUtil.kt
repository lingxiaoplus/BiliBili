package com.bilibili.lingxiao.utils

import java.math.BigDecimal

object StringUtil {

    /**
     * 将数字转换为带万的
     */
    fun getBigDecimalNumber(num :Int?): String{
        if (num == null) return "-"
        if (num > 1000 && num < 1000000){
            var float = num / 1000.0
            val b = BigDecimal(float)
            val f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).toFloat()  //表明四舍五入，保留两位小数
            return f1.toString() + "万"
        }else if (num > 1000000){
            var float = num / 1000000.0
            val b = BigDecimal(float)
            val f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).toFloat()
            return f1.toString() + "百万"
        }
        return num.toString()
    }
}