package com.bilibili.lingxiao.home.find.model


import com.google.gson.annotations.SerializedName

data class SearchResultData(
    @SerializedName("array")
    val array: Int,
    @SerializedName("attribute")
    val attribute: Int,
    @SerializedName("item")
    val item: List<Item>,
    @SerializedName("items")
    val items: Items,
    @SerializedName("nav")
    val nav: List<Nav>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("trackid")
    val trackid: String
) {
    class Items(
    )

    data class Nav(
        @SerializedName("name")
        val name: String,
        @SerializedName("pages")
        val pages: Int,
        @SerializedName("total")
        val total: Int,
        @SerializedName("type")
        val type: Int
    )

    data class Item(
        @SerializedName("param")
        val `param`: String,
        @SerializedName("archives")
        val archives: Int,
        @SerializedName("attentions")
        val attentions: Int,
        @SerializedName("author")
        val author: String?,
        @SerializedName("av_items")
        val avItems: List<AvItem>,
        @SerializedName("badge")
        val badge: String,
        @SerializedName("cover")
        val cover: String?,
        @SerializedName("danmaku")
        val danmaku: Int?,
        @SerializedName("desc")
        val desc: String,
        @SerializedName("duration")
        val duration: String,
        @SerializedName("face")
        val face: String,
        @SerializedName("fans")
        val fans: Int,
        @SerializedName("goto")
        val goto: String,
        @SerializedName("is_up")
        val isUp: Boolean,
        @SerializedName("level")
        val level: Int,
        @SerializedName("linktype")
        val linktype: String,
        @SerializedName("live_uri")
        val liveUri: String,
        @SerializedName("mid")
        val mid: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("new_rec_tags")
        val newRecTags: List<NewRecTag>,
        @SerializedName("official_verify")
        val officialVerify: OfficialVerify,
        @SerializedName("online")
        val online: Int,
        @SerializedName("play")
        val play: Int?,
        @SerializedName("position")
        val position: Int,
        @SerializedName("rating")
        val rating: Double,
        @SerializedName("rec_tags")
        val recTags: List<String>,
        @SerializedName("region")
        val region: Int,
        @SerializedName("roomid")
        val roomid: Int,
        @SerializedName("sign")
        val sign: String,
        @SerializedName("tags")
        val tags: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("trackid")
        val trackid: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("uri")
        val uri: String,
        @SerializedName("vip")
        val vip: Vip
    ) {
        data class AvItem(
            @SerializedName("param")
            val `param`: String,
            @SerializedName("cover")
            val cover: String,
            @SerializedName("ctime")
            val ctime: Int,
            @SerializedName("danmaku")
            val danmaku: Int,
            @SerializedName("duration")
            val duration: String,
            @SerializedName("goto")
            val goto: String,
            @SerializedName("play")
            val play: Int,
            @SerializedName("title")
            val title: String,
            @SerializedName("uri")
            val uri: String
        )

        data class Vip(
            @SerializedName("due_date")
            val dueDate: Int,
            @SerializedName("label")
            val label: Label,
            @SerializedName("status")
            val status: Int,
            @SerializedName("theme_type")
            val themeType: Int,
            @SerializedName("type")
            val type: Int,
            @SerializedName("vip_pay_type")
            val vipPayType: Int
        ) {
            data class Label(
                @SerializedName("path")
                val path: String
            )
        }

        data class OfficialVerify(
            @SerializedName("desc")
            val desc: String,
            @SerializedName("type")
            val type: Int
        )

        data class NewRecTag(
            @SerializedName("bg_color")
            val bgColor: String,
            @SerializedName("bg_color_night")
            val bgColorNight: String,
            @SerializedName("bg_style")
            val bgStyle: Int,
            @SerializedName("border_color")
            val borderColor: String,
            @SerializedName("border_color_night")
            val borderColorNight: String,
            @SerializedName("text")
            val text: String,
            @SerializedName("text_color")
            val textColor: String,
            @SerializedName("text_color_night")
            val textColorNight: String
        )
    }
}