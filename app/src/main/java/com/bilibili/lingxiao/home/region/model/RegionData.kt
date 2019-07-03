package com.bilibili.lingxiao.home.region.model

import com.google.gson.annotations.SerializedName

data class RegionData(
    @SerializedName("data")
    var `data`: List<Data>
){
    /*@OneToMany(oneToManyMethods = [OneToManyMethod.ALL], variableName = "data")
    fun getList(): List<Data> {
        if (data == null || data.isEmpty()) {
            data = (select from(Data::class.java)).list
        }
        return data
    }*/
    data class Data(
        @SerializedName("children")
        var children: List<Children>,
        @SerializedName("goto")
        var goto: String,
        @SerializedName("is_bangumi")
        var isBangumi: Int,
        @SerializedName("logo")
        var logo: String,
        @SerializedName("name")
        var name: String,
        @SerializedName("param")
        var `param`: String,
        @SerializedName("reid")
        val reid: Int,
        @SerializedName("tid")
        var tid: Int,
        @SerializedName("type")
        var type: Int,
        @SerializedName("uri")
        var uri: String
    ) {
        data class Children(
            @SerializedName("goto")
            var goto: String,
            @SerializedName("logo")
            var logo: String,
            @SerializedName("name")
            var name: String,
            @SerializedName("param")
            var `param`: String,
            @SerializedName("reid")
            var reid: Int,
            @SerializedName("tid")
            var tid: Int,
            @SerializedName("type")
            var type: Int
        )
    }
}