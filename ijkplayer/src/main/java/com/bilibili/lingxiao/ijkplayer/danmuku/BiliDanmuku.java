package com.bilibili.lingxiao.ijkplayer.danmuku;

import android.graphics.Color;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.ui.widget.DanmakuView;

public class BiliDanmuku {

    /**
     * 添加文本弹幕
     * @param islive
     */
    public static void addDanmaku(boolean islive,float density, DanmakuContext danmakuContext, DanmakuView danmakuView) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || danmakuView == null) {
            return;
        }
        danmaku.text = "这是一条弹幕" + System.nanoTime();
        danmaku.padding = 5;
        danmaku.priority = 0;  //0 表示可能会被各种过滤器过滤并隐藏显示 //1 表示一定会显示, 一般用于本机发送的弹幕
        danmaku.isLive = islive; //是否是直播弹幕
        //danmaku.time = danmakuView.getCurrentTime() + 1200; //显示时间
        danmaku.textSize = 25f * (density - 0.6f);
        danmaku.textColor = Color.RED;
        danmaku.textShadowColor = Color.WHITE; //阴影/描边颜色
        danmaku.borderColor = Color.GREEN; //边框颜色，0表示无边框
        danmakuView.addDanmaku(danmaku);
    }
}
