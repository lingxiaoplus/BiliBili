package com.bilibili.lingxiao.home.region.view

import com.bilibili.lingxiao.home.region.model.BangumiDetailData
import com.bilibili.lingxiao.home.region.model.BangumiRecommendData
import com.camera.lingxiao.common.app.BaseView

interface BangumiView :BaseView{
    fun onGetBangumiDetail(data :BangumiDetailData)
    fun onGetBangumiRecommend(data: BangumiRecommendData)
}