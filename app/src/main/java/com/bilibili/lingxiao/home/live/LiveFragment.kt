package com.bilibili.lingxiao.home.live

import android.view.View
import com.bilibili.lingxiao.R
import com.camera.lingxiao.common.app.BaseFragment
import javax.inject.Inject

class LiveFragment :BaseFragment() ,LiveView{

    var livePresenter: LivePresenter = LivePresenter(this,this)
    override val contentLayoutId: Int
        get() = R.layout.fragment_live

    override fun initWidget(root: View) {
        super.initWidget(root)
        livePresenter.getLiveList(1)
    }


    override fun onGetLiveList() {

    }

    override fun showDialog() {

    }

    override fun diamissDialog() {

    }

    override fun showToast(text: String?) {

    }
}