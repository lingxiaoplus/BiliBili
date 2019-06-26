package com.bilibili.lingxiao.home.find.model


import com.google.gson.annotations.SerializedName

data class HotWordsData(
    @SerializedName("list")
    val list: List<HotWord>,
    @SerializedName("trackid")
    val trackid: String
) {
    data class HotWord(
        @SerializedName("keyword")
        val keyword: String,
        @SerializedName("name_type")
        val nameType: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("word_type")
        val wordType: Int
    )
}