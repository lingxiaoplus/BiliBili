package com.bilibili.lingxiao.home.live.model

import com.google.gson.annotations.SerializedName

data class LiveUserData(
    @SerializedName("area_name")
    val areaName: String,
    @SerializedName("attention_num")
    val attentionNum: Int,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("face")
    val face: String,
    @SerializedName("fans_medal")
    val fansMedal: Any,
    @SerializedName("follow_num")
    val followNum: Int,
    @SerializedName("is_admin")
    val isAdmin: Int,
    @SerializedName("is_block")
    val isBlock: Int,
    @SerializedName("level_color")
    val levelColor: Int,
    @SerializedName("mailbox_notice")
    val mailboxNotice: Int,
    @SerializedName("mailbox_switch")
    val mailboxSwitch: Int,
    @SerializedName("main_vip")
    val mainVip: Int,
    @SerializedName("month_vip")
    val monthVip: Int,
    @SerializedName("pendant")
    val pendant: String,
    @SerializedName("pendant_from")
    val pendantFrom: Int,
    @SerializedName("privilege_type")
    val privilegeType: Int,
    @SerializedName("relation_status")
    val relationStatus: Int,
    @SerializedName("room_id")
    val roomId: Int,
    @SerializedName("title_mark")
    val titleMark: String,
    @SerializedName("uid")
    val uid: Int,
    @SerializedName("uname")
    val uname: String,
    @SerializedName("uname_color")
    val unameColor: Int,
    @SerializedName("user_level")
    val userLevel: Int,
    @SerializedName("verify_type")
    val verifyType: Int,
    @SerializedName("year_vip")
    val yearVip: Int
)