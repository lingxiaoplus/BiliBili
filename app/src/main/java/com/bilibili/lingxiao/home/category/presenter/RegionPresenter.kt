package com.bilibili.lingxiao.home.category.presenter

import com.bilibili.lingxiao.HttpTrans
import com.bilibili.lingxiao.home.category.model.RegionData
import com.bilibili.lingxiao.home.category.model.RegionRecommendData
import com.bilibili.lingxiao.home.category.ui.CategoryFragment
import com.bilibili.lingxiao.home.category.view.RegionView
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback

class RegionPresenter(view: RegionView, fragment: CategoryFragment) :
    BasePresenter<RegionView, CategoryFragment>(view, fragment) {
    private val httpTrans:HttpTrans by lazy {
        HttpTrans(fragment)
    }
    fun getRegion(){
        httpTrans.getRegion(object :HttpRxCallback<Any>(){
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

    fun getRegionRecommend(){
        httpTrans.getRegionRecommend(object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetRegionRecommend(lists[0] as List<RegionRecommendData.Data>)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }
        })
    }
}