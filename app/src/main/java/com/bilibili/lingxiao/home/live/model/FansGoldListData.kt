package com.bilibili.lingxiao.home.live.model

import com.google.gson.annotations.SerializedName

data class FansGoldListData(
    @SerializedName("list")
    val list: List<FansInfo>,
    @SerializedName("next_offset")
    val nextOffset: Int,
    @SerializedName("own")
    val own: Own
) {
    data class Own(
        @SerializedName("face")
        val face: String,
        @SerializedName("guard_level")
        val guardLevel: Int,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("rank")
        val rank: Int,
        @SerializedName("rank_text")
        val rankText: String,
        @SerializedName("score")
        val score: Int,
        @SerializedName("uid")
        val uid: Int,
        @SerializedName("uname")
        val uname: String
    )

    data class FansInfo(
        @SerializedName("face")
        val face: String?,
        @SerializedName("guard_level")
        val guardLevel: Int?,
        @SerializedName("icon")
        val icon: String?,
        @SerializedName("isSelf")
        val isSelf: Int?,
        @SerializedName("rank")
        val rank: Int,
        @SerializedName("score")
        val score: Int?,
        @SerializedName("uid")
        val uid: Int,
        @SerializedName("uname")
        val uname: String?,
        @SerializedName("medal_name")
        val medal_name: String?,
        @SerializedName("level")
        val level: Int?,
        @SerializedName("color")
        val color: Int?
    )
}