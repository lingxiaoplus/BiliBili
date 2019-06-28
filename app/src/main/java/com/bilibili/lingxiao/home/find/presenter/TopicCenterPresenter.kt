package com.bilibili.lingxiao.home.find.presenter

import com.bilibili.lingxiao.HttpTrans
import com.bilibili.lingxiao.home.find.TopicView
import com.bilibili.lingxiao.home.find.model.TopicCardData
import com.bilibili.lingxiao.home.find.ui.TopicCenterActivity
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback

class TopicCenterPresenter(view: TopicView, activity: TopicCenterActivity) :
    BasePresenter<TopicView, TopicCenterActivity>(view, activity) {
    private val httpTrans: HttpTrans by lazy {
        HttpTrans(activity)
    }

    fun getTopicCenter(page:Int,pageSize:Int){
        httpTrans.getTopicCenter(page,pageSize,object : HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetTopicResult(lists[0] as TopicCardData)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }
        })
    }


    fun getActivityCenter(page:Int,pageSize:Int){
        httpTrans.getActivityCenter(page,pageSize,object : HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetTopicResult(lists[0] as TopicCardData)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }
        })
    }
}