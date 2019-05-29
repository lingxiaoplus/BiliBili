package com.bilibili.lingxiao.home.region.model

import com.google.gson.annotations.SerializedName

data class BangumiRecommendData(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: Result?
) {
    data class Result(
        @SerializedName("from")
        val from: Int,
        @SerializedName("list")
        val list: List<BangumiInfo>?,
        @SerializedName("season_id")
        val seasonId: String,
        @SerializedName("title")
        val title: String
    ) {
        data class BangumiInfo(
            @SerializedName("bangumi_id")
            val bangumiId: String,
            @SerializedName("cover")
            val cover: String,
            @SerializedName("follow")
            val follow: String,
            @SerializedName("isfinish")
            val isfinish: String,
            @SerializedName("newest_ep_cover")
            val newestEpCover: String,
            @SerializedName("newest_ep_index")
            val newestEpIndex: String,
            @SerializedName("pub_time")
            val pubTime: String,
            @SerializedName("season_id")
            val seasonId: String,
            @SerializedName("season_status")
            val seasonStatus: Int,
            @SerializedName("tags")
            val tags: List<Tag>,
            @SerializedName("title")
            val title: String,
            @SerializedName("total_count")
            val totalCount: String
        ) {
            data class Tag(
                @SerializedName("tag_name")
                val tagName: String
            )
        }
    }
}