package com.bilibili.lingxiao.home.live.presenter

import com.bilibili.lingxiao.HttpTrans
import com.bilibili.lingxiao.home.live.model.LiveUpData
import com.bilibili.lingxiao.home.live.model.LiveUserData
import com.bilibili.lingxiao.home.live.ui.LivePlayActivity
import com.bilibili.lingxiao.home.live.view.LivePlayView
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

    fun getUserInfo(ruid:Int,uid:Int){
        httpTrans.getLiveUserInfo(ruid,uid,object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetUserInfo(lists[0] as LiveUserData)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }

        })
    }
}