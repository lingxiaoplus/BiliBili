package com.bilibili.lingxiao.ijkplayer.widget

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.SeekBar
import com.bilibili.lingxiao.ijkplayer.PlayState
import com.bilibili.lingxiao.ijkplayer.R
import com.bilibili.lingxiao.ijkplayer.media.IRenderView
import com.bilibili.lingxiao.ijkplayer.media.PlayerManager
import kotlinx.android.synthetic.main.simple_player_controlbar.view.*
import kotlinx.android.synthetic.main.simple_player_view_player.view.*
import tv.danmaku.ijk.media.player.IMediaPlayer

class SimplePlayerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

    var isLive = false
    var mCurrentPosition = 0
    var mVideoState = PlayState.STATE_IDLE

    /**
     * 同步进度
     */
    private val MESSAGE_SHOW_PROGRESS = 1
    /**
     * 设置新位置
     */
    private val MESSAGE_SEEK_NEW_POSITION = 2
    /**
     * 隐藏提示的box
     */
    private val MESSAGE_HIDE_CENTER_BOX = 3
    /**
     * 重新播放
     */
    private val MESSAGE_RESTART_PLAY = 4
    /**
     * 播放的时候是否需要网络提示，默认显示网络提示，true为显示网络提示，false不显示网络提示
     */
    private var isGNetWork = true

    /**
     * 是否在拖动进度条中，默认为停止拖动，true为在拖动中，false为停止拖动
     */
    private var isDragging: Boolean = false
    companion object {
        val TAG = SimplePlayerView::class.java.simpleName
    }

    private val mHandler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when(msg?.what){
                MESSAGE_SHOW_PROGRESS -> {
                    if (!isDragging){
                        var position = updateProgress()
                        var msge = obtainMessage(MESSAGE_SHOW_PROGRESS)
                        sendMessageDelayed(msge,1000L-(position % 1000))
                    }

                }
                MESSAGE_RESTART_PLAY ->{
                    mVideoState = PlayState.STATE_ERROR
                    startPlay()
                    updatePauseOrPlay()
                }
                MESSAGE_SEEK_NEW_POSITION->{

                }
            }
        }
    }

    init {
        initView(context)
    }

    private fun initView(context: Context) {
        View.inflate(context, R.layout.simple_player_view_player, this)
        video_play.setOnClickListener{
            if (isLive){
                video_view.stopPlayback()
            }else{
                if (video_view.isPlaying){
                    pausePlay()
                    video_play.setImageResource(R.drawable.ic_img_pause)
                    play_icon.visibility = View.VISIBLE
                }else{
                    startPlay()
                    video_play.setImageResource(R.drawable.ic_img_play)
                    play_icon.visibility = View.INVISIBLE
                }
            }
        }
        video_seekBar.setOnSeekBarChangeListener(mVideoProgressListener)
        video_view.setOnInfoListener { mp, what, extra->
            statusChanged(what)
            return@setOnInfoListener true
        }
    }

    private fun statusChanged(what: Int) {
        this.mVideoState = what
        Log.i(TAG,"播放状态: "+mVideoState)
        when(mVideoState){
            PlayState.STATE_COMPLETED ->{
                Log.d(TAG,"播放结束")
                mCurrentPosition = 0
            }

            PlayState.STATE_PREPARING,PlayState.MEDIA_INFO_BUFFERING_START,PlayState.STATE_PREPARING->{
                Log.d(TAG,"视频缓冲")
                video_progress.visibility = View.VISIBLE
            }
            PlayState.MEDIA_INFO_VIDEO_RENDERING_START,
            PlayState.STATE_PREPARED,
            PlayState.MEDIA_INFO_BUFFERING_END ->{
                Log.d(TAG,"视频缓冲结束")
                mHandler.postDelayed({
                    video_progress.visibility = View.INVISIBLE
                    mHandler.sendEmptyMessage(MESSAGE_SHOW_PROGRESS)
                },500)
            }
            PlayState.MEDIA_INFO_VIDEO_INTERRUPT->{
                Log.d(TAG,"直播停止推流")
            }
            PlayState.STATE_ERROR,
            PlayState.MEDIA_INFO_UNKNOWN,
            PlayState.MEDIA_ERROR_IO,
            PlayState.MEDIA_ERROR_MALFORMED,
            PlayState.MEDIA_ERROR_UNSUPPORTED,
            PlayState.MEDIA_ERROR_TIMED_OUT,
            PlayState.MEDIA_ERROR_SERVER_DIED->{
                Log.d(TAG,"播放错误")
            }
        }
    }

    /**
     * 时长格式化显示
     */
    private fun generateTime(time: Int): String {
        val totalSeconds = time / 1000
        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60 % 60
        val hours = totalSeconds / 3600
        return if (hours > 0) String.format("%02d:%02d:%02d", hours, minutes, seconds) else String.format(
            "%02d:%02d",
            minutes,
            seconds
        )
    }

    /**
     * 更新播放状态
     */
    private fun updatePauseOrPlay(){
        if (video_view.isPlaying){
            video_play.setImageResource(R.drawable.ic_img_play)
            play_icon.visibility = View.INVISIBLE
        }else{
            video_play.setImageResource(R.drawable.ic_img_pause)
            play_icon.visibility = View.VISIBLE
        }
    }

    /**
     * 更新播放进度
     */
    private fun updateProgress(): Int{


        var position = video_view.currentPosition  //视频进度
        var duration = video_view.duration  //视频总长度
        var bufferPos = video_view.bufferPercentage //视频缓冲进度

        /*if (position == duration || mVideoState == PlayState.STATE_COMPLETED
            || mVideoState == PlayState.STATE_PAUSED){
            //视频播放暂停或完成
            updatePauseOrPlay()
        }*/

        video_currentTime.text = generateTime(position)
        video_endTime.text = generateTime(duration)

        video_seekBar.max = duration
        video_seekBar.progress = position
        video_seekBar.secondaryProgress = bufferPos * 10

        Log.d(TAG,"视频时长：" + duration + "播放进度：" + position)
        return position
    }

    fun setVideoURI(uri: Uri){
        video_view.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
        video_view.setVideoURI(uri)
    }

    fun startPlay(){
        if (isLive){
            video_view.seekTo(0)
        }else{

        }
        //video_view.seekTo(mCurrentPosition)
        video_view.start()
    }

    fun pausePlay(){
        mVideoState = PlayState.STATE_PAUSED
        getCurrentPosition()
        video_view.pause()
    }
    fun stopPlay(){
        video_view.stopPlayback()
        video_view.release(true)
    }

    /**
     * 获取当前播放位置
     */
    fun getCurrentPosition(): Int {
        if (!isLive) {
            mCurrentPosition = video_view.getCurrentPosition()
        } else {
            /**直播 */
            mCurrentPosition = -1
        }
        return mCurrentPosition
    }


    private var mVideoProgressListener = object: SeekBar.OnSeekBarChangeListener{
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            Log.i(TAG,"用户拖动改变："+fromUser)
            if (!fromUser){
                return
            }
            val duration = video_view.duration
            val position = (duration * progress * 1.0 / 1000).toInt()
            val time = generateTime(position)
            video_currentTime.text = time
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            //开始拖动
            isDragging = true
            mHandler.removeMessages(MESSAGE_SHOW_PROGRESS)
            Log.i(TAG,"用户开始拖动")
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            //停止拖动
            isDragging = false
            mHandler.removeMessages(MESSAGE_SHOW_PROGRESS)
            video_view.seekTo((video_view.duration * seekBar!!.progress * 0.1 /1000).toInt())
            mHandler.sendEmptyMessageDelayed(MESSAGE_SHOW_PROGRESS,1000)
            Log.i(TAG,"用户停止拖动")
        }
    }
}
