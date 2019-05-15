package com.bilibili.lingxiao.home.live.view

import com.bilibili.lingxiao.home.live.model.LiveTabData
import com.camera.lingxiao.common.app.BaseView

interface LiveTabView :BaseView{
    fun onGetTabList(tabs :List<LiveTabData.Tab>)
}