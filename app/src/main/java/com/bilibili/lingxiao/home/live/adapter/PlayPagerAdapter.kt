package com.bilibili.lingxiao.home.live.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

class PlayPagerAdapter(fm: FragmentManager?,data:Array<String>,fragments:List<Fragment>) : FragmentPagerAdapter(fm) {
    private var tabArray:Array<String> = data
    private var fms:List<Fragment> = fragments
    override fun getCount(): Int {
        return tabArray.size
    }

    override fun getItem(position: Int): Fragment {
        return fms.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabArray.get(position)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //super.destroyItem(container, position, `object`)
        //不调用destroy，懒加载fragment
    }
}