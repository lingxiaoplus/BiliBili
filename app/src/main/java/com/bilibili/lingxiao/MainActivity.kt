package com.bilibili.lingxiao


import android.Manifest
import android.support.design.internal.NavigationMenuView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.bilibili.lingxiao.home.category.CategoryFragment
import com.bilibili.lingxiao.home.live.LiveFragment
import com.bilibili.lingxiao.home.mikan.MikanFragment
import com.bilibili.lingxiao.home.recommend.ui.RecommendFragment
import com.bilibili.lingxiao.user.LoginActivity
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseActivity
import com.camera.lingxiao.common.app.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class MainActivity : BaseActivity() {

    var tabArray = arrayOf("直播","推荐","分区","追番")
    var drawerOpened = false
    private val mPermessions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)

    @Inject
    lateinit var liveFragment: LiveFragment
    @Inject
    lateinit var recommendFragment: RecommendFragment
    @Inject
    lateinit var hotFragment: CategoryFragment
    @Inject
    lateinit var mikanFragment: MikanFragment

    var fragmentList:ArrayList<BaseFragment> = arrayListOf()

    override val contentLayoutId: Int
        get() = R.layout.activity_main

    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }
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
        //supportActionBar?.setHomeButtonEnabled(true);
        //supportActionBar?.setDisplayHomeAsUpEnabled(true);

        supportActionBar?.setDisplayShowTitleEnabled(false);
        /*var drawerToggle = object : ActionBarDrawerToggle(this,main_drawer_layout,main_toolbar,R.string.open,R.string.close){
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                drawerOpened = true
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                drawerOpened = false
            }
        }*/
        //drawerToggle.syncState()//设置左箭头与Home图标的切换与侧滑同步
        //main_drawer_layout.addDrawerListener(drawerToggle)//设置侧滑监听
        image_drawer_home.setOnClickListener {
            if (drawerOpened){
                main_drawer_layout.closeDrawer(Gravity.START)
            }else{
                main_drawer_layout.openDrawer(Gravity.START)
            }
        }
        image_download.setOnClickListener {  }
        image_game.setOnClickListener {  }
        image_search.setOnClickListener {  }
        for (name in tabArray){
            main_tabLayout.addTab(main_tabLayout.newTab().setText(name))
        }
        var navigationView = main_navigation.inflateHeaderView(R.layout.nav_header)
        var header_view = navigationView.findViewById<View>(R.id.nav_header)
        header_view.setOnClickListener({
            StartActivity(LoginActivity::class.java,false)
        })
        //隐藏NavigationView右侧滚动条
        var navigationMenuView = main_navigation.getChildAt(0) as NavigationMenuView
        navigationMenuView.isVerticalScrollBarEnabled = false

        main_viewPager.adapter = MainPagerAdapter(supportFragmentManager)

        main_tabLayout.setupWithViewPager(main_viewPager)

        fragmentList.add(liveFragment)
        fragmentList.add(recommendFragment)
        fragmentList.add(hotFragment)
        fragmentList.add(mikanFragment)
        main_viewPager.currentItem = 1
        //设置viewpager缓存页面个数
        main_viewPager.setOffscreenPageLimit(4)
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
            return fragmentList.get(position)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabArray.get(position)
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            super.destroyItem(container, position, `object`)
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
