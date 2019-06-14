package com.bilibili.lingxiao.ijkplayer.widget

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.widget.RelativeLayout
import android.widget.SeekBar
import com.bilibili.lingxiao.ijkplayer.PlayState
import com.bilibili.lingxiao.ijkplayer.R
import com.bilibili.lingxiao.ijkplayer.media.IRenderView
import kotlinx.android.synthetic.main.simple_player_controlbar.view.*
import kotlinx.android.synthetic.main.simple_player_view_player.view.*
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.media.AudioManager
import tv.danmaku.ijk.media.player.pragma.DebugLog
import android.content.ContextWrapper
import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.bilibili.lingxiao.ijkplayer.NetworkUtil
import com.bilibili.lingxiao.ijkplayer.danmuku.BiliDanmuku
import com.camera.lingxiao.common.app.BaseFragment
import kotlinx.android.synthetic.main.simple_player_controlbar_fullscreen.view.*
import kotlinx.android.synthetic.main.simple_player_topbar.view.*
import okhttp3.*
import java.io.IOException
import java.lang.StringBuilder
import kotlin.properties.Delegates


class SimplePlayerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RelativeLayout(context, attrs, defStyleAttr),View.OnTouchListener,View.OnClickListener ,SeekBar.OnSeekBarChangeListener{

    private var mCurrentPosition = 0
    private var mVideoState = PlayState.STATE_IDLE
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
     * 是否在拖动进度条中，默认为停止拖动，true为在拖动中，false为停止拖动
     */
    private var isDragging: Boolean = false

    /**
     * 是否隐藏了状态栏
     */
    private var isHiddenBar = false

    /**
     * 滑动进度条得到的新位置，和当前播放位置是有区别的,newPosition =0也会调用设置的，故初始化值为-1
     */
    private var newPosition: Long = -1

    /**
     * 滑动进度条得到的当前亮度
     */
    private var brightness = -1f
    /**
     * 滑动进度条得到的当前音量
     */
    private var volume = -1
    private var mMaxVolume: Int = 0
    private var mAudioManager: AudioManager? = null

    /**
     * 是否是竖屏
     */
    var isPortrait = true

    /**
     * 记录播放器竖屏时的高度 延迟初始化
     */
    private val initHeight: Int by lazy { this@SimplePlayerView.height }

    /**
     * 播放器底部导航栏 在竖直时候的view
     */
    private val bottomBarHalf:View by lazy {
        View.inflate(context,R.layout.simple_player_controlbar,null)
    }

    /**
     * 播放器底部导航栏 在全屏时候的view
     */
    private val bottomBarFullScreen:View by lazy {
        View.inflate(context,R.layout.simple_player_controlbar_fullscreen,null)
    }

    /**
     * 是否显示网络改变提示
     */
    var isShowNetworkHint = true

    private var mVideoUrl: String by Delegates.notNull<String>()

    private var mActivity: Activity? = null

    private var mGestureDector:GestureDetector? = null
    private var screenWidthPixels: Int? = 0

    private var isLive: Boolean = false
        get() {
            return mVideoUrl.startsWith("rtmp://") ||
                    mVideoUrl.startsWith("rtsp://") ||
                    mVideoUrl.startsWith("http://") && mVideoUrl.endsWith(".m3u8") ||
                    mVideoUrl.startsWith("http://") && mVideoUrl.endsWith(".flv")
        }

