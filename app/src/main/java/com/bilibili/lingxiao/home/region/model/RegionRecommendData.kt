package com.bilibili.lingxiao.home.region.model

import com.google.gson.annotations.SerializedName

data class RegionRecommendData(
    @SerializedName("data")
    val `data`: List<Data>
) {
    data class Data(
        @SerializedName("banner")
        val banner: Banner,
        @SerializedName("body")
        val body: List<Body>,
        @SerializedName("param")
        val `param`: String,
        @SerializedName("style")
        val style: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("type")
        val type: String
    ) {
        data class Banner(
            @SerializedName("top")
            val top: List<Top>
        ) {
            data class Top(
                @SerializedName("client_ip")
                val clientIp: String,
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
                @SerializedName("is_ad_loc")
                val isAdLoc: Boolean,
                @SerializedName("request_id")
                val requestId: String,
                @SerializedName("resource_id")
                val resourceId: Int,
                @SerializedName("server_type")
                val serverType: Int,
                @SerializedName("src_id")
                val srcId: Int,
                @SerializedName("title")
                val title: String,
                @SerializedName("uri")
                val uri: String
            )
        }

        data class Body(
            @SerializedName("cm_mark")
            val cmMark: Int,
            @SerializedName("cover")
            val cover: String,
            @SerializedName("danmaku")
            val danmaku: Int,
            @SerializedName("goto")
            val goto: String,
            @SerializedName("is_ad")
            val isAd: Boolean,
            @SerializedName("like")
            val like: Int,
            @SerializedName("param")
            val `param`: String,
            @SerializedName("play")
            val play: Int,
            @SerializedName("title")
            val title: String,
            @SerializedName("uri")
            val uri: String
        )
    }
}