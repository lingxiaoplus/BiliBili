package com.bilibili.lingxiao.home.live.presenter

import com.bilibili.lingxiao.HttpTrans
import com.bilibili.lingxiao.home.live.model.LiveTabData
import com.bilibili.lingxiao.home.live.ui.LiveMoreActivity
import com.bilibili.lingxiao.home.live.view.LiveTabView
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback

class LiveTabPresenter(view: LiveTabView, fragment: LiveMoreActivity) :
    BasePresenter<LiveTabView, LiveMoreActivity>(view, fragment) {
    var httpTrans: HttpTrans
    init {
        httpTrans = HttpTrans(fragment)
    }

    fun getTabList(parentId:Int){
        httpTrans.getLiveTabList(parentId,object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetTabList(lists[0] as List<LiveTabData.Tab>)
            }
            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }
            override fun onCancel() {

            }
        })
    }
}