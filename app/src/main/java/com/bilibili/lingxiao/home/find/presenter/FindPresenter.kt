package com.bilibili.lingxiao.home.find.presenter

import com.bilibili.lingxiao.HttpTrans
import com.bilibili.lingxiao.database.RegionTable
import com.bilibili.lingxiao.home.find.FindView
import com.bilibili.lingxiao.home.find.model.HotWordsData
import com.bilibili.lingxiao.home.find.ui.FindFragment
import com.bilibili.lingxiao.home.region.model.RegionData
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

    fun getRegion(){

        httpTrans.getRegion(object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                var list = lists[0] as List<RegionData.Data>
                /*var timeStamp = System.currentTimeMillis()
                list.forEach {
                    val region = RegionTable()
                    region.logo = it.logo
                    region.name = it.name
                    region.reid = it.reid
                    region.tid = it.tid
                    region.type = it.type
                    region.uri = it.uri
                    region.updateAt = timeStamp
                    region.save()
                }*/
                mView?.onGetRegion(list)
            }

            override fun onError(code: Int, desc: String?) {
                //mView?.showToast(desc)
            }

            override fun onCancel() {

            }

        })
    }
}