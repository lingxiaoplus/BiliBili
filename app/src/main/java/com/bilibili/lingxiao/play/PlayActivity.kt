package com.bilibili.lingxiao.play

import android.content.res.Configuration
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.WindowManager
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.dagger.DaggerUiComponent
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
import master.flame.danmaku.danmaku.loader.ILoader
import master.flame.danmaku.danmaku.loader.IllegalDataException
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory
import master.flame.danmaku.ui.widget.DanmakuView
import java.io.ByteArrayInputStream
import java.io.IOException
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


    }


    fun getDanmaku(){
        RxJavaHelp.workWithLifecycle(this,object :ObservableOnSubscribe<Any>{
            override fun subscribe(e: ObservableEmitter<Any>) {
                /*var loader: ILoader? = null
                try {
                    val rsp = Jsoup.connect(uri).timeout(20000).execute() as HttpConnection.Response
                    val stream = ByteArrayInputStream(BiliDanmukuCompressionTools.decompressXML(rsp.bodyAsBytes()))

                    loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI)
                    loader!!.load(stream)
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: DataFormatException) {
                    e.printStackTrace()
                } catch (e: IllegalDataException) {
                    e.printStackTrace()
                }

                val parser = BiliDanmukuParser()
                assert(loader != null)
                val dataSource = loader!!.dataSource
                parser.load(dataSource)
                emitter.onNext(parser)*/
            }

        },object : HttpRxObserver<Any>("danmku") {
            override fun onStart(d: Disposable) {

            }

            override fun onError(e: ApiException) {

            }

            override fun onSuccess(response: Any) {

            }

        })
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
