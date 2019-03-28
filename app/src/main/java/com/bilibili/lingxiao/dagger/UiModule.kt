package com.bilibili.lingxiao.dagger

import android.content.Context
import com.bilibili.lingxiao.home.hot.HotFragment
import com.bilibili.lingxiao.home.live.LiveFragment
import com.bilibili.lingxiao.home.mikan.MikanFragment
import com.bilibili.lingxiao.home.recommend.ui.RecommendFragment
import com.bilibili.lingxiao.dagger.scope.PerUi
import dagger.Module
import dagger.Provides
@Module
class UiModule {
    @Provides
    @PerUi
    fun provideLiveFragment() :LiveFragment{
        return LiveFragment()
    }

    @Provides
    @PerUi
    fun provideRecommendFragment() : RecommendFragment {
        return RecommendFragment()
    }

    @Provides
    @PerUi
    fun provideHotFragment() : HotFragment {
        return HotFragment()
    }

    @Provides
    @PerUi
    fun provideMikanFragment() : MikanFragment {
        return MikanFragment()
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