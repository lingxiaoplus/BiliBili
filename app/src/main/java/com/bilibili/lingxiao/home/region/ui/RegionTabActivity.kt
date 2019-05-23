package com.bilibili.lingxiao.home.region.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.region.model.RegionData
import com.bilibili.lingxiao.home.region.model.RegionRecommendData
import com.camera.lingxiao.common.app.BaseActivity
import kotlinx.android.synthetic.main.activity_live_more.*
import kotlinx.android.synthetic.main.title_bar.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.NumberFormatException

class RegionTabActivity : BaseActivity() {
    private var tabList = arrayListOf<RegionData.Data.Children>()
    private lateinit var mAdapter: PagerAdapter
    override val contentLayoutId: Int
        get() = R.layout.activity_live_more

    override fun initWidget() {
        super.initWidget()
        setToolbarBack(title_bar)
        title_bar.title = intent.getStringExtra("title")
        mAdapter = PagerAdapter(supportFragmentManager)
        viewpager.setAdapter(mAdapter)
        viewpager.offscreenPageLimit = 1
        tablayout.setViewPager(viewpager)
    }

    override fun isRegisterEventBus(): Boolean {
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onGetRegionInfo(info :RegionData.Data){
        tabList.addAll(info.children)
        mAdapter.notifyDataSetChanged()
        tablayout.notifyDataSetChanged()
        EventBus.getDefault().removeStickyEvent(info)
    }

    @Throws(NumberFormatException::class)
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onGetRegionRecommendInfo(data :RegionRecommendData.Data){
        var regionData = RegionData.Data.Children("",
            "","","",0,data.param.toInt(),0)
        tabList.add(regionData)
        mAdapter.notifyDataSetChanged()
        tablayout.notifyDataSetChanged()
        tablayout.visibility = View.GONE
        EventBus.getDefault().removeStickyEvent(data)
    }

    inner class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getCount(): Int {
            return tabList.size
        }

        override fun getItem(position: Int): Fragment {
            val fragment = RegionDetailFragment()
            val bundle = Bundle()
            bundle.putInt("tid",tabList[position].tid)  //其他的分类暂时就不展示了
            fragment.setArguments(bundle)
            return fragment
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabList[position].name
        }
    }
}
