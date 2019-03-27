package com.bilibili.lingxiao.home.recommend

import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback

class RecommendPresenter :BasePresenter<RecommendView,RecommendFragment>{
    var liveTrans : RecommendTrans
    constructor(view: RecommendView, fragment: RecommendFragment):super(view, fragment){
        liveTrans = RecommendTrans(fragment)
    }


    fun getRecommendList(operationState:Int){
        liveTrans.getRecommendList(operationState,object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetRecommendData(lists[0] as RecommendData)
            }

            override fun onError(code: Int, desc: String?) {

            }

            override fun onCancel() {

            }

        })
    }
}