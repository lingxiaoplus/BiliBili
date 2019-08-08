package com.bilibili.lingxiao.home.live.ui

import com.bilibili.lingxiao.R
import com.camera.lingxiao.common.app.BaseActivity
import kotlinx.android.synthetic.main.activity_stream_home.*


class StreamHomeActivity : BaseActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_stream_home

    override fun initWidget() {
        super.initWidget()
        setToolbarBack(toolbar)
        toolbar.title = resources.getString(R.string.not_idcard_approve)
    }
}
