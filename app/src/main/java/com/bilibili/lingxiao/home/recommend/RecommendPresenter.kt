package com.bilibili.lingxiao.home.recommend

import com.bilibili.lingxiao.home.recommend.model.RecommendData
import com.bilibili.lingxiao.home.recommend.ui.RecommendFragment
import com.bilibili.lingxiao.home.recommend.view.RecommendView
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback
import java.lang.ClassCastException
import java.lang.NullPointerException

class RecommendPresenter :BasePresenter<RecommendView, RecommendFragment>{
    var liveTrans : RecommendTrans
    constructor(view: RecommendView, fragment: RecommendFragment):super(view, fragment){
        liveTrans = RecommendTrans(fragment)
    }

    fun getRecommendList(operationState :Int){

        liveTrans.getRecommendList(operationState,object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                val lists = res as Array<*>
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