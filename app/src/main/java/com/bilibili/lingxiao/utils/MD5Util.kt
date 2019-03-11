package com.bilibili.lingxiao.utils

import java.security.MessageDigest

object MD5Util{
    fun getMD5(str :String): String{
        val md5 = MessageDigest.getInstance("MD5")
        md5.update(str.toByteArray())
        val m = md5.digest()//加密
        return getString(m)
    }

    private fun getString(b: ByteArray): String {
        val buf = StringBuffer()
        for (i in b.indices) {
            var a = b[i].toInt()
            if (a < 0)
                a += 256
            if (a < 16)
                buf.append("0")
            buf.append(Integer.toHexString(a))

        }
        return buf.toString()  //32位
    }
}