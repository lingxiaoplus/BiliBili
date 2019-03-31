package com.bilibili.lingxiao

import android.app.Application
import android.content.Context
import com.bilibili.lingxiao.utils.UIUtil
import com.facebook.drawee.backends.pipeline.Fresco
import com.lingxiao.skinlibrary.SkinLib

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        UIUtil.init(this)
        SkinLib.init(this)
    }

}
