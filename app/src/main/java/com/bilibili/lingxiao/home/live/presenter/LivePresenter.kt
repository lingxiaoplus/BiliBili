package com.bilibili.lingxiao.home.live.presenter

import com.bilibili.lingxiao.HttpTrans
import com.bilibili.lingxiao.home.live.model.LiveData
import com.bilibili.lingxiao.home.live.ui.LiveFragment
import com.bilibili.lingxiao.home.live.view.LiveView
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback

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