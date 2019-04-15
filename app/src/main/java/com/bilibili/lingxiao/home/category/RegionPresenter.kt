package com.bilibili.lingxiao.home.category

import com.bilibili.lingxiao.HttpTrans
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback

class RegionPresenter(view: RegionView, fragment: CategoryFragment) :
    BasePresenter<RegionView, CategoryFragment>(view, fragment) {
    private val httpTrans:HttpTrans by lazy {
        HttpTrans(fragment)
    }
    fun getRegion(){
        httpTrans.getCategory(object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetRegion(lists[0] as List<RegionData.Data>)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }

        })
    }
}