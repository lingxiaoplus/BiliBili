package com.bilibili.lingxiao.home.category.view

import com.bilibili.lingxiao.home.category.model.RegionData
import com.bilibili.lingxiao.home.category.model.RegionRecommendData
import com.camera.lingxiao.common.app.BaseView

interface RegionView :BaseView{
    fun onGetRegion(regionData: List<RegionData.Data>)
    fun onGetRegionRecommend(recommendList: List<RegionRecommendData.Data>)
}