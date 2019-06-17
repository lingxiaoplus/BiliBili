package com.bilibili.lingxiao.home.mikan.model

import com.google.gson.annotations.SerializedName

data class MiKanFallData(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: List<Result>
) {
    data class Result(
        @SerializedName("cover")
        val cover: String,
        @SerializedName("cursor")
        val cursor: Long?,
        @SerializedName("desc")
        val desc: String?,
        @SerializedName("id")
        val id: Int,
        @SerializedName("is_new")
        val isNew: Int,
        @SerializedName("link")
        val link: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("type")
        val type: Int,
        @SerializedName("wid")
        val wid: Int
    )
}