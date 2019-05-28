package com.bilibili.lingxiao.home.region.model

import com.google.gson.annotations.SerializedName

data class BangumiDetailData(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: Result?
) {
    data class Result(
        @SerializedName("actor")
        val actor: List<Actor>,
        @SerializedName("alias")
        val alias: String,
        @SerializedName("allow_bp")
        val allowBp: String,
        @SerializedName("allow_download")
        val allowDownload: String,
        @SerializedName("area")
        val area: String,
        @SerializedName("arealimit")
        val arealimit: Int,
        @SerializedName("bangumi_id")
        val bangumiId: String,
        @SerializedName("bangumi_title")
        val bangumiTitle: String,
        @SerializedName("brief")
        val brief: String,
        @SerializedName("business_type")
        val businessType: Int,
        @SerializedName("coins")
        val coins: String,
        @SerializedName("copyright")
        val copyright: String,
        @SerializedName("cover")
        val cover: String,
        @SerializedName("danmaku_count")
        val danmakuCount: String,
        @SerializedName("dm_seg")
        val dmSeg: Int,
        @SerializedName("ed_jump")
        val edJump: Int,
        @SerializedName("episodes")
        val episodes: List<Episode>?,
        @SerializedName("evaluate")
        val evaluate: String,
        @SerializedName("favorites")
        val favorites: String,
        @SerializedName("has_unfollow")
        val hasUnfollow: Int,
        @SerializedName("is_finish")
        val isFinish: String,
        @SerializedName("is_guide_follow")
        val isGuideFollow: Int,
        @SerializedName("jp_title")
        val jpTitle: String,
        @SerializedName("limit_info")
        val limitInfo: LimitInfo,
        @SerializedName("media")
        val media: Media,
        @SerializedName("newest_ep_id")
        val newestEpId: String,
        @SerializedName("newest_ep_index")
        val newestEpIndex: String,
        @SerializedName("origin_name")
        val originName: String,
        @SerializedName("play_count")
        val playCount: String,
        @SerializedName("pub_string")
        val pubString: String,
        @SerializedName("pub_time")
        val pubTime: String,
        @SerializedName("pub_time_show")
        val pubTimeShow: String,
        @SerializedName("rank")
        val rank: Rank,
        @SerializedName("related_seasons")
        val relatedSeasons: List<Any>,
        @SerializedName("rights")
        val rights: Rights,
        @SerializedName("season_id")
        val seasonId: String,
        @SerializedName("season_status")
        val seasonStatus: Int,
        @SerializedName("season_title")
        val seasonTitle: String,
        @SerializedName("seasons")
        val seasons: List<Season>?,
        @SerializedName("share_url")
        val shareUrl: String,
        @SerializedName("spid")
        val spid: String,
        @SerializedName("squareCover")
        val squareCover: String,
        @SerializedName("staff")
        val staff: String,
        @SerializedName("tag2s")
        val tag2s: List<Any>,
        @SerializedName("tags")
        val tags: List<Tag>,
        @SerializedName("title")
        val title: String,
        @SerializedName("total_count")
        val totalCount: String,
        @SerializedName("update_pattern")
        val updatePattern: String,
        @SerializedName("user_season")
        val userSeason: UserSeason,
        @SerializedName("viewRank")
        val viewRank: Int,
        @SerializedName("vip_quality")
        val vipQuality: Int,
        @SerializedName("watchingCount")
        val watchingCount: String,
        @SerializedName("weekday")
        val weekday: String
    ) {
        data class Episode(
            @SerializedName("av_id")
            val avId: String,
            @SerializedName("coins")
            val coins: String,
            @SerializedName("cover")
            val cover: String,
            @SerializedName("danmaku")
            val danmaku: String,
            @SerializedName("episode_id")
            val episodeId: String,
            @SerializedName("episode_status")
            val episodeStatus: Int,
            @SerializedName("from")
            val from: String,
            @SerializedName("index")
            val index: String,
            @SerializedName("index_title")
            val indexTitle: String,
            @SerializedName("is_webplay")
            val isWebplay: String,
            @SerializedName("mid")
            val mid: String,
            @SerializedName("page")
            val page: String,
            @SerializedName("up")
            val up: Up,
            @SerializedName("update_time")
            val updateTime: String
        ) {
            class Up(
            )
        }

        data class LimitInfo(
            @SerializedName("code")
            val code: Int,
            @SerializedName("data")
            val `data`: Data,
            @SerializedName("message")
            val message: String
        ) {
            data class Data(
                @SerializedName("down")
                val down: Int,
                @SerializedName("play")
                val play: Int
            )
        }

        data class Rights(
            @SerializedName("arealimit")
            val arealimit: Int,
            @SerializedName("is_started")
            val isStarted: Int
        )

        data class UserSeason(
            @SerializedName("attention")
            val attention: String,
            @SerializedName("bp")
            val bp: Int,
            @SerializedName("last_ep_index")
            val lastEpIndex: String,
            @SerializedName("last_time")
            val lastTime: String,
            @SerializedName("report_ts")
            val reportTs: Int
        )

        data class Actor(
            @SerializedName("actor")
            val actor: String,
            @SerializedName("actor_id")
            val actorId: Int,
            @SerializedName("role")
            val role: String
        )

        data class Media(
            @SerializedName("area")
            val area: List<Area>,
            @SerializedName("cover")
            val cover: String,
            @SerializedName("episode_index")
            val episodeIndex: EpisodeIndex,
            @SerializedName("media_id")
            val mediaId: Int,
            @SerializedName("publish")
            val publish: Publish,
            @SerializedName("rating")
            val rating: Rating,
            @SerializedName("title")
            val title: String,
            @SerializedName("type_id")
            val typeId: Int,
            @SerializedName("type_name")
            val typeName: String
        ) {
            data class EpisodeIndex(
                @SerializedName("index_show")
                val indexShow: String
            )

            data class Rating(
                @SerializedName("count")
                val count: Int,
                @SerializedName("score")
                val score: Double
            )

            data class Publish(
                @SerializedName("is_finish")
                val isFinish: Int,
                @SerializedName("is_started")
                val isStarted: Int
            )

            data class Area(
                @SerializedName("id")
                val id: Int,
                @SerializedName("name")
                val name: String
            )
        }

        data class Season(
            @SerializedName("bangumi_id")
            val bangumiId: String,
            @SerializedName("cover")
            val cover: String,
            @SerializedName("is_finish")
            val isFinish: String,
            @SerializedName("newest_ep_id")
            val newestEpId: String,
            @SerializedName("newest_ep_index")
            val newestEpIndex: String,
            @SerializedName("season_id")
            val seasonId: String,
            @SerializedName("season_status")
            val seasonStatus: Int,
            @SerializedName("title")
            val title: String,
            @SerializedName("total_count")
            val totalCount: String
        )

        data class Rank(
            @SerializedName("list")
            val list: List<X>,
            @SerializedName("total_bp_count")
            val totalBpCount: Int,
            @SerializedName("week_bp_count")
            val weekBpCount: Int
        ) {
            data class X(
                @SerializedName("face")
                val face: String,
                @SerializedName("uid")
                val uid: String,
                @SerializedName("uname")
                val uname: String,
                @SerializedName("vip")
                val vip: Vip
            ) {
                data class Vip(
                    @SerializedName("accessStatus")
                    val accessStatus: Int,
                    @SerializedName("dueRemark")
                    val dueRemark: String,
                    @SerializedName("themeType")
                    val themeType: Int,
                    @SerializedName("vipStatus")
                    val vipStatus: Int,
                    @SerializedName("vipStatusWarn")
                    val vipStatusWarn: String,
                    @SerializedName("vipType")
                    val vipType: Int
                )
            }
        }

        data class Tag(
            @SerializedName("cover")
            val cover: String,
            @SerializedName("tag_id")
            val tagId: String,
            @SerializedName("tag_name")
            val tagName: String
        )
    }
}