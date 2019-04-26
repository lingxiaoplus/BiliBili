package com.bilibili.lingxiao.home.live.model

import com.google.gson.annotations.SerializedName

data class FleetListData(
    @SerializedName("info")
    val info: Info,
    @SerializedName("list")
    val list: List<UserInfo>,
    @SerializedName("top3")
    val top3: List<Top3>
) {
    data class UserInfo(
        @SerializedName("face")
        val face: String,
        @SerializedName("guard_level")
        val guardLevel: Int,
        @SerializedName("is_alive")
        val isAlive: Int,
        @SerializedName("rank")
        val rank: Int,
        @SerializedName("ruid")
        val ruid: Int,
        @SerializedName("uid")
        val uid: Int,
        @SerializedName("username")
        val username: String
    )

    data class Info(
        @SerializedName("now")
        val now: Int,
        @SerializedName("num")
        val num: Int,
        @SerializedName("page")
        val page: Int
    )

    data class Top3(
        @SerializedName("face")
        val face: String,
        @SerializedName("guard_level")
        val guardLevel: Int,
        @SerializedName("is_alive")
        val isAlive: Int,
        @SerializedName("rank")
        val rank: Int,
        @SerializedName("ruid")
        val ruid: Int,
        @SerializedName("uid")
        val uid: Int,
        @SerializedName("username")
        val username: String
    )
}