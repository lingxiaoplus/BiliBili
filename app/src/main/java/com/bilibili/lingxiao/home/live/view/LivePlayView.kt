package com.bilibili.lingxiao.home.live.view

import com.bilibili.lingxiao.home.live.model.*
import com.camera.lingxiao.common.app.BaseView

interface LivePlayView :BaseView{
    fun onGetUpInfo(liveUpData: LiveUpData)
    fun onGetUserInfo(liveUpData: LiveUserData)
    fun onGetFleetList(fleetListData: FleetListData)
    fun onGetUpVideoList(list: List<UpInfoData>)
    fun onGetUpChatHistory(list: List<LiveChatData.Room>)
}