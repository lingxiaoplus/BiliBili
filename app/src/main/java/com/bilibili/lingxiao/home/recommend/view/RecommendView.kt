package com.bilibili.lingxiao.home.recommend.view

import com.bilibili.lingxiao.home.recommend.model.RecommendData
import com.bilibili.lingxiao.play.model.CommentData
import com.bilibili.lingxiao.play.model.VideoDetailData
import com.bilibili.lingxiao.play.model.VideoRecoData
import com.camera.lingxiao.common.app.BaseView

interface RecommendView :BaseView{
    fun onGetRecommendData(recommendData: List<RecommendData>)
    fun onGetVideoDetail(videoDetailData: VideoDetailData)
    fun onGetVideoRecommend(videoRecoData: VideoRecoData)
    fun onGetVideoComment(commentData: CommentData)
}