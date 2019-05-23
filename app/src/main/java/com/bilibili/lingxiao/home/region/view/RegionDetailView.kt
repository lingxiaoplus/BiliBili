package com.bilibili.lingxiao.home.region.view

import com.bilibili.lingxiao.home.region.model.RegionDetailData
import com.camera.lingxiao.common.app.BaseView

interface RegionDetailView :BaseView{
    fun onGetRegionDetail(data :RegionDetailData)
    fun onGetRegionMore(data :RegionDetailData)
}