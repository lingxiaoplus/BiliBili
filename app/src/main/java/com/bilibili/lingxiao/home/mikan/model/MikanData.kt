package com.bilibili.lingxiao.home.mikan.model

data class MikanData(var type:Int){
    companion object{
        val LOGIN_HEADER = 0
        val TOP_BAR = 1
        val BANGUMI_ITEM = 2
        val NEWS = 3
    }
    lateinit var mikanRecommend:MiKanRecommendData.Result.Recommend.Info
    lateinit var mikanFall:MiKanFallData.Result
    lateinit var titleBar:TitleBarData

    data class TitleBarData(var title:String,var showMore:Boolean){
        var imageId:Int = -1
    }
}