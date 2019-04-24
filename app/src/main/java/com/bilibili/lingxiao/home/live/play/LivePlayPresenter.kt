package com.bilibili.lingxiao.home.live.play

import com.bilibili.lingxiao.HttpTrans
import com.bilibili.lingxiao.home.live.model.LiveUpData
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback

class LivePlayPresenter(view: LivePlayView, activity: LivePlayActivity) :
    BasePresenter<LivePlayView, LivePlayActivity>(view, activity) {

    var httpTrans: HttpTrans
    init {
        httpTrans = HttpTrans(activity)
    }

    fun getUpInfo(roomId:Int){
        httpTrans.getLiveUpInfo(roomId,object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetUpInfo(lists[0] as LiveUpData)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)

            }

            override fun onCancel() {
            }

        })
    }
}