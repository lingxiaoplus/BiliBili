package com.bilibili.lingxiao.home.find.model


import com.google.gson.annotations.SerializedName

data class TopicCardData(
    @SerializedName("code")
    val code: Int,
    @SerializedName("list")
    val list: List<Item>,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("total")
    val total: Int
) {
    data class Item(
        @SerializedName("cover")
        val cover: String,
        @SerializedName("link")
        val link: String,
        @SerializedName("state")
        val state: Int?,
        @SerializedName("title")
        val title: String
    )
}