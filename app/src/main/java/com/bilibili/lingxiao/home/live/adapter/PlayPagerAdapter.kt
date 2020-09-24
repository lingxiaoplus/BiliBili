package com.bilibili.lingxiao.home.live.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter

class PlayPagerAdapter(fm: FragmentManager, data:Array<String>, fragments:List<Fragment>) : FragmentPagerAdapter(fm) {
    private var tabArray:Array<String> = data
    private var fms:List<androidx.fragment.app.Fragment> = fragments
    override fun getCount(): Int {
        return tabArray.size
    }

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
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