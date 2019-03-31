package com.bilibili.lingxiao.home

import android.util.SparseArray
import com.bilibili.lingxiao.home.hot.HotFragment
import com.bilibili.lingxiao.home.live.LiveFragment
import com.bilibili.lingxiao.home.mikan.MikanFragment
import com.bilibili.lingxiao.home.recommend.ui.RecommendFragment
import com.camera.lingxiao.common.app.BaseFragment

object FragmentFactory{
    val TYPE_LIVE = 0
    val TYPE_RECOMMEND = 1
    val TYPE_HOT = 2
    val TYPE_MIKAN = 3
    private val mFragmentMap = SparseArray<BaseFragment>()
    fun createFragment(type :Int) :BaseFragment{
        var fragment = mFragmentMap.get(type)
        if (fragment == null){
            when(type){
                TYPE_LIVE-> fragment = LiveFragment()
                TYPE_RECOMMEND-> fragment = RecommendFragment()
                TYPE_HOT-> fragment = HotFragment()
                TYPE_MIKAN-> fragment = MikanFragment()
                else-> fragment = LiveFragment()
            }
        }
        return fragment
    }
}