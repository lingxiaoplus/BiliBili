package com.bilibili.lingxiao.home.live.presenter

import com.bilibili.lingxiao.HttpTrans
import com.bilibili.lingxiao.home.live.model.LiveAllData
import com.bilibili.lingxiao.home.live.ui.LiveAllFragment
import com.bilibili.lingxiao.home.live.view.LiveAllView
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback

class LiveAllPresenter(view: LiveAllView, fragment: LiveAllFragment) :
    BasePresenter<LiveAllView,LiveAllFragment>(view,fragment){
    var httpTrans: HttpTrans
    init {
        httpTrans = HttpTrans(fragment)
    }

    fun getLiveAllList(page:Int,type:String,areaId:Int,parentAreaId:Int){
        httpTrans.getLiveAllList(page,30,type,areaId,parentAreaId, object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetHotList(lists[0] as LiveAllData)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {
            }
        })
    }
}