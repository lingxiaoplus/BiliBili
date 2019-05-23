package com.bilibili.lingxiao.home.region.presenter

import com.bilibili.lingxiao.HttpTrans
import com.bilibili.lingxiao.home.region.model.RegionDetailData
import com.bilibili.lingxiao.home.region.ui.RegionDetailFragment
import com.bilibili.lingxiao.home.region.view.RegionDetailView
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback

class RegionDetailPresenter(view: RegionDetailView, fragment: RegionDetailFragment) :
    BasePresenter<RegionDetailView, RegionDetailFragment>(view, fragment) {
    private val httpTrans: HttpTrans by lazy {
        HttpTrans(fragment)
    }

    fun getRegionDetail(tid:Int){
        httpTrans.getRegionList(tid,object : HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetRegionDetail(lists[0] as RegionDetailData)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }

        })
    }

    fun getRegionMore(rid:Int){
        httpTrans.getRegionMoreList(rid,object : HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetRegionMore(lists[0] as RegionDetailData)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }

        })
    }
}