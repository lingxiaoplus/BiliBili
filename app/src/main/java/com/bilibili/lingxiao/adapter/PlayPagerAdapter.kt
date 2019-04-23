package com.bilibili.lingxiao.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

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
}