package com.bilibili.lingxiao.home.live.play.fans

import com.camera.lingxiao.common.app.BaseView

interface FansDetailView :BaseView{
    fun onGetFansGoldList(fansList: FansGoldListData)

}