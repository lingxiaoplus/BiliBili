package com.bilibili.lingxiao.home.live.presenter

import com.bilibili.lingxiao.HttpTrans
import com.bilibili.lingxiao.home.live.ui.play.FansDetailFragment
import com.bilibili.lingxiao.home.live.model.FansGoldListData
import com.bilibili.lingxiao.home.live.view.FansDetailView
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback

class FansDetailPresenter(view: FansDetailView, fragment: FansDetailFragment) :
    BasePresenter<FansDetailView, FansDetailFragment>(view, fragment) {
    var httpTrans:HttpTrans
    init {
        httpTrans = HttpTrans(fragment)
    }

    fun getFansGoldList(roomId:Int,ruid:Int){
        httpTrans.getLiveGoldList(roomId,ruid,object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetFansGoldList(lists[0] as FansGoldListData)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }

        })
    }

    fun getLiveToDayList(roomId:Int,ruid:Int){
        httpTrans.getLiveToDayList(roomId,ruid,object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetFansGoldList(lists[0] as FansGoldListData)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }

        })
    }

    fun getLiveFansList(roomId:Int,ruid:Int){
        httpTrans.getLiveFansList(roomId,ruid,object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetFansGoldList(lists[0] as FansGoldListData)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }
        })
    }
}