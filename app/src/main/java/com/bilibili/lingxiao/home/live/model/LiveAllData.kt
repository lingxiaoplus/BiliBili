package com.bilibili.lingxiao.home.live.model

import com.google.gson.annotations.SerializedName

data class LiveAllData(
    @SerializedName("banner")
    val banner: List<Any>,
    @SerializedName("count")
    val count: Int,
    @SerializedName("list")
    val list: List<LiveInfo>,
    @SerializedName("new_tags")
    val newTags: List<NewTag>,
    @SerializedName("tags")
    val tags: List<Tag>
) {
    data class LiveInfo(
        @SerializedName("area_id")
        val areaId: Int,
        @SerializedName("area_name")
        val areaName: String,
        @SerializedName("area_v2_id")
        val areaV2Id: Int,
        @SerializedName("area_v2_name")
        val areaV2Name: String,
        @SerializedName("area_v2_parent_id")
        val areaV2ParentId: Int,
        @SerializedName("area_v2_parent_name")
        val areaV2ParentName: String,
        @SerializedName("face")
        val face: String,
        @SerializedName("group_id")
        val groupId: Int,
        @SerializedName("link")
        val link: String,
        @SerializedName("online")
        val online: Int,
        @SerializedName("parent_id")
        val parentId: Int,
        @SerializedName("parent_name")
        val parentName: String,
        @SerializedName("pk_id")
        val pkId: Int,
        @SerializedName("roomid")
        val roomid: Int,
        @SerializedName("session_id")
        val sessionId: String,
        @SerializedName("show_cover")
        val showCover: String,
        @SerializedName("system_cover")
        val systemCover: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("uid")
        val uid: Int,
        @SerializedName("uname")
        val uname: String,
        @SerializedName("user_cover")
        val userCover: String,
        @SerializedName("user_cover_flag")
        val userCoverFlag: Int,
        @SerializedName("web_pendent")
        val webPendent: String,
        @SerializedName("play_url")
        val playUrl:String,
        @SerializedName("play_url_h265")
        val playUrlH265:String,
        @SerializedName("play_url_card")
        val playUrlCard:String,
        @SerializedName("quality_description")
        val qualitys:List<QualityDescription>,
        @SerializedName("current_quality")
        val currentQuality:Int
    ){
        data class QualityDescription(@SerializedName("qn")
                                      val qn:Int,@SerializedName("desc")
        val desc:String)
    }

    data class NewTag(
        @SerializedName("hero_list")
        val heroList: List<Any>,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("sort_type")
        val sortType: String,
        @SerializedName("sub")
        val sub: List<Any>,
        @SerializedName("type")
        val type: Int
    )

    data class Tag(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("sort_type")
        val sortType: String
    )
}