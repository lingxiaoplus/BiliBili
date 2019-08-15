package com.bilibili.lingxiao.home


import android.Manifest
import android.net.Uri
import android.support.design.internal.NavigationMenuView
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bilibili.lingxiao.GlobalProperties
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.dynamic.DynamicFragment
import com.bilibili.lingxiao.home.find.ui.FindFragment
import com.bilibili.lingxiao.home.live.adapter.PlayPagerAdapter
import com.bilibili.lingxiao.home.region.ui.RegionFragment
import com.bilibili.lingxiao.home.live.ui.LiveFragment
import com.bilibili.lingxiao.home.mikan.ui.MikanFragment
import com.bilibili.lingxiao.home.navigation.SettingActivity
import com.bilibili.lingxiao.home.navigation.ThemeActivity
import com.bilibili.lingxiao.home.recommend.ui.RecommendFragment
import com.bilibili.lingxiao.user.LoginActivity
import com.bilibili.lingxiao.utils.ToastUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseActivity
import com.camera.lingxiao.common.app.BaseFragment
import com.camera.lingxiao.common.rxbus.SkinChangedEvent
import com.camera.lingxiao.common.utills.PopwindowUtil
import com.github.zackratos.ultimatebar.UltimateBar
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject
import com.camera.lingxiao.common.app.ActivityController
import com.camera.lingxiao.common.utills.SpUtils
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ImageDecodeOptionsBuilder
import com.google.gson.Gson
import com.hiczp.bilibili.api.app.model.MyInfo


class MainActivity : BaseActivity() {

