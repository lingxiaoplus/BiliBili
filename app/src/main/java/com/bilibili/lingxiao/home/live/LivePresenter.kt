package com.bilibili.lingxiao.home.live

import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback
import javax.inject.Inject

class LivePresenter : BasePresenter<LiveView, LiveFragment> {

    var liveTrans :LiveTrans
    constructor(view: LiveView, fragment: LiveFragment):super(view, fragment){
        liveTrans = LiveTrans(fragment)
    }

    fun getLiveList(roomId :Int){
        liveTrans.getLiveList(roomId,object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                mView?.onGetLiveList()
            }

            override fun onError(code: Int, desc: String?) {

            }

            override fun onCancel() {

            }

        })
    }

}