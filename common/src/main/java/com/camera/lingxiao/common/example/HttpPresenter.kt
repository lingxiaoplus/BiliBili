package com.camera.lingxiao.common.example

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.observer.HttpRxCallback
import com.trello.rxlifecycle2.LifecycleProvider
import java.util.*

class HttpPresenter(view: HttpView, activity: MainActivity): BasePresenter<HttpView,MainActivity>(view,activity){
    var httpTrans: HttpTrans? = null;
    companion object {
        val TAG: String? = MainActivity::class.java.simpleName;
    }
    init {
        httpTrans = HttpTrans(this.mActivity!!)
    }

    fun getResult(){
        mView?.showDialog()
        httpTrans?.getResult(object : HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                var lists = res as Array<*>
                var list: Array<HttpModle>? = lists[0] as Array<HttpModle>?
                mView?.onGetResult(list)
                mView?.diamissDialog()
            }
            override fun onError(code: Int, desc: String?) {
                mView?.showToast(desc)
                mView?.diamissDialog()
                Log.e(TAG,"请求失败" + desc + " 错误码： " + code)
            }
            override fun onCancel() {

            }
        })
    }
}