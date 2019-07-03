package com.bilibili.lingxiao

import android.app.Application
import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.bilibili.lingxiao.database.NetCacheDatabase

import com.camera.lingxiao.common.Common
import com.lingxiao.skinlibrary.SkinLib
import com.raizlabs.android.dbflow.config.FlowManager
import com.tencent.smtt.sdk.QbSdk
import com.raizlabs.android.dbflow.config.FlowConfig



/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 *
 *
 * 初始化操作都放进这个service
 */
class InitIalizeService : IntentService("InitIalizeService") {

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //startForeground(1,new Notification());
            startMyOwnForeground()
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun startMyOwnForeground() {
        val NOTIFICATION_CHANNEL_ID = "com.example.simpleapp"
        val channelName = "My Background Service"
        val chan = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(chan)

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("App is running in background")
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(2, notification)
    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (ACTION_FOO == action) {
                performInit()
            }
        }
    }

    /**
     * 初始化操作
     */
    private fun performInit() {
        SkinLib.init(mContext as Application)
        QbSdk.initX5Environment(this, null)
        Common.initCrash(this, true)
        FlowManager.init(FlowConfig.Builder(this).build())
    }

    companion object {
        // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
        private val ACTION_FOO = "com.lingxiaosuse.picture.tudimension.service.action.FOO"
        private lateinit var mContext: Context
        fun initialize(context: Context) {
            val intent = Intent(context, InitIalizeService::class.java)
            intent.action = ACTION_FOO
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
            //context.startService(intent);
            mContext = context
        }
    }

}
