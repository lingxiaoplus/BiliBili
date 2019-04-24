package com.bilibili.lingxiao.home.live.model

import com.google.gson.annotations.SerializedName

data class LiveUpData(
    @SerializedName("allow_change_area_time")
    val allowChangeAreaTime: Int,
    @SerializedName("allow_upload_cover_time")
    val allowUploadCoverTime: Int,
    @SerializedName("area_id")
    val areaId: Int,
    @SerializedName("area_name")
    val areaName: String,
    @SerializedName("area_pendants")
    val areaPendants: String,
    @SerializedName("attention")
    val attention: Int,
    @SerializedName("background")
    val background: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("hot_words")
    val hotWords: List<String>,
    @SerializedName("hot_words_status")
    val hotWordsStatus: Int,
    @SerializedName("is_anchor")
    val isAnchor: Int,
    @SerializedName("is_portrait")
    val isPortrait: Boolean,
    @SerializedName("is_strict_room")
    val isStrictRoom: Boolean,
    @SerializedName("keyframe")
    val keyframe: String,
    @SerializedName("live_status")
    val liveStatus: Int,
    @SerializedName("live_time")
    val liveTime: String,
    @SerializedName("new_pendants")
    val newPendants: NewPendants,
    @SerializedName("old_area_id")
    val oldAreaId: Int,
    @SerializedName("online")
    val online: Int,
    @SerializedName("parent_area_id")
    val parentAreaId: Int,
    @SerializedName("parent_area_name")
    val parentAreaName: String,
    @SerializedName("pendants")
    val pendants: String,
    @SerializedName("pk_id")
    val pkId: Int,
    @SerializedName("pk_status")
    val pkStatus: Int,
    @SerializedName("room_id")
    val roomId: Int,
    @SerializedName("room_silent_level")
    val roomSilentLevel: Int,
    @SerializedName("room_silent_second")
    val roomSilentSecond: Int,
    @SerializedName("room_silent_type")
    val roomSilentType: String,
    @SerializedName("short_id")
    val shortId: Int,
    @SerializedName("tags")
    val tags: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("uid")
    val uid: Int,
    @SerializedName("up_session")
    val upSession: String,
    @SerializedName("user_cover")
    val userCover: String,
    @SerializedName("verify")
    val verify: String
) {
    data class NewPendants(
        @SerializedName("badge")
        val badge: Badge?,
        @SerializedName("frame")
        val frame: Any,
        @SerializedName("mobile_badge")
        val mobileBadge: Any,
        @SerializedName("mobile_frame")
        val mobileFrame: Any
    ) {
        data class Badge(
            @SerializedName("desc")
            val desc: String?,
            @SerializedName("name")
            val name: String,
            @SerializedName("position")
            val position: Int,
            @SerializedName("value")
            val value: String
        )
    }
}