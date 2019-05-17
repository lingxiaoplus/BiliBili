package com.bilibili.lingxiao.home.live.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.model.LiveTabData
import com.bilibili.lingxiao.home.live.presenter.LiveTabPresenter
import com.bilibili.lingxiao.home.live.view.LiveTabView
import com.bilibili.lingxiao.utils.ToastUtil
import com.camera.lingxiao.common.app.BaseActivity
import kotlinx.android.synthetic.main.activity_live_more.*
import kotlinx.android.synthetic.main.title_bar.*

class LiveMoreActivity :BaseActivity(),LiveTabView{

    private var presenter = LiveTabPresenter(this,this)
    private var tabList = arrayListOf<LiveTabData.Tab>()
    private lateinit var mAdapter:PagerAdapter
    override val contentLayoutId: Int
        get() = R.layout.activity_live_more

    override fun initWidget() {
        super.initWidget()
        var intent = getIntent()
        var parentId = intent.getIntExtra("parentId",1)
        var parentName = intent.getStringExtra("parentName")
        setToolbarBack(title_bar)
        title_bar.title = parentName
        mAdapter = PagerAdapter(supportFragmentManager)
        viewpager.setAdapter(mAdapter)
        tablayout.setViewPager(viewpager)
        presenter.getTabList(parentId)
    }

    override fun onGetTabList(tabs: List<LiveTabData.Tab>) {
        tabList.addAll(tabs)
        mAdapter.notifyDataSetChanged()
        tablayout.notifyDataSetChanged()
    }

    override fun showDialog() {
    }

    override fun diamissDialog() {
    }

    override fun showToast(text: String?) {
        ToastUtil.show(text)
    }

    inner class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return tabList.size
        }

        override fun getItem(position: Int): Fragment {
            val fragment = LiveAllFragment()
            val bundle = Bundle()
            bundle.putString("type","online")  //其他的分类暂时就不展示了
            tabList[position].id?.toIntOrNull()?.let { bundle.putInt("areaId", it) }
            tabList[position].parentId?.toIntOrNull()?.let { bundle.putInt("parentAreaId", it) }
            fragment.setArguments(bundle)
            return fragment
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabList[position].name
        }
    }
}