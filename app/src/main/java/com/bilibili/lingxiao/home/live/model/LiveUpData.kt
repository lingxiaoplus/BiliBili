package com.bilibili.lingxiao.home.live.model

import com.google.gson.annotations.SerializedName

data class LiveUpData(
    @SerializedName("activity_banner_info")
    val activityBannerInfo: ActivityBannerInfo,
    @SerializedName("activity_lol_match_info")
    val activityLolMatchInfo: ActivityLolMatchInfo,
    @SerializedName("activity_score_info")
    val activityScoreInfo: Any,
    @SerializedName("anchor_info")
    val anchorInfo: AnchorInfo,
    @SerializedName("anchor_reward")
    val anchorReward: AnchorReward,
    @SerializedName("guard_buy_info")
    val guardBuyInfo: GuardBuyInfo,
    @SerializedName("guard_info")
    val guardInfo: GuardInfo,
    @SerializedName("pk_info")
    val pkInfo: Any,
    @SerializedName("rankdb_info")
    val rankdbInfo: RankdbInfo,
    @SerializedName("room_info")
    val roomInfo: RoomInfo,
    @SerializedName("round_video_info")
    val roundVideoInfo: Any,
    @SerializedName("skin_info")
    val skinInfo: SkinInfo,
    @SerializedName("tab_info")
    val tabInfo: List<TabInfo>
) {
    data class AnchorInfo(
        @SerializedName("base_info")
        val baseInfo: BaseInfo,
        @SerializedName("live_info")
        val liveInfo: LiveInfo,
        @SerializedName("relation_info")
        val relationInfo: RelationInfo
    ) {
        data class BaseInfo(
            @SerializedName("face")
            val face: String,
            @SerializedName("gender")
            val gender: String,
            @SerializedName("official_info")
            val officialInfo: OfficialInfo,
            @SerializedName("uname")
            val uname: String
        ) {
            data class OfficialInfo(
                @SerializedName("desc")
                val desc: String,
                @SerializedName("role")
                val role: Int,
                @SerializedName("title")
                val title: String
            )
        }

        data class LiveInfo(
            @SerializedName("level")
            val level: Int,
            @SerializedName("level_color")
            val levelColor: Int
        )

        data class RelationInfo(
            @SerializedName("attention")
            val attention: Int
        )
    }

    data class TabInfo(
        @SerializedName("default")
        val default: Int,
        @SerializedName("default_sub_tab")
        val defaultSubTab: String,
        @SerializedName("desc")
        val desc: String,
        @SerializedName("order")
        val order: Int,
        @SerializedName("status")
        val status: Int,
        @SerializedName("sub_tab")
        val subTab: List<Any>,
        @SerializedName("type")
        val type: String,
        @SerializedName("url")
        val url: String
    )

    data class GuardBuyInfo(
        @SerializedName("count")
        val count: Int,
        @SerializedName("duration")
        val duration: Int,
        @SerializedName("list")
        val list: List<Any>
    )

    data class SkinInfo(
        @SerializedName("current_time")
        val currentTime: Int,
        @SerializedName("end_time")
        val endTime: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("skin_config")
        val skinConfig: String,
        @SerializedName("start_time")
        val startTime: Int
    )

    data class GuardInfo(
        @SerializedName("count")
        val count: Int
    )

    data class AnchorReward(
        @SerializedName("wish_open")
        val wishOpen: Boolean
    )

    data class RankdbInfo(
        @SerializedName("color")
        val color: String,
        @SerializedName("h5_url")
        val h5Url: String,
        @SerializedName("rank_desc")
        val rankDesc: String,
        @SerializedName("timestamp")
        val timestamp: Int,
        @SerializedName("web_url")
        val webUrl: String
    )

    data class ActivityBannerInfo(
        @SerializedName("bottom")
        val bottom: List<Any>,
        @SerializedName("gift_banner")
        val giftBanner: GiftBanner,
        @SerializedName("inputBanner")
        val inputBanner: List<Any>,
        @SerializedName("lol_activity")
        val lolActivity: LolActivity,
        @SerializedName("superBanner")
        val superBanner: Any,
        @SerializedName("top")
        val top: List<Top>
    ) {
        data class Top(
            @SerializedName("activity_title")
            val activityTitle: String,
            @SerializedName("color")
            val color: String,
            @SerializedName("cover")
            val cover: String,
            @SerializedName("expire_hour")
            val expireHour: Int,
            @SerializedName("gift_img")
            val giftImg: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("is_close")
            val isClose: Int,
            @SerializedName("jump_url")
            val jumpUrl: String,
            @SerializedName("rank")
            val rank: String,
            @SerializedName("rank_name")
            val rankName: String,
            @SerializedName("title")
            val title: String,
            @SerializedName("type")
            val type: Int,
            @SerializedName("week_gift_color")
            val weekGiftColor: String,
            @SerializedName("week_rank_color")
            val weekRankColor: String,
            @SerializedName("week_text_color")
            val weekTextColor: String
        )

        data class GiftBanner(
            @SerializedName("img")
            val img: List<Any>,
            @SerializedName("interval")
            val interval: Int
        )

        data class LolActivity(
            @SerializedName("guess_cover")
            val guessCover: String,
            @SerializedName("status")
            val status: Int,
            @SerializedName("vote_cover")
            val voteCover: String
        )
    }

    data class ActivityLolMatchInfo(
        @SerializedName("commentatorInfo")
        val commentatorInfo: List<Any>,
        @SerializedName("guess_info")
        val guessInfo: List<Any>,
        @SerializedName("match_id")
        val matchId: Int,
        @SerializedName("round")
        val round: Int,
        @SerializedName("status")
        val status: Int,
        @SerializedName("team_info")
        val teamInfo: List<Any>,
        @SerializedName("timestamp")
        val timestamp: Int,
        @SerializedName("vote_config")
        val voteConfig: VoteConfig
    ) {
        data class VoteConfig(
            @SerializedName("price")
            val price: Int,
            @SerializedName("status")
            val status: Int,
            @SerializedName("vote_nums")
            val voteNums: List<Int>
        )
    }

    data class RoomInfo(
        @SerializedName("area_id")
        val areaId: Int,
        @SerializedName("area_name")
        val areaName: String,
        @SerializedName("background")
        val background: String,
        @SerializedName("cover")
        val cover: String,
        @SerializedName("description")
        val description: String?,
        @SerializedName("hidden_status")
        val hiddenStatus: Int,
        @SerializedName("hidden_time")
        val hiddenTime: Int,
        @SerializedName("keyframe")
        val keyframe: String,
        @SerializedName("live_screen_type")
        val liveScreenType: Int,
        @SerializedName("live_start_time")
        val liveStartTime: Int,
        @SerializedName("live_status")
        val liveStatus: Int,
        @SerializedName("lock_status")
        val lockStatus: Int,
        @SerializedName("lock_time")
        val lockTime: Int,
        @SerializedName("online")
        val online: Int,
        @SerializedName("parent_area_id")
        val parentAreaId: Int,
        @SerializedName("parent_area_name")
        val parentAreaName: String,
        @SerializedName("pendants")
        val pendants: Pendants,
        @SerializedName("pk_status")
        val pkStatus: Int,
        @SerializedName("room_id")
        val roomId: Int,
        @SerializedName("short_id")
        val shortId: Int,
        @SerializedName("special_type")
        val specialType: Int,
        @SerializedName("tags")
        val tags: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("uid")
        val uid: Int,
        @SerializedName("up_session")
        val upSession: String
    ) {
        data class Pendants(
            @SerializedName("badge")
            val badge: Any,
            @SerializedName("frame")
            val frame: Any
        )
    }
}