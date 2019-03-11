package com.bilibili.lingxiao.home

import android.support.v4.view.PagerAdapter
import android.view.View

class MainPagerAdapter: PagerAdapter(){
    override fun isViewFromObject(view: View, any: Any): Boolean {
        return true
    }

    override fun getCount(): Int {
        return 4
    }

}