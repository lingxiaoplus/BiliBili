package com.bilibili.lingxiao.home.mikan

import com.bilibili.lingxiao.HttpTrans
import com.bilibili.lingxiao.home.mikan.model.MiKanFallData
import com.bilibili.lingxiao.home.mikan.model.MiKanRecommendData
import com.bilibili.lingxiao.home.mikan.ui.MikanFragment
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback

class MiKanPresenter(view: MikanView, fragment: MikanFragment) :
    BasePresenter<MikanView, MikanFragment>(view, fragment) {
    val httpTrans:HttpTrans by lazy { HttpTrans(fragment) }

    fun getBanGuMiRecommend(){
        httpTrans.getBanGumiRecommend(object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetMikanRecommend(lists[0] as MiKanRecommendData)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }

        })
    }


    fun getBanGuMiFall(cursor:Long){
        httpTrans.getBanGumiFall(cursor,object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetMikanFall(lists[0] as MiKanFallData)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }
        })
    }
}