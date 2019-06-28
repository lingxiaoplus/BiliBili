package com.bilibili.lingxiao.home.find.presenter

import com.bilibili.lingxiao.HttpTrans
import com.bilibili.lingxiao.home.find.FindView
import com.bilibili.lingxiao.home.find.model.SearchResultData
import com.bilibili.lingxiao.home.find.ui.SearchDetailActivity
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback

class SearchDetailPresenter(view: FindView, activity: SearchDetailActivity)
    : BasePresenter<FindView, SearchDetailActivity>(view, activity) {
    private val httpTrans: HttpTrans by lazy {
        HttpTrans(activity)
    }

    fun getSearchResult(word:String,page:Int,pageSize:Int){
        httpTrans.getSearchResult(word,page,pageSize,object : HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetSearchResult(lists[0] as SearchResultData)
            }

            override fun onError(code: Int, desc: String?) {

            }

            override fun onCancel() {

            }
        })
    }
}