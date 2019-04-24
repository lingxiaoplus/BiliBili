package com.bilibili.lingxiao.home.live

import com.bilibili.lingxiao.home.live.model.LiveData
import com.camera.lingxiao.common.app.BaseView

interface LiveView :BaseView{
    fun onGetLiveList(data: LiveData)
}