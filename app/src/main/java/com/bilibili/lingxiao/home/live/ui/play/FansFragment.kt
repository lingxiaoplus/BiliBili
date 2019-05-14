package com.bilibili.lingxiao.home.live.ui.play

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.model.LiveUpData
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseFragment
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.fragment_fans.*
import kotlinx.android.synthetic.main.fragment_fans.view.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class FansFragment :BaseFragment(){
    private var roomId = 0
    private var uid = 0
    private val tabArray by lazy {
        resources.getStringArray(R.array.live_fans)
    }
    override val contentLayoutId: Int
        get() = R.layout.fragment_fans
    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }

    override fun onFirstVisiblity() {
        super.onFirstVisiblity()
        viewpager.adapter = PagerAdapter(childFragmentManager)
        viewpager.offscreenPageLimit = tabArray.size
        //tablayout和ViewPager的联动需要自己做
        //root.fans_tablayout.setViewPager(root.viewpager)
        fans_tablayout.setTabData(tabArray)
    }
    override fun initWidget(root: View) {
        super.initWidget(root)

        root.viewpager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                root.fans_tablayout.currentTab = position
            }

            override fun onPageScrollStateChanged(position: Int) {

            }
        })
        root.fans_tablayout.setOnTabSelectListener(object :OnTabSelectListener{
            override fun onTabSelect(position: Int) {
                root.viewpager.currentItem = position
            }
            override fun onTabReselect(position: Int) {

            }
        })
    }
    override fun isRegisterEventBus(): Boolean {
        return true
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onGetUpInfo(liveUpData: LiveUpData){
        roomId = liveUpData.roomInfo.roomId
        uid = liveUpData.roomInfo.uid
    }

    inner class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return tabArray.size
        }

        override fun getItem(position: Int): Fragment {
            val fragment = FansDetailFragment()
            val bundle = Bundle()
            bundle.putInt("position", position)
            bundle.putInt("roomId", roomId)
            bundle.putInt("uid", uid)
            fragment.setArguments(bundle)
            return fragment
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabArray[position]
        }
    }
}