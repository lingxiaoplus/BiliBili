package com.bilibili.lingxiao.home.find.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.bilibili.lingxiao.R
import com.camera.lingxiao.common.app.BaseActivity
import kotlinx.android.synthetic.main.activity_rank_list.*

class RankListActivity : BaseActivity() {
    val tabArray by lazy {
        resources.getStringArray(R.array.origin_ranking_list)
    }
    override val contentLayoutId: Int
        get() = R.layout.activity_rank_list

    override fun initWidget() {
        super.initWidget()
        setToolbarBack(tool_bar)
        tool_bar.title = resources.getString(R.string.find_line_top_all)
        for (name in tabArray){
            tabLayout.addTab(tabLayout.newTab().setText(name))
        }
        viewpager.adapter =
            PagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewpager)
        //设置viewpager缓存页面个数
        viewpager.setOffscreenPageLimit(3)
    }

    inner class PagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return tabArray.size
        }

        override fun getItem(position: Int): Fragment {
            var bundle = Bundle()
            var type = "origin"
            if (position == 1){
                type = "all"
            }else if (position == 2){
                type = "bangumi"
            }
            bundle.putString("type",type)
            var fragment = RankListFragment()
            fragment.arguments = bundle
            return fragment
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabArray.get(position)
        }
    }
}
