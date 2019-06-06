package com.bilibili.lingxiao.home.live.ui

import android.content.res.Configuration
import android.net.Uri
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.adapter.PlayPagerAdapter
import com.bilibili.lingxiao.home.live.DanMaKuTool
import com.bilibili.lingxiao.home.live.model.*
import com.bilibili.lingxiao.home.live.ui.play.FansFragment
import com.bilibili.lingxiao.home.live.presenter.LivePlayPresenter
import com.bilibili.lingxiao.home.live.ui.play.FleetListFragment
import com.bilibili.lingxiao.home.live.ui.play.InteractFragment
import com.bilibili.lingxiao.home.live.ui.play.UpInfoFragment
import com.bilibili.lingxiao.home.live.view.LivePlayView
import com.bilibili.lingxiao.utils.ToastUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseActivity
import com.camera.lingxiao.common.app.BaseFragment
import com.camera.lingxiao.common.utills.LogUtils
import com.camera.lingxiao.common.utills.PopwindowUtil
import com.facebook.drawee.view.SimpleDraweeView
import com.github.zackratos.ultimatebar.UltimateBar
import kotlinx.android.synthetic.main.activity_live_play.*
import org.greenrobot.eventbus.EventBus
import java.util.ArrayList
import javax.inject.Inject
import kotlin.properties.Delegates

class LivePlayActivity : BaseActivity() , LivePlayView {

    @Inject
    lateinit var interactFragment: InteractFragment
    @Inject
    lateinit var upInfoFragment: UpInfoFragment
    @Inject
    lateinit var fansFragment: FansFragment
    @Inject
    lateinit var fleetListFragment: FleetListFragment
    var fragmentList: ArrayList<BaseFragment> = arrayListOf()
    var  tabArray: Array<String> by Delegates.notNull()

    public var livePresenter = LivePlayPresenter(this, this)
    private var ruid = 0
    override val contentLayoutId: Int
        get() = R.layout.activity_live_play

    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }
    override fun initWidget() {
        super.initWidget()
        //屏幕常亮

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        tabArray = resources.getStringArray(R.array.live_tab)
        for (name in tabArray){
            live_tablayout.addTab(live_tablayout.newTab().setText(name))
        }

        val play_url = intent.getStringExtra("play_url")
        live_play
            .setLive(true)
            .setVideoUrl(play_url)
            .startPlay()
        val roomId = intent.getIntExtra("room_id",0)
        livePresenter.getUpInfo(roomId)
        DanMaKuTool.joinRoom(roomId)

        fragmentList.add(interactFragment)
        fragmentList.add(upInfoFragment)
        fragmentList.add(fansFragment)
        fragmentList.add(fleetListFragment)
        live_viewpager.adapter =
            PlayPagerAdapter(supportFragmentManager, tabArray, fragmentList)
        live_tablayout.setupWithViewPager(live_viewpager)

    }

    fun getUserInfo(uid:Int){
        showProgressDialog("请稍后")
        livePresenter.getUserInfo(ruid,uid)
    }

    override fun initBefore() {
        UltimateBar.newTransparentBuilder()
            .statusColor(resources.getColor(R.color.colorTrans))        // 状态栏颜色
            .statusAlpha(100)               // 状态栏透明度
            .applyNav(true)                // 是否应用到导航栏
            .build(this)
            .apply();
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        live_play.onConfigurationChang(newConfig)
    }
    override fun onResume() {
        super.onResume()
        live_play.onResume()
    }

    override fun onRestart() {
        super.onRestart()
        live_play.onRestart()
    }
    override fun onPause() {
        super.onPause()
        live_play.onPause()
    }
    override fun onDestroy() {
        super.onDestroy()
        live_play.onDestory()
        DanMaKuTool.exitRoom()
        fragmentList.clear()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        live_play.onBackPressed()
    }


    override fun showDialog() {

    }

    override fun onGetUpInfo(liveUpData: LiveUpData) {
        ruid = liveUpData.roomInfo.uid
        EventBus.getDefault().postSticky(liveUpData)
        LogUtils.d("获取到直播up主信息-->${liveUpData}")
    }
    override fun onGetFleetList(fleetListData: FleetListData) {

    }

    override fun onGetUpVideoList(list: List<UpInfoData>) {

    }

    override fun onGetUpChatHistory(list: List<LiveChatData.Room>) {

    }

    override fun onGetUserInfo(liveUserData: LiveUserData) {
        cancleProgressDialog()
        val popwindowUtil = PopwindowUtil.PopupWindowBuilder(this@LivePlayActivity)
            .setView(R.layout.pop_user_info)
            .setElevation(10f)
            .setFocusable(true)
            .setAnimationStyle(R.style.contextMenuAnim)
            .setTouchable(true)
            .setOutsideTouchable(true)
            .create()
        popwindowUtil.showAtLocation(live_viewpager, 0,0,Gravity.CENTER)
        popwindowUtil.getView<ImageView>(R.id.image_close)!!.setOnClickListener {
            popwindowUtil.dissmiss()
        }
        popwindowUtil.getView<SimpleDraweeView>(R.id.image_header)!!.setImageURI(Uri.parse(liveUserData.face))
        popwindowUtil.getView<TextView>(R.id.username)!!.text = liveUserData.uname
        popwindowUtil.getView<TextView>(R.id.user_level)!!.text = "UL " + liveUserData.userLevel
        popwindowUtil.getView<TextView>(R.id.follow_num)!!.text = "关注："+ liveUserData.attentionNum + "    粉丝：" + liveUserData.followNum
        popwindowUtil.getView<TextView>(R.id.text_user_space)!!.setOnClickListener {  }
        popwindowUtil.getView<TextView>(R.id.text_user_follow)!!.setOnClickListener {  }
    }

    override fun diamissDialog() {

    }

    override fun showToast(text: String?) {
        ToastUtil.show(text)
    }
}
