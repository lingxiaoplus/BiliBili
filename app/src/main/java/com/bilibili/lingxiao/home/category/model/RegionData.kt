package com.bilibili.lingxiao.home.category.model

import com.google.gson.annotations.SerializedName

data class RegionData(
    @SerializedName("data")
    val `data`: List<Data>
) {
    data class Data(
        @SerializedName("children")
        val children: List<Children>,
        @SerializedName("goto")
        val goto: String,
        @SerializedName("is_bangumi")
        val isBangumi: Int,
        @SerializedName("logo")
        val logo: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("param")
        val `param`: String,
        @SerializedName("reid")
        val reid: Int,
        @SerializedName("tid")
        val tid: Int,
        @SerializedName("type")
        val type: Int,
        @SerializedName("uri")
        val uri: String
    ) {
        data class Children(
            @SerializedName("goto")
            val goto: String,
            @SerializedName("logo")
            val logo: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("param")
            val `param`: String,
            @SerializedName("reid")
            val reid: Int,
            @SerializedName("tid")
            val tid: Int,
            @SerializedName("type")
            val type: Int
        )
    }
}