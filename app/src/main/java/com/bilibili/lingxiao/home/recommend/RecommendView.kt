package com.bilibili.lingxiao.home.recommend

import com.bilibili.lingxiao.play.model.VideoDetailData
import com.bilibili.lingxiao.play.model.VideoRecoData
import com.camera.lingxiao.common.app.BaseView

interface RecommendView :BaseView{
    fun onGetRecommendData(recommendData: List<RecommendData>)
    fun onGetVideoDetail(videoDetailData: VideoDetailData)
    fun onGetVideoRecommend(videoRecoData: VideoRecoData)
}