    private val tabArray by lazy {
     resources.getStringArray(R.array.main_tab)
    }
    private var drawerOpened = false
    private val mPermessions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE)

    @Inject
    lateinit var liveFragment: LiveFragment
    @Inject
    lateinit var recommendFragment: RecommendFragment
    @Inject
    lateinit var categoryFragment: RegionFragment
    @Inject
    lateinit var mikanFragment: MikanFragment
    @Inject
    lateinit var dynamicFragment: DynamicFragment
    @Inject
    lateinit var findFragment: FindFragment

    private var fragmentList:ArrayList<BaseFragment> = arrayListOf()

    override val contentLayoutId: Int
        get() = R.layout.activity_main

    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }

    override fun isRegisterEventBus(): Boolean {
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onSkinChanged(event : SkinChangedEvent){
        UltimateBar.newColorBuilder()
            .statusColor(event.color)   // 状态栏颜色
            .applyNav(true)             // 是否应用到导航栏
            .navColor(event.color)         // 导航栏颜色
            .build(this)
            .apply()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLogined(user: MyInfo){
        changeLoginedState(user)
    }

    override fun initWidget() {
        super.initWidget()
        //权限检测
        if (!EasyPermissions.hasPermissions(this, *mPermessions)){
            //没有权限就申请
            EasyPermissions.requestPermissions(this, "申请权限",
                100, *mPermessions)
        }
        setSupportActionBar(main_toolbar)
        //设置返回键可用
        //supportActionBar?.setHomeButtonEnabled(true);
        //supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowTitleEnabled(false)
        initNavigationView()
        initTabLayout()
        image_header.setOnClickListener {
            StartActivity(LoginActivity::class.java,false)
        }
        image_download.setOnClickListener {  }
        image_game.setOnClickListener {  }
        image_search.setOnClickListener {
            val popwindowUtil = PopwindowUtil.PopupWindowBuilder(this@MainActivity)
                .setView(R.layout.fragment_dialog_search)
                .size(ViewGroup.LayoutParams.MATCH_PARENT.toFloat(),ViewGroup.LayoutParams.WRAP_CONTENT.toFloat())
                .setFocusable(true)
                .setTouchable(true)
                .setOutsideTouchable(true)
                .create()
            popwindowUtil.showAtLocation(it,0,-it.height,Gravity.TOP,0.5f)
            popwindowUtil.getView<ImageView>(R.id.image_exit)!!.setOnClickListener {
                popwindowUtil.dissmiss()
            }
        }
        val info = SpUtils.getString(this, GlobalProperties.USER_INFO, "")
        if (info.isNotEmpty()){
            val userInfo = Gson().fromJson(info,MyInfo::class.java)
            changeLoginedState(userInfo)
        }
    }

    private lateinit var navigationView:View
    private fun initNavigationView() {
        val drawerToggle = object : ActionBarDrawerToggle(this,main_drawer_layout,
            main_toolbar, R.string.open, R.string.close
        ){
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                drawerOpened = true
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                drawerOpened = false
            }
        }
        main_drawer_layout.addDrawerListener(drawerToggle)//设置侧滑监听
        //drawerToggle.syncState()//设置左箭头与Home图标的切换与侧滑同步
        image_drawer_home.setOnClickListener {
            if (drawerOpened){
                main_drawer_layout.closeDrawer(Gravity.START)
            }else{
                main_drawer_layout.openDrawer(Gravity.START)
            }
        }
        navigationView = main_navigation.inflateHeaderView(R.layout.nav_header)
        val headerView = navigationView.findViewById<SimpleDraweeView>(R.id.nav_header)
        headerView.setOnClickListener{
            StartActivity(LoginActivity::class.java,false)
        }
        findViewById<LinearLayout>(R.id.ll_nav_setting)
            .setOnClickListener {
                StartActivity(SettingActivity::class.java,false)
            }
        findViewById<LinearLayout>(R.id.ll_nav_theme)
            .setOnClickListener {
                StartActivity(ThemeActivity::class.java,false)
            }
        //隐藏NavigationView右侧滚动条
        val navigationMenuView = main_navigation.getChildAt(0) as NavigationMenuView
        navigationMenuView.isVerticalScrollBarEnabled = false
    }

    private fun changeLoginedState(user:MyInfo){
        val name = navigationView.findViewById<TextView>(R.id.text_name)
        val dynamicNum = navigationView.findViewById<TextView>(R.id.dynamic_num)
        val followedNum = navigationView.findViewById<TextView>(R.id.followed_num)
        val followingNum = navigationView.findViewById<TextView>(R.id.following_num)
        val headerView = navigationView.findViewById<SimpleDraweeView>(R.id.nav_header)

        headerView.hierarchy.actualImageScaleType = ScalingUtils.ScaleType.CENTER_CROP
        val imageDecode = ImageDecodeOptionsBuilder().setForceStaticImage(true).build()

        user.data.let {
            headerView.setImageURI(Uri.parse(it.face))
            name.text = it.name
            dynamicNum.text = "0"
            followedNum.text = "0"
            followingNum.text = "0"
        }
        image_header.setImageURI(Uri.parse(user.data.face))
        username.text = user.data.name
    }

    private fun initTabLayout() {
        for (name in tabArray){
            main_tabLayout.addTab(main_tabLayout.newTab().setText(name))
        }
        fragmentList.add(liveFragment)
        fragmentList.add(recommendFragment)
        fragmentList.add(categoryFragment)
        fragmentList.add(mikanFragment)
        fragmentList.add(dynamicFragment)
        fragmentList.add(findFragment)
        main_viewPager.adapter =
            PlayPagerAdapter(supportFragmentManager, tabArray, fragmentList)
        main_tabLayout.setupWithViewPager(main_viewPager)
        main_viewPager.currentItem = 1
        //设置viewpager缓存页面个数
        main_viewPager.offscreenPageLimit = 4
    }

    private var preTime: Long = 0
    override fun onBackPressed() {
        if (drawerOpened){
            main_drawer_layout.closeDrawers()
        }else{
            val nowTime = System.currentTimeMillis()
            if (nowTime - preTime > 2000) {
                ToastUtil.show("再按一次退出")
                preTime = nowTime
            } else {
                ActivityController.finishAll()
            }
            //super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentList.clear()
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
