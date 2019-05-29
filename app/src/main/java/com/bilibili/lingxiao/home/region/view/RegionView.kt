package com.bilibili.lingxiao.home.region.view

import com.bilibili.lingxiao.home.region.model.RegionData
import com.bilibili.lingxiao.home.region.model.RegionRecommendData
import com.camera.lingxiao.common.app.BaseView

interface RegionView :BaseView{
    fun onGetRegion(regionData: List<RegionData.Data>)
    fun onGetRegionRecommend(recommendList: List<RegionRecommendData.Data>)
    fun onRefreshRegion(list: List<RegionRecommendData.Data.Body>)
}