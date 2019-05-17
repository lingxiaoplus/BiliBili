package com.bilibili.lingxiao.home.category.view

import com.bilibili.lingxiao.home.category.model.RegionDetailData
import com.camera.lingxiao.common.app.BaseView

interface RegionDetailView :BaseView{
    fun onGetRegionDetail(data :RegionDetailData)
}