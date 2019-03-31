package com.bilibili.lingxiao.home.live

import com.bilibili.lingxiao.MainActivity
import com.bilibili.lingxiao.ViewModule
import dagger.Component

//@Component(modules = arrayOf(ViewModule::class))
interface LivePlatform{
    fun getLiveList(liveFragment: LiveFragment)
}