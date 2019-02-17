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
        video_view.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
        video_view.setVideoURI(Uri.parse(url));
        video_view.start();

    }

    override fun onStop() {
        super.onStop()
        video_view.stopBackgroundPlay()
        video_view.release(true)
    }
}
