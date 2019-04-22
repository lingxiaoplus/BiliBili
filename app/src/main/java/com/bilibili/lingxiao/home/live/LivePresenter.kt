package com.bilibili.lingxiao.home.live

import android.util.Log
import com.bilibili.lingxiao.GlobalProperties
import com.bilibili.lingxiao.HttpTrans
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback
import okhttp3.*
import okio.ByteString
import org.json.JSONObject
import java.util.*
import kotlin.concurrent.timerTask

class LivePresenter : BasePresenter<LiveView, LiveFragment> {

    private val TAG = LivePresenter::class.java.simpleName
    var liveTrans : HttpTrans
    //@Inject
    constructor(view: LiveView, fragment: LiveFragment):super(view, fragment){
        liveTrans = HttpTrans(fragment)
    }

    fun getLiveList(){
        liveTrans.getLiveList(object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetLiveList(lists[0] as LiveData)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }

        })
    }



}