package com.bilibili.lingxiao.home.mikan

import com.bilibili.lingxiao.home.mikan.model.MiKanFallData
import com.bilibili.lingxiao.home.mikan.model.MiKanRecommendData
import com.camera.lingxiao.common.app.BaseView

interface MikanView :BaseView{
    fun onGetMikanRecommend(data: MiKanRecommendData)
    fun onGetMikanFall(data: MiKanFallData)
}