package com.bilibili.lingxiao

import android.app.Application
import androidx.room.Room
import com.bilibili.lingxiao.database.db.AppDatabase
import com.bilibili.lingxiao.utils.UIUtil

import com.facebook.drawee.backends.pipeline.Fresco


class App : Application() {
    private val TAG = App::class.java.simpleName
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        UIUtil.init(this)
        InitIalizeService.initialize(this);

    }

}
