package com.bilibili.lingxiao.utils

import java.math.BigDecimal

object StringUtil {

    /**
     * 将数字转换为带万的
     */
    fun getBigDecimalNumber(num:Int): String{
        if (num < 1000){
            return num.toString()
        }
        var float = num / 1000.0
        val b = BigDecimal(float)
        val f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).toFloat()  //表明四舍五入，保留两位小数
        return f1.toString() + "万"
    }
}