package com.bilibili.lingxiao.dagger

import android.content.Context
import com.bilibili.lingxiao.home.category.CategoryFragment
import com.bilibili.lingxiao.home.live.LiveFragment
import com.bilibili.lingxiao.home.mikan.MikanFragment
import com.bilibili.lingxiao.home.recommend.ui.RecommendFragment
import com.bilibili.lingxiao.dagger.scope.PerUi
import com.bilibili.lingxiao.play.CommentFragment
import com.bilibili.lingxiao.play.IntroduceFragment
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
    fun provideHotFragment() : CategoryFragment {
        return CategoryFragment()
    }

    @Provides
    @PerUi
    fun provideMikanFragment() : MikanFragment {
        return MikanFragment()
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