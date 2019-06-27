package com.bilibili.lingxiao.home.find

import com.bilibili.lingxiao.HttpTrans
import com.bilibili.lingxiao.home.find.model.HotWordsData
import com.bilibili.lingxiao.home.find.ui.FindFragment
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback

class FindPresenter(view: FindView, fragment: FindFragment)
    :BasePresenter<FindView, FindFragment>(view, fragment) {
    private val httpTrans: HttpTrans by lazy {
        HttpTrans(fragment)
    }
    fun getHotWords(limit :Int){
        httpTrans.getHotSearchWords(limit,object : HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetHotWords(lists[0] as HotWordsData)
            }

            override fun onError(code: Int, desc: String?) {

            }

            override fun onCancel() {

            }
        })
    }
}