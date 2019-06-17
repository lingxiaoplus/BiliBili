package com.bilibili.lingxiao.home.mikan.model

import com.google.gson.annotations.SerializedName

data class MiKanRecommendData(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: Result
) {
    data class Result(
        @SerializedName("ad")
        val ad: List<Any>,
        @SerializedName("recommend_cn")
        val recommendCn: Recommend,
        @SerializedName("recommend_jp")
        val recommendJp: Recommend,
        @SerializedName("recommend_review")
        val recommendReview: List<Any>,
        @SerializedName("timeline")
        val timeline: List<Timeline>
    ) {
        data class Recommend(
            @SerializedName("foot")
            val foot: List<Foot>,
            @SerializedName("recommend")
            val recommend: List<Info>
        ) {
            data class Foot(
                @SerializedName("cover")
                val cover: String,
                @SerializedName("desc")
                val desc: String?,
                @SerializedName("id")
                val id: Int,
                @SerializedName("is_auto")
                val isAuto: Int,
                @SerializedName("link")
                val link: String,
                @SerializedName("title")
                val title: String,
                @SerializedName("wid")
                val wid: Int
            )

            data class Info(
                @SerializedName("cover")
                val cover: String,
                @SerializedName("favourites")
                val favourites: String,
                @SerializedName("is_auto")
                val isAuto: Int,
                @SerializedName("is_finish")
                val isFinish: Int,
                @SerializedName("is_started")
                val isStarted: Int,
                @SerializedName("last_time")
                val lastTime: Int,
                @SerializedName("newest_ep_index")
                val newestEpIndex: String,
                @SerializedName("pub_time")
                val pubTime: Int,
                @SerializedName("season_id")
                val seasonId: Int,
                @SerializedName("season_status")
                val seasonStatus: Int,
                @SerializedName("title")
                val title: String,
                @SerializedName("total_count")
                val totalCount: Int,
                @SerializedName("watching_count")
                val watchingCount: Int,
                @SerializedName("wid")
                val wid: Int
            )
        }

        data class Timeline(
            @SerializedName("area_id")
            val areaId: Int,
            @SerializedName("cover")
            val cover: String,
            @SerializedName("delay")
            val delay: Int,
            @SerializedName("ep_id")
            val epId: Int,
            @SerializedName("favorites")
            val favorites: Int,
            @SerializedName("follow")
            val follow: Int,
            @SerializedName("is_published")
            val isPublished: Int,
            @SerializedName("order")
            val order: Int,
            @SerializedName("pub_date")
            val pubDate: String,
            @SerializedName("pub_index")
            val pubIndex: String,
            @SerializedName("pub_time")
            val pubTime: String,
            @SerializedName("pub_ts")
            val pubTs: Int,
            @SerializedName("season_id")
            val seasonId: Int,
            @SerializedName("season_status")
            val seasonStatus: Int,
            @SerializedName("square_cover")
            val squareCover: String,
            @SerializedName("title")
            val title: String
        )
    }
}