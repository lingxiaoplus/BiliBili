package com.bilibili.lingxiao.home.live.presenter

import com.bilibili.lingxiao.HttpTrans
import com.bilibili.lingxiao.home.live.model.UpInfoData
import com.bilibili.lingxiao.home.live.ui.play.UpInfoFragment
import com.bilibili.lingxiao.home.live.view.LivePlayView
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback

class UpVideoPresenter(view: LivePlayView, fragment: UpInfoFragment) :
    BasePresenter<LivePlayView, UpInfoFragment>(view, fragment) {
    var httpTrans: HttpTrans
    init {
        httpTrans = HttpTrans(fragment)
    }

    fun getUpVideo(page:Int,pageSize:Int,ruid:Int){
        httpTrans.getLiveUpVideoList(page,pageSize,ruid,object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetUpVideoList(lists[0] as List<UpInfoData>)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {
            }

        })
    }
}