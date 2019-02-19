package com.bilibili.lingxiao.ijkplayer

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bilibili.lingxiao.ijkplayer.media.IRenderView
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.simple_player_view_player.*
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class PlayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        /** 普通播放 start **/
        var url = getIntent().getStringExtra("url");
        simple_view.setVideoURI(Uri.parse(url))
        simple_view.startPlay()
    }

    override fun onPause() {
        super.onPause()
        simple_view.onPause()
    }

    override fun onResume() {
        super.onResume()
        simple_view.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        simple_view.onDestory()
    }
}
