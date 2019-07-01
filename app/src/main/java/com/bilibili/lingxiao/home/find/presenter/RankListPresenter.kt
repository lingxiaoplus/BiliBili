package com.bilibili.lingxiao.home.find.presenter

import com.bilibili.lingxiao.HttpTrans
import com.bilibili.lingxiao.home.find.RankListView
import com.bilibili.lingxiao.home.find.model.RankListData
import com.bilibili.lingxiao.home.find.ui.RankListFragment
import com.bilibili.lingxiao.home.region.model.RegionData
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback

class RankListPresenter(view: RankListView, fragment: RankListFragment) :
    BasePresenter<RankListView, RankListFragment>(view, fragment) {
    private val httpTrans: HttpTrans by lazy {
        HttpTrans(fragment)
    }

    fun getOriginRankingList(type :String, page:Int,pageSize:Int){
        httpTrans.getOriginRankingList(type,page,pageSize,object : HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetRankList(lists[0] as MutableList<RankListData.Item>)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }
        })
    }

    fun getALlRegionRankingList(rid :Int, page:Int,pageSize:Int){
        httpTrans.getAllRegionRankingList(rid, page, pageSize,object : HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetRankList(lists[0] as MutableList<RankListData.Item>)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }
        })
    }
}