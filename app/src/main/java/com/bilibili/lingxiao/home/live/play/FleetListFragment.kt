package com.bilibili.lingxiao.home.live.play

import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseFragment

class FleetListFragment :BaseFragment(){
    override val contentLayoutId: Int
        get() = R.layout.fragment_interact

    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }
}