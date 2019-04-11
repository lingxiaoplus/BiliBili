package com.bilibili.lingxiao.ijkplayer.danmuku

import android.graphics.Color
import android.util.Log
import master.flame.danmaku.controller.DrawHandler
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory
import master.flame.danmaku.danmaku.model.BaseDanmaku
import master.flame.danmaku.danmaku.model.DanmakuTimer
import master.flame.danmaku.danmaku.model.IDisplayer
import master.flame.danmaku.danmaku.model.android.DanmakuContext
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser
import master.flame.danmaku.ui.widget.DanmakuView
import java.io.ByteArrayInputStream
import java.util.HashMap

object BiliDanmuku {
    //弹幕字体大小偏移量 1f是xml解析之后原始的大小
    public val textSizeOffset = 1.3f
    private val mDanmakuContext by lazy { DanmakuContext.create() }
    private var initialized = false
    private val TAG = BiliDanmuku::class.java.simpleName


    /**
     * 初始化弹幕库  只需要初始化一次
     */
    fun initDanmaku(danmakuView: DanmakuView) {
        if (initialized) return
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
        initialized = true
    }

    /**
     * 添加文本弹幕
     * @param islive 直播弹幕
     * @param density 像素密度
     * @param text 要发送的弹幕
     * @param danmakuContext 弹幕上下文
     */
    fun addDanmaku(islive: Boolean, density: Float, text:String, danmakuView: DanmakuView?) {
        val danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL)
        if (danmaku == null || danmakuView == null) {
            return
        }
        danmaku.text = text + System.nanoTime()
        danmaku.padding = 5
        danmaku.priority = 0  //0 表示可能会被各种过滤器过滤并隐藏显示 //1 表示一定会显示, 一般用于本机发送的弹幕
        danmaku.isLive = islive //是否是直播弹幕
        //danmaku.time = danmakuView.getCurrentTime() + 1200; //显示时间
        danmaku.textSize = 25f * (density - 0.6f) / textSizeOffset
        danmaku.textColor = Color.RED
        danmaku.textShadowColor = Color.WHITE //阴影/描边颜色
        danmaku.borderColor = Color.GREEN //边框颜色，0表示无边框
        danmakuView.addDanmaku(danmaku)
    }

    /**
     * 解析弹幕
     * @param danmakuData 弹幕数据
     */
    fun parseDanmaku(danmakuData:ByteArray?): BiliDanmukuParser{
        var loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI)
        var bytes = BiliDanmukuCompressionTools.decompressXML(danmakuData)
        var inputStream = ByteArrayInputStream(bytes)
        loader.load(inputStream)
        val parser = BiliDanmukuParser()
        assert(loader != null)
        val dataSource = loader.dataSource
        parser.load(dataSource)
        return parser
    }

    /**
     * 播放弹幕
     * @param danmakuParser 弹幕解析类
     * @param danmakuView
     */
    fun playDanmaku(danmakuParser: BaseDanmakuParser,danmakuView: DanmakuView){
        danmakuView.prepare(danmakuParser, mDanmakuContext)
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
                //Log.d(TAG,"弹幕：" + danmaku.text)
            }

            override fun drawingFinished() {

            }
        })
    }
}
