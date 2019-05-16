package com.bilibili.lingxiao.home.live.model

import com.google.gson.annotations.SerializedName

data class LiveTabData(
    @SerializedName("data")
    val `data`: List<Tab>
) {
    data class Tab(
        @SerializedName("act_id")
        val actId: String,
        @SerializedName("area_type")
        val areaType: Int,
        @SerializedName("hot_status")
        val hotStatus: Int,
        @SerializedName("id")
        val id: String?,
        @SerializedName("lock_status")
        val lockStatus: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("old_area_id")
        val oldAreaId: String,
        @SerializedName("parent_id")
        val parentId: String?,
        @SerializedName("parent_name")
        val parentName: String,
        @SerializedName("pic")
        val pic: String,
        @SerializedName("pk_status")
        val pkStatus: String
    )
}