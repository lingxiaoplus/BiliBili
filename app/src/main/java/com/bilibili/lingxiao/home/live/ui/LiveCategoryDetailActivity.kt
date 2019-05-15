package com.bilibili.lingxiao.home.live.ui

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.model.LiveTabData
import com.bilibili.lingxiao.home.live.presenter.LiveTabPresenter
import com.bilibili.lingxiao.home.live.view.LiveTabView
import com.bilibili.lingxiao.utils.ToastUtil
import com.camera.lingxiao.common.app.BaseActivity
import kotlinx.android.synthetic.main.fragment_live_category_detail.*
import kotlinx.android.synthetic.main.title_bar.*

class LiveCategoryDetailActivity :BaseActivity(),LiveTabView{

    private var presenter = LiveTabPresenter(this,this)
    private var tabList = arrayListOf<LiveTabData.Tab>()
    private lateinit var mAdapter:TabPagerAdapter
    override val contentLayoutId: Int
        get() = R.layout.fragment_live_category_detail

    override fun initWidget() {
        super.initWidget()
        setToolbarBack(title_bar)
        mAdapter = TabPagerAdapter()
        viewpager.setAdapter(mAdapter)
        tablayout.setViewPager(viewpager)
        presenter.getTabList(1)
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

    inner class TabPagerAdapter :PagerAdapter(){
        override fun isViewFromObject(p0: View, p1: Any): Boolean {
            return p0 == p1
        }

        override fun getCount(): Int {
            return tabList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabList[position].name
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var text = TextView(this@LiveCategoryDetailActivity)
            text.width = ViewGroup.LayoutParams.MATCH_PARENT
            text.setText(tabList[position].name)
            container.addView(text)
            return text
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            //super.destroyItem(container, position, `object`)
            container.removeView(`object` as View?)
        }
    }
}