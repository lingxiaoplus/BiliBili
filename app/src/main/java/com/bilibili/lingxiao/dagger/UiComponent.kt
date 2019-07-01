package com.bilibili.lingxiao.dagger

import com.bilibili.lingxiao.home.MainActivity
import com.bilibili.lingxiao.home.region.ui.RegionFragment
import com.bilibili.lingxiao.home.live.ui.LiveFragment
import com.bilibili.lingxiao.home.mikan.ui.MikanFragment
import com.bilibili.lingxiao.home.recommend.ui.RecommendFragment
import com.bilibili.lingxiao.dagger.scope.PerUi
import com.bilibili.lingxiao.home.dynamic.DynamicFragment
import com.bilibili.lingxiao.home.find.ui.FindFragment
import com.bilibili.lingxiao.home.live.ui.play.FansFragment
import com.bilibili.lingxiao.home.live.ui.play.FleetListFragment
import com.bilibili.lingxiao.home.live.ui.play.InteractFragment
import com.bilibili.lingxiao.home.live.ui.play.UpInfoFragment
import com.bilibili.lingxiao.home.live.ui.LivePlayActivity
import com.bilibili.lingxiao.play.ui.CommentDetailFragment
import com.bilibili.lingxiao.play.ui.CommentFragment
import com.bilibili.lingxiao.play.ui.IntroduceFragment
import com.bilibili.lingxiao.play.ui.PlayActivity
import dagger.Component

@Component(modules = [UiModule::class])
@PerUi
interface UiComponent {
    fun inject(liveFragment: LiveFragment)
    fun inject(recommendFragment: RecommendFragment)
    fun inject(hotFragment: RegionFragment)
    fun inject(mikanFragment: MikanFragment)
    fun inject(dynamicFragment: DynamicFragment)
    fun inject(findFragment: FindFragment)

    fun inject(introduceFragment: IntroduceFragment)
    fun inject(commentFragment: CommentFragment)
    fun inject(commentDetailFragment: CommentDetailFragment)

    fun inject(interactFragment: InteractFragment)
    fun inject(upInfoFragment: UpInfoFragment)
    fun inject(fansFragment: FansFragment)
    fun inject(fleetListFragment: FleetListFragment)


    fun inject(mainActivity: MainActivity)
    fun inject(playActivity: PlayActivity)
    fun inject(livePlayActivity: LivePlayActivity)
}