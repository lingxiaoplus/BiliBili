package com.bilibili.lingxiao.play.ui

import android.content.res.Configuration
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.adapter.PlayPagerAdapter
import com.bilibili.lingxiao.dagger.DaggerUiComponent
import com.bilibili.lingxiao.ijkplayer.widget.SimplePlayerView
import com.bilibili.lingxiao.play.model.VideoData
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseActivity
import com.camera.lingxiao.common.app.BaseFragment
import com.camera.lingxiao.common.utills.LogUtils
import com.camera.lingxiao.common.utills.PopwindowUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.zackratos.ultimatebar.UltimateBar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.activity_setting.*
import java.lang.StringBuilder
import java.net.URLDecoder
import java.util.*
import javax.inject.Inject

class PlayActivity : BaseActivity() {
    var tabArray = arrayOf("简介","评论")
    var fragmentList:ArrayList<BaseFragment> = arrayListOf()

    @Inject
    lateinit var  introduceFragment: IntroduceFragment
    @Inject
    lateinit var  commentFragment: CommentFragment

    lateinit var videoInfo:VideoData
    val TAG = PlayActivity::class.java.simpleName
    override val contentLayoutId: Int
        get() = R.layout.activity_play

    override fun initInject() {
        super.initInject()
        DaggerUiComponent.builder().build().inject(this)
    }

    override fun initBefore() {
        UltimateBar.newTransparentBuilder()
            .statusColor(resources.getColor(R.color.colorTrans))        // 状态栏颜色
            .statusAlpha(100)               // 状态栏透明度
            .applyNav(true)                // 是否应用到导航栏
            .build(this)
            .apply();
    }
    @Throws(NumberFormatException::class)
    override fun initWidget() {
        super.initWidget()
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        var uri = intent.data
        var player_preload = URLDecoder.decode(uri.getQueryParameter("player_preload"),"UTF-8")
        var player_width = URLDecoder.decode(uri.getQueryParameter("player_width"),"UTF-8").toInt()
        var player_height = URLDecoder.decode(uri.getQueryParameter("player_height"),"UTF-8").toInt()
        var player_rotate = URLDecoder.decode(uri.getQueryParameter("player_rotate"),"UTF-8") //0为不旋转
        videoInfo = Gson().fromJson(player_preload, VideoData::class.java)

        UIUtil.getDensityString()
        var layoutParams = ViewGroup.LayoutParams(play_view.width,
            (player_width / (player_height+1.0f) * play_view.width).toInt())
        Log.d(TAG,"需要播放的video宽高：width: ${layoutParams.width}, heigth: ${layoutParams.height}, 信息：$videoInfo")
        //play_view.layoutParams = layoutParams

        var danmakuUrl = "http://comment.bilibili.com/${videoInfo.cid}.xml"
        var quilityPosition = getQuilityIndex()
        play_view
            .setLive(true)
            .setVideoUrl(videoInfo.url)
            .setSize(player_width,player_height)
            .setQuilityText(videoInfo.support_description[quilityPosition])
            .initDanMaKu(danmakuUrl,2000)
            .startPlay()
        play_view.setPlayerItemClickListener(object :SimplePlayerView.OnPlayerItemClickListener{
            override fun onQuilityTextClick() {
                showSupportQuilityWindow(quilityPosition)
            }
        })

        for (name in tabArray){
            skin_tabLayout.addTab(skin_tabLayout.newTab().setText(name))
        }
        fragmentList.add(introduceFragment)
        fragmentList.add(commentFragment)
        play_viewpager.adapter =
            PlayPagerAdapter(supportFragmentManager, tabArray, fragmentList)
        skin_tabLayout.setupWithViewPager(play_viewpager)
        //setSupportActionBar(toolbar)
        //toolbar.title = ""
    }

    /*fun showCommentDetail(fragmentTag:String){
        var transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.pop_show,R.anim.pop_hide)
        if (commentDetailFragment.isAdded){
            transaction.show(commentDetailFragment).commitAllowingStateLoss()
        }else{
            transaction
                .add(R.id.comment_detail,commentDetailFragment,fragmentTag)
                .addToBackStack(fragmentTag)
                .commitAllowingStateLoss()
        }

    }

    fun hideCommentDetail(){
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.pop_hide,R.anim.pop_show)
            .remove(commentDetailFragment)
            .commitAllowingStateLoss()
    }*/


    fun getQuilityIndex():Int{
        var index = 0
        for ((position,item) in videoInfo.support_quality.withIndex()){
            if (item == videoInfo.quality){
                index = position
                break
            }
        }
        return index
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
        fragmentList.clear()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        play_view.onBackPressed()
    }

    private fun showSupportQuilityWindow(index:Int){
        val popwindowUtil = PopwindowUtil.PopupWindowBuilder(this)
            .setView(R.layout.popwindow_play_support_quality)
            .size(LinearLayout.LayoutParams.WRAP_CONTENT.toFloat(), LinearLayout.LayoutParams.MATCH_PARENT.toFloat())
            .setAnimationStyle(R.style.pop_player_support_quility)
            .setFocusable(true)
            .setTouchable(true)
            .setOutsideTouchable(true)
            .create()
        popwindowUtil.showAtLocation(play_view,0,0, Gravity.RIGHT)
        var recycerView = popwindowUtil.getView<RecyclerView>(R.id.recyclerview)
        recycerView?.layoutManager = GridLayoutManager(this,
            videoInfo.support_description.size,GridLayoutManager.HORIZONTAL,false)
        recycerView?.adapter = PlayQuilityAdapter(R.layout.item_play_support_quility,videoInfo.support_description,index)
    }

    //data class QuilityData(var title:String,var choosed:Boolean)
    inner class PlayQuilityAdapter(layoutId: Int,data: List<String>) :BaseQuickAdapter<String, BaseViewHolder>(layoutId,data) {
        var defaultPosition:Int = 0
        constructor(layoutId: Int,data: List<String>,position:Int):this(layoutId,data){
            defaultPosition = position
        }
        override fun convert(helper: BaseViewHolder, item: String) {
            var imageVip = helper.getView<ImageView>(R.id.image_vip)
            if (item.contains("1080PP60")){
                imageVip.visibility = View.VISIBLE
            }else{
                imageVip.visibility = View.GONE
            }
            if (helper.position == defaultPosition){
                helper.setTextColor(R.id.text_quility,mContext.resources.getColor(R.color.colorPrimary))
            }
            helper.setText(R.id.text_quility,item)
            Log.d(TAG,"视频质量：$item")
        }
    }
}
