package com.bilibili.lingxiao.play.model

import com.google.gson.annotations.SerializedName

data class VideoDetailData(
    val allow_bp: Int,
    val allow_download: Int,
    val allow_feed: Int,
    val arctype: String,
    val author: String,
    val cid: Int,
    val coins: Int,
    val created: Int,
    val created_at: String?,
    val credit: String,
    val description: String?,
    val face: String,
    val favorites: Int,
    val from: String,
    val instant_server: String,
    val list: List,
    val mid: Int,
    val offsite: String,
    val pages: Int,
    val part: String,
    val partname: String,
    val pic: String,
    val play: Int,
    val review: Int,
    val spid: Any,
    val src: String,
    val tag: Any,
    val tid: Int,
    val title: String,
    val type: String,
    val typename: String,
    val vid: String,
    val video_review: Int
) {
    data class List(
        @SerializedName("0")
        val x: X0
    ) {
        data class X0(
            val cid: Int,
            val has_alias: Boolean,
            val page: Int,
            val part: String,
            val type: String,
            val vid: String,
            val weblink: String
        )
    }
}