    private val TAG = SimplePlayerView::class.java.simpleName
    private val mHandler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when(msg?.what){
                MESSAGE_SHOW_PROGRESS -> {
                    if (!isDragging){
                        var position = updateProgress()
                        var msge = obtainMessage(MESSAGE_SHOW_PROGRESS)
                        sendMessageDelayed(msge,1000L - (position % 1000))
                    }

                }
                MESSAGE_RESTART_PLAY ->{
                    mVideoState = PlayState.STATE_ERROR
                    startPlay()
                    updatePauseOrPlay()
                }
                MESSAGE_SEEK_NEW_POSITION->{
                    if (!isLive && newPosition >= 0) {
                        video_view.seekTo(newPosition.toInt())
                        newPosition = -1
                    }
                }
            }
        }
    }

    private var itemClickListener:OnPlayerItemClickListener? = null
    fun setPlayerItemClickListener(listener:OnPlayerItemClickListener){
        itemClickListener = listener
    }
    interface OnPlayerItemClickListener{
        fun onQuilityTextClick()
    }

    init {
        initView(context)
    }

    private fun initView(context: Context) {
        View.inflate(context, R.layout.simple_player_view_player, this)
        mActivity = getActivityFromContext(context)
        screenWidthPixels = mActivity?.getResources()?.getDisplayMetrics()?.widthPixels
        mAudioManager = mActivity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        mMaxVolume = mAudioManager?.getStreamMaxVolume(AudioManager.STREAM_MUSIC)!!
        if (isShowNetworkHint){
            video_progress.visibility = INVISIBLE
        }
        bottom_root.addView(bottomBarHalf)
        video_play.setOnClickListener(this)
        //video_play_full.setOnClickListener(this)
        play_icon.setOnClickListener(this)
        video_button_continue.setOnClickListener(this)
        video_fullscreen.setOnClickListener(this)
        video_finish.setOnClickListener(this)
        //video_seekBar.setOnSeekBarChangeListener(mVideoProgressListener)
        video_seekBar.setOnSeekBarChangeListener(this)
        video_view.setOnInfoListener { mp, what, extra->
            statusChanged(what)
            return@setOnInfoListener true
        }
        mGestureDector = GestureDetector(getContext(),object : PlayerGestureDetector(){})
        setClickable(true) //设置可点击
        setOnTouchListener(this)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        if (heightMode == MeasureSpec.AT_MOST){
            heightSize = Math.max(heightMeasureSpec,200)
        }
        setMeasuredDimension(widthSize, heightSize)
    }


    private fun onChangeToLandScape(){
        video_play_full.setOnClickListener(this)
        video_fullscreen.setOnClickListener(this)
        video_finish.setOnClickListener(this)
        danmaku_switch_full.setOnClickListener(this)
        video_seekBar_full.setOnSeekBarChangeListener(this)
        video_quility.setOnClickListener {
            hideBarUI()
            itemClickListener?.onQuilityTextClick()
        }
        video_quility.setText(mQuilityText)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.video_play,R.id.video_play_full->{
                if (video_view.isPlaying){
                    if (isLive)
                        video_view.stopPlayback()
                    else
                        pausePlay()

                    if (isPortrait)
                        video_play.setImageResource(R.drawable.ic_img_pause)
                    else
                        video_play_full.setImageResource(R.drawable.ic_img_pause)

                    play_icon.setImageResource(R.drawable.ic_img_pause)
                    play_icon.visibility = View.VISIBLE
                }else{
                    startPlay()
                    if (isPortrait)
                        video_play.setImageResource(R.drawable.ic_img_play)
                    else
                        video_play_full.setImageResource(R.drawable.ic_img_play)

                    play_icon.setImageResource(R.drawable.ic_img_play)
                    play_icon.visibility = View.INVISIBLE
                }
            }
            R.id.play_icon->{
                if (video_view.isPlaying){
                    pausePlay()
                    video_play.setImageResource(R.drawable.ic_img_pause)
                    play_icon.setImageResource(R.drawable.ic_img_pause)
                    play_icon.visibility = View.VISIBLE
                }else{
                    startPlay()
                    video_play.setImageResource(R.drawable.ic_img_play)
                    play_icon.setImageResource(R.drawable.ic_img_play)
                    mHandler.postDelayed({
                        play_icon.visibility = View.INVISIBLE
                    },500)
                }
            }
            R.id.video_button_continue->{
                video_netTie.visibility = View.GONE
                video_view.start()
            }
            R.id.video_fullscreen-> toggleFullScreen()
            R.id.video_finish->{
                if (isPortrait){
                    mActivity?.finish()
                }else{
                    toggleFullScreen()
                }
            }
            R.id.danmaku_switch_full->{
                if (danmaku.isShown){
                    danmaku.hide()
                    danmaku_switch_full.setImageResource(R.drawable.bili_player_danmaku_is_closed)
                }else{
                    danmaku.show()
                    danmaku_switch_full.setImageResource(R.drawable.bili_player_danmaku_is_open)
                }
            }
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (!fromUser){
            return
        }
        val position = progress
        if (isPortrait) video_currentTime.text = generateTime(position)
            else video_currentTime_full.text = generateTime(position)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        //开始拖动
        isDragging = true
        mHandler.removeMessages(MESSAGE_SHOW_PROGRESS)
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        //停止拖动
        isDragging = false
        mHandler.removeMessages(MESSAGE_SHOW_PROGRESS)
        video_view.seekTo(seekBar.progress)
        mHandler.sendEmptyMessageDelayed(MESSAGE_SHOW_PROGRESS,1000)
    }



    private fun statusChanged(what: Int) {
        this.mVideoState = what
        Log.i(TAG,"播放状态: " + mVideoState)

        when(mVideoState){
            PlayState.STATE_COMPLETED ->{
                Log.d(TAG,"播放结束")
                mCurrentPosition = 0
                mVideoState = PlayState.STATE_COMPLETED
            }

            PlayState.STATE_PREPARING,PlayState.MEDIA_INFO_BUFFERING_START,PlayState.STATE_PREPARING->{
                Log.d(TAG,"视频缓冲")
                video_progress.visibility = View.VISIBLE
                mVideoState = PlayState.STATE_PREPARING
            }
            PlayState.MEDIA_INFO_VIDEO_RENDERING_START,
            PlayState.STATE_PREPARED,
            PlayState.MEDIA_INFO_BUFFERING_END ->{
                Log.d(TAG,"视频缓冲结束")
                video_progress.visibility = View.INVISIBLE
                mHandler.postDelayed({
                    hideBarUI()
                    mHandler.sendEmptyMessage(MESSAGE_SHOW_PROGRESS)
                },500)
                mVideoState = PlayState.STATE_PLAYING
            }
            PlayState.MEDIA_INFO_VIDEO_INTERRUPT->{
                Log.d(TAG,"直播停止推流")
                if (isShowNetworkHint &&
                    NetworkUtil.getNetworkType(context) != NetworkUtil.NetworkType.NETWORK_WIFI &&
                    NetworkUtil.getNetworkType(context) != NetworkUtil.NetworkType.NETWORK_NO){
                    video_netTie.visibility = View.VISIBLE
                }
            }
            PlayState.STATE_ERROR,
            PlayState.MEDIA_INFO_UNKNOWN,
            PlayState.MEDIA_ERROR_IO,
            PlayState.MEDIA_ERROR_MALFORMED,
            PlayState.MEDIA_ERROR_UNSUPPORTED,
            PlayState.MEDIA_ERROR_TIMED_OUT,
            PlayState.MEDIA_ERROR_SERVER_DIED->{
                Log.d(TAG,"播放错误")
                mVideoState = PlayState.STATE_ERROR
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

        if (isPortrait){
            video_currentTime.text = generateTime(position)
            video_endTime.text = generateTime(duration)
            video_seekBar.max = duration
            video_seekBar.progress = position
            video_seekBar.secondaryProgress = bufferPos * 1000
        }else{
            video_currentTime_full.text = generateTime(position)
            video_endTime_full.text = generateTime(duration)
            video_seekBar_full.max = duration
            video_seekBar_full.progress = position
            video_seekBar_full.secondaryProgress = bufferPos * 1000
        }
        return position
    }

    fun setLive(live:Boolean): SimplePlayerView{
        this.isLive = live
        return this
    }
    fun setVideoUrl(url: String): SimplePlayerView{
        this.mVideoUrl = url
        if(isLive){
            video_seekBar.visibility = View.INVISIBLE
            tv_sprit.visibility = View.INVISIBLE
            video_endTime.visibility = View.INVISIBLE
        }
        //video_view.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT)
        video_view.setAspectRatio(IRenderView.AR_16_9_FIT_PARENT)
        video_view.setVideoURI(Uri.parse(url),isLive)
        return this
    }

    /**
     * IRenderView
     */
    fun setSize(width :Int,height :Int): SimplePlayerView{
        var scale = width / (height * 1.0f)
        when(scale){
            16.0f/9.0f -> video_view.setAspectRatio(IRenderView.AR_16_9_FIT_PARENT)
            4.0f/3.0f -> video_view.setAspectRatio(IRenderView.AR_4_3_FIT_PARENT)
            else -> video_view.setAspectRatio(IRenderView.AR_16_9_FIT_PARENT)
        }

        return this
    }

    fun startPlay(){
        if (isLive){
            video_view.setVideoPath(mVideoUrl)
            video_view.seekTo(0)
        }
        if (isShowNetworkHint &&
            NetworkUtil.getNetworkType(context) != NetworkUtil.NetworkType.NETWORK_WIFI &&
            NetworkUtil.getNetworkType(context) != NetworkUtil.NetworkType.NETWORK_NO){
            video_netTie.visibility = View.VISIBLE
        }else{
            video_view.start()
        }
        danmaku.start(mDanmukuPosition)

    }

    private var mDanmukuPosition = 0L
    fun pausePlay(){
        mVideoState = PlayState.STATE_PAUSED
        getCurrentPosition()
        video_view.pause()
        danmaku.pause()
        mDanmukuPosition = danmaku.currentTime
    }

    fun stopPlay(){
        video_view.stopPlayback()
        video_view.release(true)
        danmaku.stop()
    }

    fun setVideoTitle(title: String): SimplePlayerView{
        video_title.text = title
        return this
    }

    private var mQuilityText = "自动"
    fun setQuilityText(text:String): SimplePlayerView{
        mQuilityText = text
        return this
    }

    /**
     * 初始化弹幕库
     * @param url 弹幕地址
     */
    fun initDanMaKu(url:String): SimplePlayerView{
        BiliDanmuku.initDanmaku(danmaku)
        getDanmakuFromUrl(url)
        return this
    }

    /**
     * 初始化弹幕库
     * @param url 弹幕xml地址
     * @param delay 延迟获取，同时初始化视频和弹幕部分手机可能会卡顿
     */
    fun initDanMaKu(url :String,delay:Long): SimplePlayerView{
        this.postDelayed({
            BiliDanmuku.initDanmaku(danmaku)
            getDanmakuFromUrl(url)
        },delay)
        return this
    }

    /**
     * 是否显示网络状态提示
     */
    fun showNetWorkHint(show: Boolean): SimplePlayerView{
        isShowNetworkHint = show
        return this
    }

    /**
     * 自定义底部状态栏
     */
    fun setBottomViewBar(view :View){
        bottom_root.removeAllViews()
        bottom_root.addView(view)
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

    fun isPlaying():Boolean{
        when(mVideoState){
            PlayState.STATE_PLAYING -> return true
            else -> return false
        }
    }


    private val mTopMargin:Int by lazy { (video_top.layoutParams as (ViewGroup.MarginLayoutParams)).topMargin }
    private fun showBarUI(){
        if (!isHiddenBar) return
        var margin = 0
        var layoutParams = video_top.layoutParams as (ViewGroup.MarginLayoutParams)
        if (isPortrait){
            margin = mTopMargin
        }
        layoutParams.setMargins(0,margin,0,0)
        video_top.layoutParams = layoutParams
        toggleAnim(video_top,-video_top.height.toFloat()-margin,0f)
        toggleAnim(bottom_root, bottom_root.height.toFloat(),0f)
        isHiddenBar = false
        /*//3秒之后隐藏状态栏
        mHandler.postDelayed({
            hideBarUI()
        },2000)*/
    }

    /**
     * 隐藏顶部和底部的状态栏
     */
    private fun hideBarUI(){
        if (isHiddenBar) return
        var margin = 0
        var layoutParams = video_top.layoutParams as (ViewGroup.MarginLayoutParams)
        if (isPortrait){
            margin = mTopMargin
        }
        layoutParams.setMargins(0,margin,0,0)
        video_top.layoutParams = layoutParams
        toggleAnim(video_top,0f,-video_top.height.toFloat() - margin)
        toggleAnim(bottom_root,0f, bottom_root.height.toFloat())
        isHiddenBar = true
    }

    private fun toggleAnim(view: View ,fromY:Float,toY:Float) {
        val animatorx = ObjectAnimator.ofFloat(view, "translationY", fromY, toY)
        //ObjectAnimator animatory = ObjectAnimator.ofFloat(view,"scaleY",visible?0f:1f,visible?1f:0f);
        val animatorSet = AnimatorSet()
        animatorSet.duration = 500
        animatorSet.play(animatorx)
        animatorSet.start()
    }

    /**
     * 全透状态栏
     */
    protected fun setStatusBarTransparent(visible :Boolean) {
        val window = mActivity!!.window
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            if (visible){
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_VISIBLE
            }else{
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    fun onBackPressed(){
        //这个地方横屏才响应
        if (!isPortrait){
            toggleFullScreen()
        }else{
            //TODO: 存在fragment栈的情况
            mActivity?.finish()
        }
    }
    /**
     * activity横竖屏切换调用此方法
     */
    fun onConfigurationChang(conf: Configuration?) :Boolean{
        isPortrait = conf!!.orientation == Configuration.ORIENTATION_PORTRAIT
        mHandler.post {
            //tryFullScreen(!portrait)
            if (isPortrait) {
                this@SimplePlayerView.size(false,initHeight, false)
            } else {
                val heightPixels = mActivity!!.getResources().displayMetrics.heightPixels
                val widthPixels = mActivity!!.getResources().displayMetrics.widthPixels
                this@SimplePlayerView.size(false,Math.min(heightPixels, widthPixels), false)
            }
            updateFullScreenButton()
        }
        //orientationEventListener.enable()
        return isPortrait
    }

    private fun size(width: Boolean, n: Int, dip: Boolean) {
        var n = n
        val lp = getLayoutParams()
        if (n > 0 && dip) {
            n = dip2pixel(context, n.toFloat())
        }
        if (width) {
            lp.width = n
        } else {
            lp.height = n
        }
        setLayoutParams(lp)
    }

    fun dip2pixel(context: Context, n: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, n, context.resources.displayMetrics).toInt()
    }

    /**
     * 全屏切换
     */
    private fun toggleFullScreen(): SimplePlayerView {
        if (getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            mActivity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            bottom_root.removeView(bottomBarFullScreen)
            bottom_root.addView(bottomBarHalf)
        } else {
            //因为是延迟初始化，所以在这里需要使用initHeight
            Log.i(TAG,"记录竖屏状态下的hiehgt：" + initHeight)
            mActivity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            bottom_root.removeView(bottomBarHalf)
            bottom_root.addView(bottomBarFullScreen)
            onChangeToLandScape()
        }
        return this
    }

    private fun updateFullScreenButton() {
        //layoutParams.width = LayoutParams.MATCH_PARENT
        //layoutParams.height = LayoutParams.MATCH_PARENT
        //setLayoutParams(layoutParams)
        changeWindow()
    }

    fun changeWindow(){
        val attrs = mActivity!!.getWindow().getAttributes()
        if (isPortrait) {
            attrs.flags = attrs.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
            mActivity!!.getWindow().setAttributes(attrs)
            mActivity!!.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        } else {
            attrs.flags = attrs.flags or WindowManager.LayoutParams.FLAG_FULLSCREEN
            mActivity!!.getWindow().setAttributes(attrs)
            mActivity!!.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }

    private fun getScreenOrientation(): Int {
        val rotation = mActivity!!.getWindowManager().getDefaultDisplay().getRotation()
        val dm = DisplayMetrics()
        mActivity!!.getWindowManager().getDefaultDisplay().getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels
        val orientation: Int
        // if the device's natural orientation is portrait:
        if ((rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) && height > width || (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) && width > height) {
            when (rotation) {
                Surface.ROTATION_0 -> orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                Surface.ROTATION_90 -> orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                Surface.ROTATION_180 -> orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                Surface.ROTATION_270 -> orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                else -> orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        } else {
            when (rotation) {
                Surface.ROTATION_0 -> orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                Surface.ROTATION_90 -> orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                Surface.ROTATION_180 -> orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                Surface.ROTATION_270 -> orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                else -> orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
        }// if the device's natural orientation is landscape or if the device
        // is square:
        return orientation
    }
    fun onPause(){
        //恢复系统其它媒体的状态
        muteAudioFocus(context,true)
        getCurrentPosition()
        video_view.release(false)
        danmaku.pause()
    }

    fun onResume(){
        //暂停系统其它媒体的状态
        muteAudioFocus(context,false)
        video_view.openVideo()
        if (isLive){
            video_view.seekTo(0)
        }else{
            video_view.seekTo(mCurrentPosition)
        }
        danmaku.resume()
    }

    fun onRestart(){
        video_view.openVideo()
        if (isLive){
            video_view.seekTo(0)
        }else{
            video_view.seekTo(mCurrentPosition)
        }
        danmaku.restart()
    }

    fun onDestory(){
        mHandler.removeCallbacksAndMessages(null)
        stopPlay()
        danmaku.release()
    }

    override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
        //将触摸事件交给GestureDetector
        if(mGestureDector!!.onTouchEvent(motionEvent)){
            return true
        }
        //多点触控
        when(motionEvent!!.action and MotionEvent.ACTION_MASK){
            MotionEvent.ACTION_UP -> endGesture()
        }
        return false
    }

    /**
     * 手势结束
     */
    private fun endGesture() {
        volume = -1
        brightness = -1f
        if (newPosition >= 0) {
            mHandler.removeMessages(MESSAGE_SEEK_NEW_POSITION)
            mHandler.sendEmptyMessage(MESSAGE_SEEK_NEW_POSITION)
        } else {
            /**什么都不做(do nothing) */
        }
        mHandler.removeMessages(MESSAGE_HIDE_CENTER_BOX)
        mHandler.sendEmptyMessageDelayed(MESSAGE_HIDE_CENTER_BOX, 500)
        ll_video_progress.visibility = View.GONE
        video_volume_controller_root.visibility = View.GONE
        video_brightness_controller_root.visibility = View.GONE
    }

    private fun getActivityFromContext(context: Context?): Activity? {
        var context = context
        if (null != context) {
            while (context is ContextWrapper) {
                if (context is Activity) {
                    return context
                }else if (context is Fragment){
                    return context.activity
                }
                context = context.baseContext
            }
        }
        return null
    }

    /**
     * @param bMute 值为true时为关闭背景音乐。
     */
    private fun muteAudioFocus(context: Context, bMute: Boolean): Boolean {
        var bool = false
        val am = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if (bMute) {
            val result = am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
            bool = result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        } else {
            val result = am.abandonAudioFocus(null)
            bool = result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        }
        return bool
    }

    private open inner class PlayerGestureDetector: GestureDetector.SimpleOnGestureListener(){
        private var firstTouch: Boolean = false  //是否是按下的标识，默认为其他动作
        private var volumeControl: Boolean = false  //控制声音
        private var toSeek: Boolean = false  //进度条
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            //return super.onSingleTapUp(e)
            if (isHiddenBar){
                showBarUI()
            }else{
                hideBarUI()
            }
            return true
        }

        override fun onDown(e: MotionEvent?): Boolean {
            firstTouch = true
            return super.onDown(e)
        }
        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            val mOldX = e1!!.getX()
            val mOldY = e1!!.getY()
            val deltaX = mOldX - e2!!.getX()
            val deltaY = mOldY - e2!!.getY()

            if (firstTouch) {
                toSeek = Math.abs(distanceX) >= Math.abs(distanceY)  //横向滑动
                volumeControl = mOldX > screenWidthPixels!! * 0.5f  //左边是控制亮度，右边控制音量
                firstTouch = false
            }

            if (toSeek) {
                if (!isLive) {
                    onProgressSlide(-deltaX / video_view.getWidth())
                }
            } else {
                val percent = deltaY / video_view.getHeight()
                if (volumeControl) {
                    onVolumeSlide(percent)
                } else {
                    onBrightnessSlide(percent)
                }
            }
            return super.onScroll(e1, e2, distanceX, distanceY)
        }
    }

    /**
     * 滑动改变声音大小
     *
     * @param percent
     */
    private fun onVolumeSlide(percent: Float) {
        if (volume == -1) {
            volume = mAudioManager!!.getStreamVolume(AudioManager.STREAM_MUSIC)
            if (volume < 0)
                volume = 0
        }
        var index = (percent * mMaxVolume).toInt() + volume
        if (index > mMaxVolume) {
            index = mMaxVolume
        } else if (index < 0) {
            index = 0
        }
        // 变更声音
        mAudioManager!!.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0)
        // 变更进度条
        val i = (index * 1.0 / mMaxVolume * 100).toInt()
        var s = "" + i + "%"
        if (i == 0) {
            s = "off"
        }
        DebugLog.d("", "onVolumeSlide:$s")
        if (video_volume_controller_root.visibility == View.GONE){
            video_volume_controller_root.visibility = View.VISIBLE
        }
        video_volume_controller.max = 100
        video_volume_controller.setProgress(i)
    }

    /**
     * 滑动改变进度
     *
     * @param percent
     */
    private fun onProgressSlide(percent: Float) {
        val position = video_view.getCurrentPosition().toLong()
        val duration = video_view.getDuration().toLong()
        val deltaMax = Math.min((100 * 1000).toLong(), duration - position)
        var delta = (deltaMax * percent).toLong()

        newPosition = delta + position
        if (newPosition > duration) {
            newPosition = duration
        } else if (newPosition <= 0) {
            newPosition = 0
            delta = -position
        }
        val showDelta = delta.toInt() / 1000
        if (showDelta != 0) {
            val text = if (showDelta > 0) "+$showDelta" else "" + showDelta
            DebugLog.d("", "onProgressSlide:$text")
        }
        showBarUI()
        video_currentTime.text = generateTime(newPosition.toInt())
        video_seekBar.progress = newPosition.toInt()
        ll_video_progress.visibility = View.VISIBLE
        video_progress_text.text = video_currentTime.text.toString() + "/" + video_endTime.text.toString()
    }

    /**
     * 滑动改变亮度
     *
     * @param percent
     */
    private fun onBrightnessSlide(percent: Float) {
        if (brightness < 0) {
            brightness = mActivity!!.getWindow().getAttributes().screenBrightness
            if (brightness <= 0.00f) {
                brightness = 0.50f
            } else if (brightness < 0.01f) {
                brightness = 0.01f
            }
        }
        DebugLog.d("", "brightness:$brightness,percent:$percent")
        val lpa = mActivity!!.getWindow().getAttributes()
        lpa.screenBrightness = brightness + percent
        if (lpa.screenBrightness > 1.0f) {
            lpa.screenBrightness = 1.0f
        } else if (lpa.screenBrightness < 0.01f) {
            lpa.screenBrightness = 0.01f
        }
        mActivity!!.getWindow().setAttributes(lpa)

        if (video_brightness_controller_root.visibility == View.GONE){
            video_brightness_controller_root.visibility = View.VISIBLE
        }
        video_brightness_controller.max = 100
        video_brightness_controller.setProgress((lpa.screenBrightness*100).toInt())
    }

    /**
     * 获取弹幕并播放
     */
    private fun getDanmakuFromUrl(url :String){
        var client =  OkHttpClient();//创建OkHttpClient对象
        var request = Request.Builder()
            .url(url)//请求接口。如果需要传参拼接到接口后面。
            .build();//创建Request 对象
        //var response = client.newCall(request).execute();//得到Response 对象
        client.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG,"播放弹幕失败$e")
            }
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful()) {
                    var response = response.body()
                    val parser = BiliDanmuku.parseDanmaku(response?.bytes())
                    danmaku.post {
                        BiliDanmuku.playDanmaku(parser,danmaku)
                    }
                }else{
                    Log.e(TAG,"播放弹幕失败$response")
                }
            }

        })
    }

}
