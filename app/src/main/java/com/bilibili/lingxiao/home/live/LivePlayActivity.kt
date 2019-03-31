package com.bilibili.lingxiao.home.live

import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.view.WindowManager
import com.bilibili.lingxiao.R
import com.camera.lingxiao.common.app.BaseActivity
import com.camera.lingxiao.common.utills.LogUtils
import com.github.zackratos.ultimatebar.UltimateBar
import kotlinx.android.synthetic.main.activity_live_play.*
import java.net.URLDecoder
import kotlin.properties.Delegates

class LivePlayActivity : BaseActivity() {
    var  tabArray: Array<String> by Delegates.notNull()
    override val contentLayoutId: Int
        get() = R.layout.activity_live_play

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

    override fun onPause() {
        super.onPause()
        live_play.onPause()
    }
    override fun onDestroy() {
        super.onDestroy()
        live_play.onDestory()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        live_play.onBackPressed()
    }

}
