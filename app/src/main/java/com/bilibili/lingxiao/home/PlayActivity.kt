package com.bilibili.lingxiao.home

import android.content.res.Configuration
import android.view.WindowManager
import com.bilibili.lingxiao.R
import com.camera.lingxiao.common.app.BaseActivity
import com.camera.lingxiao.common.utills.LogUtils
import com.github.zackratos.ultimatebar.UltimateBar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_play.*
import java.net.URLDecoder

class PlayActivity : BaseActivity() {
    var tabArray = arrayOf("简介","评论")
    override val contentLayoutId: Int
        get() = R.layout.activity_play

    override fun initWidget() {
        super.initWidget()
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        var uri = intent.data
        //uri.getQueryParameter("player_width")
        //uri.getQueryParameter("player_height")
        //uri.getQueryParameter("player_rotate")
        var player_preload = URLDecoder.decode(uri.getQueryParameter("player_preload"),"UTF-8")
        var videoData = Gson().fromJson(player_preload,VideoData::class.java)
        LogUtils.d("需要播放的video信息：" + videoData.url)

        play_view
            .setLive(true)
            .setVideoUrl(videoData.url)
            .startPlay()

        for (name in tabArray){
            skin_tabLayout.addTab(skin_tabLayout.newTab().setText(name))
        }
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
        play_view.onConfigurationChang(newConfig)
    }
    override fun onResume() {
        super.onResume()
        play_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        play_view.onPause()
    }
    override fun onDestroy() {
        super.onDestroy()
        play_view.onDestory()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        play_view.onBackPressed()
    }
}
