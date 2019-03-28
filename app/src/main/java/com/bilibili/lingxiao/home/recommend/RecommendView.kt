package com.bilibili.lingxiao.home.recommend

import com.camera.lingxiao.common.app.BaseView

interface RecommendView :BaseView{
    fun onGetRecommendData(recommendData: List<RecommendData>)
}