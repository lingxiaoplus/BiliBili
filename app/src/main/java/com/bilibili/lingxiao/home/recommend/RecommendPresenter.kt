package com.bilibili.lingxiao.home.recommend

import com.bilibili.lingxiao.home.recommend.ui.RecommendFragment
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback
import com.camera.lingxiao.common.utills.LogUtils

class RecommendPresenter :BasePresenter<RecommendView, RecommendFragment>{
    var liveTrans : RecommendTrans
    constructor(view: RecommendView, fragment: RecommendFragment):super(view, fragment){
        liveTrans = RecommendTrans(fragment)
    }


    fun getRecommendList(operationState:Int){
        liveTrans.getRecommendList(operationState,object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetRecommendData(lists[0] as List<RecommendData>)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }

        })
    }
}