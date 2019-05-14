package com.bilibili.lingxiao.home.live.view

import com.bilibili.lingxiao.home.live.model.LiveAllData
import com.camera.lingxiao.common.app.BaseView

interface LiveAllView :BaseView{
    fun onGetHotList(data :LiveAllData)
    fun onGetNewList(data :LiveAllData)
}