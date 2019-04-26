package com.bilibili.lingxiao.home.live.view

import com.bilibili.lingxiao.home.live.model.LiveUpData
import com.bilibili.lingxiao.home.live.model.FleetListData
import com.bilibili.lingxiao.home.live.model.UpInfoData
import com.camera.lingxiao.common.app.BaseView

interface LivePlayView :BaseView{
    fun onGetUpInfo(liveUpData: LiveUpData)
    fun onGetFleetList(fleetListData: FleetListData)
    fun onGetUpVideoList(list: List<UpInfoData>)
}