package com.bilibili.lingxiao


import android.support.v4.view.PagerAdapter
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import com.bilibili.lingxiao.user.LoginActivity
import com.camera.lingxiao.common.app.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    var tabArray = arrayOf("直播","推荐","热门","追番")
    var drawerOpened = false
    override val contentLayoutId: Int
        get() = R.layout.activity_main

    override fun initWidget() {
        super.initWidget()
        setSupportActionBar(main_toolbar)
        //设置返回键可用
        supportActionBar?.setHomeButtonEnabled(true);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        var drawerToggle = object : ActionBarDrawerToggle(this,main_drawer_layout,main_toolbar,R.string.open,R.string.close){
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                drawerOpened = true
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                drawerOpened = false
            }
        }

        drawerToggle.syncState()//设置左箭头与Home图标的切换与侧滑同步
        main_drawer_layout.addDrawerListener(drawerToggle)//设置侧滑监听
        for (name in tabArray){
            main_tabLayout.addTab(main_tabLayout.newTab().setText(name))
        }
        var navigationView = main_navigation.inflateHeaderView(R.layout.nav_header)
        var header_view = navigationView.findViewById<View>(R.id.nav_header)
        header_view.setOnClickListener({
            StartActivity(LoginActivity::class.java,false)
        })


    }

    override fun onBackPressed() {
        if (drawerOpened){
            main_drawer_layout.closeDrawers()
        }else{
            super.onBackPressed()
        }
    }

    class MainPagerAdapter :PagerAdapter(){
        override fun isViewFromObject(view: View, any: Any): Boolean {

            return true
        }

        override fun getCount(): Int {
            return 4
        }

    }
}
