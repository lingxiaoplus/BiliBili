package com.bilibili.lingxiao.home.find

import com.bilibili.lingxiao.home.find.model.RankListData
import com.camera.lingxiao.common.app.BaseView

interface RankListView :BaseView{
    fun onGetRankList(data :MutableList<RankListData.Item>)
}