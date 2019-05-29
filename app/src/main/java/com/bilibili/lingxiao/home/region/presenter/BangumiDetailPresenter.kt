package com.bilibili.lingxiao.home.region.presenter

import com.bilibili.lingxiao.HttpTrans
import com.bilibili.lingxiao.home.region.model.BangumiDetailData
import com.bilibili.lingxiao.home.region.model.BangumiRecommendData
import com.bilibili.lingxiao.home.region.ui.BangumiDetailActivity
import com.bilibili.lingxiao.home.region.view.BangumiView
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback

class BangumiDetailPresenter (view: BangumiView, activity :BangumiDetailActivity) :
    BasePresenter<BangumiView, BangumiDetailActivity>(view, activity) {
    private val httpTrans: HttpTrans by lazy {
        HttpTrans(activity)
    }
    fun getBangumiDetail(season_id:String,type:String){
        mView?.showDialog()
        httpTrans.getBangumiDetail(season_id,type,object : HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetBangumiDetail(lists[0] as BangumiDetailData)
                mView?.diamissDialog()
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
                mView?.diamissDialog()
            }

            override fun onCancel() {
                mView?.diamissDialog()
            }

        })
    }

    fun getBangumiRecommend(season_id:String){
        httpTrans.getBangumiDetailRecommend(season_id,object : HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetBangumiRecommend(lists[0] as BangumiRecommendData)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }
        })
    }
}