package com.bilibili.lingxiao.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MainPagerAdapter : FragmentPagerAdapter {
    constructor(fm: FragmentManager?) : super(fm){

    }
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        return FragmentFactory.createFragment(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return super.getPageTitle(position)
    }
}