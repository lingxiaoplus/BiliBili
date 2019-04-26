package com.bilibili.lingxiao.home.live

import com.bilibili.lingxiao.home.live.ui.LiveFragment

//@Component(modules = arrayOf(ViewModule::class))
interface LivePlatform{
    fun getLiveList(liveFragment: LiveFragment)
}