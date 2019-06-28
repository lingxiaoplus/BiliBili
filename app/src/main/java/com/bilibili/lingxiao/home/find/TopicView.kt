package com.bilibili.lingxiao.home.find

import com.bilibili.lingxiao.home.find.model.TopicCardData
import com.camera.lingxiao.common.app.BaseView

interface TopicView :BaseView{
    fun onGetTopicResult(data :TopicCardData)
}