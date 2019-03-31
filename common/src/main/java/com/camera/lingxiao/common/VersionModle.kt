package com.camera.lingxiao.common

/**
 * Created by lingxiao on 2017/9/7.
 */

class VersionModle {
    /**
     * versionCode : 3
     * downloadUrl :
     * versionDes : 3.0重大更新，贴心的小bug修复，优化你的体验！
     * versionName : 3.0
     */

    var versionCode: Int = 0
    var downloadUrl: String? = null
    var versionDes: String? = null
    var versionName: String? = null

    override fun toString(): String {
        return "VersionModle{" +
                "versionCode=" + versionCode +
                ", downloadUrl='" + downloadUrl + '\''.toString() +
                ", versionDes='" + versionDes + '\''.toString() +
                ", versionName='" + versionName + '\''.toString() +
                '}'.toString()
    }
}

