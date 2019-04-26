package com.bilibili.lingxiao.home.live.view

import com.bilibili.lingxiao.home.live.model.FansGoldListData
import com.camera.lingxiao.common.app.BaseView

interface FansDetailView :BaseView{
    fun onGetFansGoldList(fansList: FansGoldListData)

}