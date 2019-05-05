package com.bilibili.lingxiao.play

import com.bilibili.lingxiao.HttpTrans
import com.bilibili.lingxiao.home.recommend.view.RecommendView
import com.bilibili.lingxiao.play.model.CommentData
import com.bilibili.lingxiao.play.model.VideoDetailData
import com.bilibili.lingxiao.play.model.VideoRecoData
import com.camera.lingxiao.common.app.BaseFragment
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback

class VideoPresenter : BasePresenter<RecommendView, BaseFragment>{
    var httpTrans : HttpTrans

    constructor(view: RecommendView, fragment: BaseFragment):super(view, fragment){
        httpTrans = HttpTrans(fragment)
    }

    fun getDetailInfo(page:Int,id:String){
        httpTrans.getDetailInfo(page,id,object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetVideoDetail(lists[0] as VideoDetailData)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }

        })
    }

    fun getRecommendList(tid:Int,page:Int){
        httpTrans.getRecommendList(tid,page,30,"default",object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetVideoRecommend(lists[0] as VideoRecoData)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }

        })
    }

    /**
     * @param oid  视频av号
     */
    fun getComment(oid:String,next: Int){
        httpTrans.getComment(oid,next,object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                mView?.onGetVideoComment(lists[0] as CommentData)
            }

            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
            }

            override fun onCancel() {

            }

        })
    }

}