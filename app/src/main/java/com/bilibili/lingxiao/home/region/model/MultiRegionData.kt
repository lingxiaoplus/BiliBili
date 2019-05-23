package com.bilibili.lingxiao.home.region.model

data class MultiRegionData(val itemType:Int) {
    companion object {
        val REGION_ITEM = 1
        val REGION_RECOMMEND = 2
    }


    var regionData: RegionData.Data? = null
    var recommendData: RegionRecommendData.Data? = null

}