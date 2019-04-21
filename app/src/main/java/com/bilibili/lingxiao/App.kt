package com.bilibili.lingxiao

import android.app.Application
import android.content.Context
import com.bilibili.lingxiao.utils.UIUtil
import com.facebook.drawee.backends.pipeline.Fresco
import com.lingxiao.skinlibrary.SkinLib
import com.tencent.bugly.Bugly
import com.tencent.smtt.sdk.QbSdk

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        UIUtil.init(this)
        SkinLib.init(this)
        Bugly.init(this,"180f968345",true)
        QbSdk.initX5Environment(this,null)
    }

}
