package com.bilibili.lingxiao.play.model

import com.google.gson.annotations.SerializedName

data class VideoRecoData(
    @SerializedName("code")
    val code: Int,
    @SerializedName("list")
    val list: List<VideoInfo>,
    @SerializedName("num")
    val num: Int,
    @SerializedName("pages")
    val pages: Int
) {
    data class VideoInfo(
        @SerializedName("aid")
        val aid: Int,
        @SerializedName("author")
        val author: String,
        @SerializedName("coins")
        val coins: Int,
        @SerializedName("create")
        val create: String,
        @SerializedName("credit")
        val credit: Int,
        @SerializedName("description")
        val description: String,
        @SerializedName("duration")
        val duration: String,
        @SerializedName("favorites")
        val favorites: Int,
        @SerializedName("last_recommend")
        val lastRecommend: List<LastRecommend>,
        @SerializedName("mid")
        val mid: Int,
        @SerializedName("pic")
        val pic: String,
        @SerializedName("play")
        val play: Int,
        @SerializedName("review")
        val review: Int,
        @SerializedName("rights")
        val rights: Rights,
        @SerializedName("subtitle")
        val subtitle: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("typeid")
        val typeid: Int,
        @SerializedName("typename")
        val typename: String,
        @SerializedName("video_review")
        val videoReview: Int
    ) {
        data class Rights(
            @SerializedName("autoplay")
            val autoplay: Int,
            @SerializedName("bp")
            val bp: Int,
            @SerializedName("download")
            val download: Int,
            @SerializedName("elec")
            val elec: Int,
            @SerializedName("hd5")
            val hd5: Int,
            @SerializedName("is_cooperation")
            val isCooperation: Int,
            @SerializedName("movie")
            val movie: Int,
            @SerializedName("no_reprint")
            val noReprint: Int,
            @SerializedName("pay")
            val pay: Int,
            @SerializedName("ugc_pay")
            val ugcPay: Int,
            @SerializedName("ugc_pay_preview")
            val ugcPayPreview: Int
        )

        data class LastRecommend(
            @SerializedName("face")
            val face: String,
            @SerializedName("mid")
            val mid: Int,
            @SerializedName("msg")
            val msg: String,
            @SerializedName("time")
            val time: Int,
            @SerializedName("uname")
            val uname: String
        )
    }
}