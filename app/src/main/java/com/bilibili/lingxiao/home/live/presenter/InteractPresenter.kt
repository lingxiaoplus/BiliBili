package com.bilibili.lingxiao.home.live.presenter

import com.bilibili.lingxiao.HttpTrans
import com.bilibili.lingxiao.home.live.model.LiveChatData
import com.bilibili.lingxiao.home.live.ui.play.InteractFragment
import com.bilibili.lingxiao.home.live.view.LivePlayView
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback

class InteractPresenter(view: LivePlayView, fragment: InteractFragment) :
    BasePresenter<LivePlayView, InteractFragment>(view, fragment) {
    var httpTrans:HttpTrans
    init {
        httpTrans = HttpTrans(fragment)
    }

    fun getChatHistory(roomId:Int){
        httpTrans.getHistoryChat(roomId,object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                var chatdata = lists[0] as LiveChatData
                mView?.onGetUpChatHistory(chatdata.room)
            }

            override fun onError(code: Int, desc: String?) {
               mView?.showToast(desc)
            }

            override fun onCancel() {

            }

        })
    }
}