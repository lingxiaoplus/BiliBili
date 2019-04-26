package com.bilibili.lingxiao.home.live.presenter

import com.bilibili.lingxiao.HttpTrans
import com.bilibili.lingxiao.home.live.ui.play.FleetListFragment
import com.bilibili.lingxiao.home.live.model.FleetListData
import com.bilibili.lingxiao.home.live.view.LivePlayView
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback

class FleetPresenter(view: LivePlayView, fragment: FleetListFragment) :
    BasePresenter<LivePlayView, FleetListFragment>(view, fragment) {
    var httpTrans:HttpTrans
    init {
        httpTrans = HttpTrans(fragment)
    }

    fun getFleetList(page:Int,pageSize:Int,ruid:Int){
        httpTrans.getLiveFleetList(page,pageSize,ruid,object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetFleetList(lists[0] as FleetListData)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }

        })
    }
}