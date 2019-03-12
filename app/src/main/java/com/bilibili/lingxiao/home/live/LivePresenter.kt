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