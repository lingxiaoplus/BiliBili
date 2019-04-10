package com.bilibili.lingxiao.play

import android.content.res.Configuration
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import android.view.WindowManager
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.dagger.DaggerUiComponent
import com.bilibili.lingxiao.ijkplayer.danmuku.BiliDanmukuCompressionTools
import com.bilibili.lingxiao.ijkplayer.danmuku.BiliDanmukuParser
import com.bilibili.lingxiao.play.model.VideoData
import com.camera.lingxiao.common.app.BaseActivity
import com.camera.lingxiao.common.app.BaseFragment
import com.camera.lingxiao.common.exception.ApiException
import com.camera.lingxiao.common.observer.HttpRxCallback
import com.camera.lingxiao.common.observer.HttpRxObserver
import com.camera.lingxiao.common.utills.LogUtils
import com.camera.lingxiao.common.utills.RxJavaHelp
import com.github.zackratos.ultimatebar.UltimateBar
import com.google.gson.Gson
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_play.*
import master.flame.danmaku.controller.DrawHandler
import master.flame.danmaku.danmaku.loader.ILoader
import master.flame.danmaku.danmaku.loader.IllegalDataException
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory
import master.flame.danmaku.danmaku.model.BaseDanmaku
import master.flame.danmaku.danmaku.model.DanmakuTimer
import master.flame.danmaku.danmaku.model.IDisplayer
import master.flame.danmaku.danmaku.model.android.DanmakuContext
import master.flame.danmaku.ui.widget.DanmakuView
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.lang.StringBuilder
import java.net.URLDecoder
import java.util.*
import java.util.zip.DataFormatException
import javax.inject.Inject
import kotlin.properties.Delegates

class PlayActivity : BaseActivity() {
    var tabArray = arrayOf("简介","评论")
    var fragmentList:ArrayList<BaseFragment> = arrayListOf()
    var danmakuView: DanmakuView by Delegates.notNull()
    @Inject
    lateinit var  introduceFragment:IntroduceFragment
    @Inject
    lateinit var  commentFragment: CommentFragment

    lateinit var mDanmakuContext: DanmakuContext
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

    override fun initWidget() {
        super.initWidget()
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        var uri = intent.data
        //uri.getQueryParameter("player_width")
        //uri.getQueryParameter("player_height")
        //uri.getQueryParameter("player_rotate")
        var player_preload = URLDecoder.decode(uri.getQueryParameter("player_preload"),"UTF-8")
        var videoData = Gson().fromJson(player_preload, VideoData::class.java)
        LogUtils.d("需要播放的video信息：" + videoData.url)

        play_view
            .setLive(true)
            .setVideoUrl(videoData.url)
            .startPlay()

        for (name in tabArray){
            skin_tabLayout.addTab(skin_tabLayout.newTab().setText(name))
        }

        play_viewpager.adapter = PlayPagerAdapter(supportFragmentManager)
        skin_tabLayout.setupWithViewPager(play_viewpager)

        fragmentList.add(introduceFragment)
        fragmentList.add(commentFragment)

        danmakuView = play_view.getDanmakuView()
        initDanmaku()
        getDanmaku(videoData.cid)

    }


    fun getDanmaku(cid:Int){
        RxJavaHelp.workWithLifecycle(this,object :ObservableOnSubscribe<BiliDanmukuParser>{
            override fun subscribe(e: ObservableEmitter<BiliDanmukuParser>) {

                var url = StringBuilder()
                url.append("http://comment.bilibili.com/")
                url.append(cid)
                url.append(".xml")
                var client =  OkHttpClient();//创建OkHttpClient对象
                var request = Request.Builder()
                    .url(url.toString())//请求接口。如果需要传参拼接到接口后面。
                    .build();//创建Request 对象
                var response = client.newCall(request).execute();//得到Response 对象
                if (response.isSuccessful()) {
                    var response = response.body()
                    var loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI)
                    var bytes = BiliDanmukuCompressionTools.decompressXML(response?.bytes())
                    var inputStream = ByteArrayInputStream(bytes)

                    loader.load(inputStream)
                    val parser = BiliDanmukuParser()
                    assert(loader != null)
                    val dataSource = loader.dataSource
                    parser.load(dataSource)
                    e.onNext(parser)
                    e.onComplete()
                }
            }

        },object : HttpRxObserver<BiliDanmukuParser>("danmku") {
            override fun onStart(d: Disposable) {

            }

            override fun onError(e: ApiException) {
                Log.e(TAG,"播放弹幕失败" + e)
            }

            override fun onSuccess(response: BiliDanmukuParser) {
                danmakuView.prepare(response, mDanmakuContext)
                danmakuView.showFPS(false)//是否显示FPS
                danmakuView.enableDanmakuDrawingCache(true)
                danmakuView.setCallback(object : DrawHandler.Callback {
                    override fun prepared() {
                        danmakuView.start()
                        Log.d(TAG,"开始播放弹幕")
                    }

                    override fun updateTimer(danmakuTimer: DanmakuTimer) {

                    }

                    override fun danmakuShown(danmaku: BaseDanmaku) {
                        Log.d(TAG,"弹幕：" + danmaku.text)
                    }

                    override fun drawingFinished() {

                    }
                })
            }

        })
    }

    private fun initDanmaku() {
        mDanmakuContext = DanmakuContext.create()
        // 设置最大行数,从右向左滚动(有其它方向可选)
        var maxLinesPair = HashMap<Int, Int>()
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 3)
        //配置弹幕库
        danmakuView.enableDanmakuDrawingCache(true)
        // 设置是否禁止重叠
        var overlappingEnablePair = HashMap<Int, Boolean>()
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_LR, true)
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_BOTTOM, true)
        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3f) //设置描边样式
            .setDuplicateMergingEnabled(false)
            .setScrollSpeedFactor(1.2f) //是否启用合并重复弹幕
            .setScaleTextSize(1.2f) //设置弹幕滚动速度系数,只对滚动弹幕有效
            // 默认使用{@link SimpleTextCacheStuffer}只支持纯文字显示,
            // 如果需要图文混排请设置{@link SpannedCacheStuffer}
            // 如果需要定制其他样式请扩展{@link SimpleTextCacheStuffer}|{@link SpannedCacheStuffer}
            .setMaximumLines(maxLinesPair) //设置最大显示行数
            .preventOverlapping(overlappingEnablePair) //设置防弹幕重叠，null为允许重叠

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

    inner class PlayPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return tabArray.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList.get(position)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabArray.get(position)
        }
    }
}
