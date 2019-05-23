package com.bilibili.lingxiao.home.region.model

import com.google.gson.annotations.SerializedName

data class RegionDetailData(
    @SerializedName("banner")
    val banner: Banner?,
    @SerializedName("cbottom")
    val cbottom: Int,
    @SerializedName("ctop")
    val ctop: Int,
    @SerializedName("new")
    val new: List<Info>?,
    @SerializedName("recommend")
    val recommend: List<Info>?
) {
    data class Info(
        @SerializedName("cover")
        val cover: String,
        @SerializedName("danmaku")
        val danmaku: Int,
        @SerializedName("duration")
        val duration: Int,
        @SerializedName("face")
        val face: String,
        @SerializedName("favourite")
        val favourite: Int,
        @SerializedName("goto")
        val goto: String,
        @SerializedName("like")
        val like: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("param")
        val `param`: String,
        @SerializedName("play")
        val play: Int,
        @SerializedName("pubdate")
        val pubdate: Int,
        @SerializedName("reply")
        val reply: Int,
        @SerializedName("rid")
        val rid: Int,
        @SerializedName("rname")
        val rname: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("uri")
        val uri: String
    )

    data class Banner(
        @SerializedName("top")
        val top: List<Top>?
    ) {
        data class Top(
            @SerializedName("cm_mark")
            val cmMark: Int,
            @SerializedName("hash")
            val hash: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("image")
            val image: String,
            @SerializedName("index")
            val index: Int,
            @SerializedName("is_ad")
            val isAd: Boolean,
            @SerializedName("request_id")
            val requestId: String,
            @SerializedName("resource_id")
            val resourceId: Int,
            @SerializedName("server_type")
            val serverType: Int,
            @SerializedName("title")
            val title: String,
            @SerializedName("uri")
            val uri: String
        )
    }
}