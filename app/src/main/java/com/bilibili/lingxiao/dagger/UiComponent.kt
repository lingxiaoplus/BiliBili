package com.bilibili.lingxiao.dagger

import com.bilibili.lingxiao.MainActivity
import com.bilibili.lingxiao.ViewModule
import com.bilibili.lingxiao.home.hot.HotFragment
import com.bilibili.lingxiao.home.live.LiveFragment
import com.bilibili.lingxiao.home.mikan.MikanFragment
import com.bilibili.lingxiao.home.recommend.RecommendFragment
import com.bilibili.lingxiao.dagger.scope.PerUi
import dagger.Component

@Component(modules = [UiModule::class])
@PerUi
interface UiComponent {
    fun inject(liveFragment: LiveFragment)
    fun inject(recommendFragment: RecommendFragment)
    fun inject(hotFragment: HotFragment)
    fun inject(mikanFragment: MikanFragment)

    fun inject(mainActivity: MainActivity)
}