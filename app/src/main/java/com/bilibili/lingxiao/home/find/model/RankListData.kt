package com.bilibili.lingxiao.home.find.model


import com.google.gson.annotations.SerializedName

data class RankListData(
    @SerializedName("data")
    val `data`: List<Item>
) {
    data class Item(
        @SerializedName("param")
        val `param`: String,
        @SerializedName("children")
        val children: List<Children>?,
        @SerializedName("cid")
        val cid: Int,
        @SerializedName("cover")
        val cover: String,
        @SerializedName("danmaku")
        val danmaku: Int,
        @SerializedName("duration")
        val duration: Int,
        @SerializedName("face")
        val face: String,
        @SerializedName("favourite")
        val favourite: Int,
        @SerializedName("follower")
        val follower: Int,
        @SerializedName("goto")
        val goto: String,
        @SerializedName("like")
        val like: Int,
        @SerializedName("mid")
        val mid: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("official_verify")
        val officialVerify: OfficialVerify,
        @SerializedName("play")
        val play: Int,
        @SerializedName("pts")
        val pts: Int,
        @SerializedName("pubdate")
        val pubdate: Int,
        @SerializedName("reply")
        val reply: Int,
        @SerializedName("rid")
        val rid: Int,
        @SerializedName("rname")
        val rname: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("uri")
        val uri: String
    ) {
        data class OfficialVerify(
            @SerializedName("desc")
            val desc: String,
            @SerializedName("type")
            val type: Int
        )

        data class Children(
            @SerializedName("param")
            val `param`: String,
            @SerializedName("cid")
            val cid: Int,
            @SerializedName("cover")
            val cover: String,
            @SerializedName("danmaku")
            val danmaku: Int,
            @SerializedName("duration")
            val duration: Int,
            @SerializedName("face")
            val face: String,
            @SerializedName("favourite")
            val favourite: Int,
            @SerializedName("goto")
            val goto: String,
            @SerializedName("like")
            val like: Int,
            @SerializedName("mid")
            val mid: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("play")
            val play: Int,
            @SerializedName("pts")
            val pts: Int,
            @SerializedName("pubdate")
            val pubdate: Int,
            @SerializedName("reply")
            val reply: Int,
            @SerializedName("rid")
            val rid: Int,
            @SerializedName("rname")
            val rname: String,
            @SerializedName("title")
            val title: String,
            @SerializedName("uri")
            val uri: String
        )
    }
}