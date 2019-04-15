package com.bilibili.lingxiao.home.category

import com.camera.lingxiao.common.app.BaseView

interface RegionView :BaseView{
    fun onGetRegion(regionData: List<RegionData.Data>)
}