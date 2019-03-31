package com.camera.lingxiao.common.example

import android.util.Log
import com.camera.lingxiao.common.R
import com.camera.lingxiao.common.app.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity() , HttpView{
    var httpPresenter: HttpPresenter? = null;
    val TAG: String? = MainActivity::class.java.simpleName;
    override val contentLayoutId: Int
        get() = R.layout.activity_main

    override fun initWidget() {
        super.initWidget()
        httpPresenter = HttpPresenter(this,this)
        button_get.setOnClickListener{
            httpPresenter?.getResult()
        }
    }

    override fun showDialog() {

        showProgressDialog("请求中...")
    }

    override fun onGetResult(result: Array<HttpModle>?) {
        Log.e(TAG,"请求结果" + result!![0].desc)
        tv_msg.text = Arrays.toString(result)
    }

    override fun diamissDialog() {
        cancleProgressDialog()
    }

    override fun showToast(text: String?) {

    }
}
