package com.bilibili.lingxiao.home.recommend.model

data class RecommendData(
    val autoplay: Int,
    val autoplay_card: Int,
    val banner_item: List<BannerItem>,
    val cid: Int,
    val coin: Int,
    val cover: String,
    val ctime: Int,
    val danmaku: Int,
    val desc: String,
    val dislike_reasons: List<DislikeReason>,
    val duration: Int,
    val face: String,
    val favorite: Int,
    val goto: String,
    val hash: String,
    val idx: Int,
    val like: Int,
    val mid: Int,
    val name: String,
    val official: Official,
    val `param`: String,
    val play: Int,
    val reply: Int,
    val share: Int,
    val tag: Tag,
    val tid: Int,
    val title: String,
    val tname: String?,
    val uri: String
) {
    data class BannerItem(
        val ad_cb: String,
        val click_url: String,
        val client_ip: String,
        val cm_mark: Int,
        val creative_id: Int,
        val extra: Extra,
        val hash: String,
        val id: Int,
        val image: String,
        val index: Int,
        val is_ad: Boolean,
        val is_ad_loc: Boolean,
        val request_id: String,
        val resource_id: Int,
        val server_type: Int,
        val src_id: Int,
        val title: String,
        val uri: String
    ) {
        data class Extra(
            val card: Card,
            val click_urls: List<String>,
            val open_whitelist: List<String>,
            val preload_landingpage: Int,
            val report_time: Int,
            val sales_type: Int,
            val show_urls: List<Any>,
            val special_industry: Boolean,
            val special_industry_tips: String,
            val use_ad_web_v2: Boolean
        ) {
            data class Card(
                val ad_tag: String,
                val ad_tag_style: AdTagStyle,
                val button: Button,
                val callup_url: String,
                val card_type: Int,
                val covers: List<Cover>,
                val desc: String,
                val extra_desc: String,
                val jump_url: String,
                val long_desc: String,
                val title: String
            ) {
                data class Button(
                    val dlsuc_callup_url: String,
                    val jump_url: String,
                    val report_urls: List<Any>,
                    val text: String,
                    val type: Int
                )

                data class Cover(
                    val url: String
                )

                data class AdTagStyle(
                    val bg_border_color: String,
                    val text: String,
                    val text_color: String,
                    val type: Int
                )
            }
        }
    }

    data class Tag(
        val count: Count,
        val tag_id: Int,
        val tag_name: String
    ) {
        data class Count(
            val atten: Int
        )
    }

    data class Official(
        val role: Int,
        val title: String
    )

    data class DislikeReason(
        val reason_id: Int,
        val reason_name: String
    )
}