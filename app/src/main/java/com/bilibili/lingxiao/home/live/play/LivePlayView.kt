package com.bilibili.lingxiao.home.live.play

import com.bilibili.lingxiao.home.live.model.LiveUpData
import com.camera.lingxiao.common.app.BaseView

interface LivePlayView :BaseView{
    fun onGetUpInfo(liveUpData: LiveUpData)
}