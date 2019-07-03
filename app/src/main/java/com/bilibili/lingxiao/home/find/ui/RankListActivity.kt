package com.bilibili.lingxiao.home.find.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.database.RegionTable
import com.bilibili.lingxiao.home.region.model.RegionData
import com.camera.lingxiao.common.app.BaseActivity
import com.raizlabs.android.dbflow.sql.language.SQLite
import kotlinx.android.synthetic.main.activity_rank_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class RankListActivity : BaseActivity() {
    lateinit var tabArray :Array<String>
    var tables:List<RegionData.Data> = arrayListOf()
    lateinit var pagerAdapter:PagerAdapter
    override val contentLayoutId: Int
        get() = R.layout.activity_rank_list

    override fun initWidget() {
        super.initWidget()
        setToolbarBack(tool_bar)
        val type = intent.getStringExtra("type")
        tool_bar.title = type
        if (type.equals(resources.getString(R.string.find_line_top_original))){
            tabArray = resources.getStringArray(R.array.origin_ranking_list)
            initTabView()
        }else{
            //tables = SQLite.select().from(RegionTable::class.java).queryList()

            /*tables?.let {
                tabArray = it.map {
                        regionTable ->  regionTable.name
                }.toTypedArray()
            }*/
        }

    }

    fun initTabView(){
        for (name in tabArray){
            tabLayout.addTab(tabLayout.newTab().setText(name))
        }
        pagerAdapter = PagerAdapter(supportFragmentManager)
        viewpager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewpager)
        //设置viewpager缓存页面个数
        viewpager.setOffscreenPageLimit(3)
    }

    override fun isRegisterEventBus(): Boolean {
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onGetRegion(list: List<RegionData.Data>){
        EventBus.getDefault().removeStickyEvent(list)
        tables = list.subList(1,list.size)
        tabArray = tables.map {
                region ->  region.name
        }.toTypedArray()
        initTabView()
    }
    inner class PagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return tabArray.size
        }

        override fun getItem(position: Int): Fragment {
            var bundle = Bundle()
            if (tables.isEmpty()){
                var type = "origin"
                if (position == 1){
                    type = "all"
                }else if (position == 2){
                    type = "bangumi"
                }
                bundle.putString("type",type)
            }else{
                tables.let {
                    if (it.size > position)
                        bundle.putInt("rid",it[position].tid)
                    else
                        bundle.putInt("rid",0)
                }
            }

            var fragment = RankListFragment()
            fragment.arguments = bundle
            return fragment
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabArray.get(position)
        }
    }
}
