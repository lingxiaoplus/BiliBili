package com.camera.lingxiao.common

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import com.camera.lingxiao.common.exception.crash.CrashActivity
import com.tencent.bugly.Bugly
import com.tencent.bugly.crashreport.CrashReport

object Common {
    private val TAG = Common::class.java.simpleName
    /**
     * 当前皮肤的id
     */
    val SKIN_ID = "skin_id"

    private fun showSelect(urls: Array<String>, context: Context) {
        val builder = AlertDialog.Builder(context)
        val alertDialog = builder.create()
        alertDialog.setTitle("选择播放路径")
        builder.setItems(urls) { dialogInterface, i -> }
    }

    fun initCrash(context: Context,isDebug:Boolean){
        val strategy = CrashReport.UserStrategy(context)
        strategy.setCrashHandleCallback(object : CrashReport.CrashHandleCallback() {
            @Synchronized
            override fun onCrashHandleStart(
                crashType: Int,
                errorType: String?,
                errorMessage: String?,
                errorStack: String?
            ): Map<String, String> {
                val intent = Intent(context, CrashActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra("msg", errorStack)
                context.startActivity(intent)
                Log.e(TAG,"异常堆栈${errorStack}, 异常类型${errorType}, 异常内容${errorMessage}")
                return super.onCrashHandleStart(crashType, errorType, errorMessage, errorStack)
            }
        })
        Bugly.init(context,"180f968345",isDebug,strategy)
    }
}
