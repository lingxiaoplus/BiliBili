package com.bilibili.lingxiao.home.live.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.widget.RippleAnimation
import com.camera.lingxiao.common.app.BaseActivity
import com.camera.lingxiao.common.rxbus.SkinChangedEvent
import com.github.zackratos.ultimatebar.UltimateBar
import kotlinx.android.synthetic.main.activity_live_all.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class LiveAllActivity : BaseActivity() {
    val tabArray by lazy {
        resources.getStringArray(R.array.live_all)
    }
    override val contentLayoutId: Int
        get() = R.layout.activity_live_all
    override fun isRegisterEventBus(): Boolean {
        return true
    }
    val searchDialogFragment = SearchDialogFragment()

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onSkinChanged(event : SkinChangedEvent){
        UltimateBar.newColorBuilder()
            .statusColor(event.color)   // 状态栏颜色
            .applyNav(true)             // 是否应用到导航栏
            .navColor(event.color)         // 导航栏颜色
            .build(this)
            .apply()

    }
    override fun initWidget() {
        super.initWidget()
        setToolbarBack(toolbar)
        toolbar.title = resources.getString(R.string.live_all_title)
        for (name in tabArray){
            tabLayout.addTab(tabLayout.newTab().setText(name))
        }
        viewPager.adapter = PagerAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = tabArray.size
        tabLayout.setupWithViewPager(viewPager)
        image_search.setOnClickListener {
            searchDialogFragment.show(supportFragmentManager,LiveAllActivity::class.java.simpleName)
        }
    }

    inner class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return tabArray.size
        }

        override fun getItem(position: Int): Fragment {
            val fragment = LiveAllFragment()
            val bundle = Bundle()
            if (position == 0){
                bundle.putString("type", "online")
            }else{
                bundle.putString("type", "live_time")
            }
            fragment.setArguments(bundle)
            return fragment
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabArray[position]
        }
    }

}
