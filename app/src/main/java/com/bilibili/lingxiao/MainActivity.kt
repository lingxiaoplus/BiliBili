package com.bilibili.lingxiao


import android.Manifest
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import com.bilibili.lingxiao.home.FragmentFactory
import com.bilibili.lingxiao.user.LoginActivity
import com.camera.lingxiao.common.app.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : BaseActivity() {
    var tabArray = arrayOf("直播","推荐","热门","追番")
    var drawerOpened = false
    private val mPermessions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    override val contentLayoutId: Int
        get() = R.layout.activity_main

    override fun initWidget() {
        super.initWidget()
        //权限检测
        if (!EasyPermissions.hasPermissions(this, *mPermessions)){
            //没有权限就申请
            EasyPermissions.requestPermissions(this, "申请权限",
                100, *mPermessions);
        }
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

        main_viewPager.adapter = MainPagerAdapter(supportFragmentManager)
        main_tabLayout.setupWithViewPager(main_viewPager)
    }

    override fun onBackPressed() {
        if (drawerOpened){
            main_drawer_layout.closeDrawers()
        }else{
            super.onBackPressed()
        }
    }

    inner class MainPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return tabArray.size
        }

        override fun getItem(position: Int): Fragment {
            return FragmentFactory.createFragment(position)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabArray.get(position)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }
}
