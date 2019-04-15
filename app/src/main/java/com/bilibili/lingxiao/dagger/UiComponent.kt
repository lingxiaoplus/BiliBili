package com.bilibili.lingxiao.dagger

import com.bilibili.lingxiao.MainActivity
import com.bilibili.lingxiao.home.category.CategoryFragment
import com.bilibili.lingxiao.home.live.LiveFragment
import com.bilibili.lingxiao.home.mikan.MikanFragment
import com.bilibili.lingxiao.home.recommend.ui.RecommendFragment
import com.bilibili.lingxiao.dagger.scope.PerUi
import com.bilibili.lingxiao.play.CommentFragment
import com.bilibili.lingxiao.play.IntroduceFragment
import com.bilibili.lingxiao.play.PlayActivity
import dagger.Component

@Component(modules = [UiModule::class])
@PerUi
interface UiComponent {
    fun inject(liveFragment: LiveFragment)
    fun inject(recommendFragment: RecommendFragment)
    fun inject(hotFragment: CategoryFragment)
    fun inject(mikanFragment: MikanFragment)
    fun inject(introduceFragment: IntroduceFragment)
    fun inject(commentFragment: CommentFragment)

    fun inject(mainActivity: MainActivity)
    fun inject(playActivity: PlayActivity)
}