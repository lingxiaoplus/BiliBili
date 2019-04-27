package com.bilibili.lingxiao.home.live.model

import com.google.gson.annotations.SerializedName

data class LiveChatData(
    @SerializedName("admin")
    val admin: List<Any>,
    @SerializedName("room")
    val room: List<Room>
) {
    data class Room(
        @SerializedName("bubble")
        val bubble: Int,
        @SerializedName("check_info")
        val checkInfo: CheckInfo,
        @SerializedName("guard_level")
        val guardLevel: Int,
        @SerializedName("isadmin")
        val isadmin: Int,
        @SerializedName("medal")
        val medal: List<Any>,
        @SerializedName("nickname")
        val nickname: String,
        @SerializedName("rank")
        val rank: Int,
        @SerializedName("rnd")
        val rnd: String,
        @SerializedName("svip")
        val svip: Int,
        @SerializedName("teamid")
        val teamid: Int,
        @SerializedName("text")
        val text: String,
        @SerializedName("timeline")
        val timeline: String,
        @SerializedName("title")
        val title: List<String>,
        @SerializedName("uid")
        val uid: Int,
        @SerializedName("uname_color")
        val unameColor: String,
        @SerializedName("user_level")
        val userLevel: List<Any>,
        @SerializedName("user_title")
        val userTitle: String,
        @SerializedName("vip")
        val vip: Int
    ) {
        data class CheckInfo(
            @SerializedName("ct")
            val ct: String,
            @SerializedName("ts")
            val ts: Int
        )
    }
}