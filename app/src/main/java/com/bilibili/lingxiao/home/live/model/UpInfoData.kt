package com.bilibili.lingxiao.home.live.model

import com.google.gson.annotations.SerializedName

data class UpInfoData(
    @SerializedName("aid")
    val aid: Int,
    @SerializedName("attribute")
    val attribute: Int,
    @SerializedName("cid")
    val cid: Int,
    @SerializedName("copyright")
    val copyright: Int,
    @SerializedName("ctime")
    val ctime: Int,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("dimension")
    val dimension: Dimension,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("dynamic")
    val `dynamic`: String,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("pic")
    val pic: String,
    @SerializedName("pubdate")
    val pubdate: Int,
    @SerializedName("rights")
    val rights: Rights,
    @SerializedName("stat")
    val stat: Stat,
    @SerializedName("state")
    val state: Int,
    @SerializedName("tid")
    val tid: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("tname")
    val tname: String,
    @SerializedName("videos")
    val videos: Int
) {
    data class Stat(
        @SerializedName("aid")
        val aid: Int,
        @SerializedName("coin")
        val coin: Int,
        @SerializedName("danmaku")
        val danmaku: Int,
        @SerializedName("dislike")
        val dislike: Int,
        @SerializedName("favorite")
        val favorite: Int,
        @SerializedName("his_rank")
        val hisRank: Int,
        @SerializedName("like")
        val like: Int,
        @SerializedName("now_rank")
        val nowRank: Int,
        @SerializedName("reply")
        val reply: Int,
        @SerializedName("share")
        val share: Int,
        @SerializedName("view")
        val view: Int
    )

    data class Owner(
        @SerializedName("face")
        val face: String,
        @SerializedName("mid")
        val mid: Int,
        @SerializedName("name")
        val name: String
    )

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

    data class Dimension(
        @SerializedName("height")
        val height: Int,
        @SerializedName("rotate")
        val rotate: Int,
        @SerializedName("width")
        val width: Int
    )
}