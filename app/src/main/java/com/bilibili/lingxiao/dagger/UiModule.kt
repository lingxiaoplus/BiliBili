package com.bilibili.lingxiao.dagger

import android.content.Context
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
import com.bilibili.lingxiao.play.ui.CommentDetailFragment
import com.bilibili.lingxiao.play.ui.CommentFragment
import com.bilibili.lingxiao.play.ui.IntroduceFragment
import dagger.Module
import dagger.Provides
@Module
class UiModule {
    @Provides
    @PerUi
    fun provideLiveFragment() : LiveFragment {
        return LiveFragment()
    }

    @Provides
    @PerUi
    fun provideRecommendFragment() : RecommendFragment {
        return RecommendFragment()
    }

    @Provides
    @PerUi
    fun provideHotFragment() : RegionFragment {
        return RegionFragment()
    }

    @Provides
    @PerUi
    fun provideMikanFragment() : MikanFragment {
        return MikanFragment()
    }

    @Provides
    @PerUi
    fun provideDynamicFragment() : DynamicFragment {
        return DynamicFragment()
    }

    @Provides
    @PerUi
    fun provideFindFragment() : FindFragment {
        return FindFragment()
    }

    @Provides
    @PerUi
    fun provideIntroduceFragment() : IntroduceFragment {
        return IntroduceFragment()
    }

    @Provides
    @PerUi
    fun provideCommentFragment() : CommentFragment {
        return CommentFragment()
    }

    @Provides
    @PerUi
    fun provideInteractFragment() : InteractFragment {
        return InteractFragment()
    }

    @Provides
    @PerUi
    fun provideUpInfoFragment() : UpInfoFragment {
        return UpInfoFragment()
    }

    @Provides
    @PerUi
    fun provideFansFragment() : FansFragment {
        return FansFragment()
    }

    @Provides
    @PerUi
    fun provideFleetListFragment() : FleetListFragment {
        return FleetListFragment()
    }

    @Provides
    @PerUi
    fun provideCommentDetailFragment() : CommentDetailFragment {
        return CommentDetailFragment()
    }

    private lateinit var context: Context
    fun UiModule(context: Context) {
        this.context = context
    }

    @Provides
    @PerUi
    fun provideContext() : Context {
        return context
    }
}