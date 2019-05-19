package com.bilibili.lingxiao.play.model

import com.google.gson.annotations.SerializedName

data class VideoData(
    val cid: Int,
    val expire_time: Int,
    val file_info: FileInfo,
    val fnval: Int,
    val fnver: Int,
    val quality: Int,
    val support_description: List<String>,
    val support_formats: List<String>,
    val support_quality: List<Int>,
    val url: String,
    val video_codecid: Int,
    val video_project: Boolean
) {
    data class FileInfo(
        @SerializedName("112")
        val x_112: List<X112>,
        @SerializedName("16")
        val x_16: List<X16>,
        @SerializedName("32")
        val x_32: List<X32>,
        @SerializedName("64")
        val x_64: List<X64>,
        @SerializedName("80")
        val x_80: List<X80>
    ) {
        data class X32(
            val filesize: Long,
            val timelength: Int
        )

        data class X16(
            val filesize: Long,
            val timelength: Int
        )

        data class X112(
            val filesize: Int,
            val timelength: Int
        )

        data class X64(
            val filesize: Long,
            val timelength: Int
        )

        data class X80(
            val filesize: Long,
            val timelength: Int
        )
    }
}