package com.bilibili.lingxiao

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.Common
import com.camera.lingxiao.common.exception.crash.CrashActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.lingxiao.skinlibrary.SkinLib
import com.raizlabs.android.dbflow.config.FlowManager
import com.tencent.smtt.sdk.QbSdk

class App : Application() {
    private val TAG = App::class.java.simpleName
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        UIUtil.init(this)
        InitIalizeService.initialize(this);
    }

}
