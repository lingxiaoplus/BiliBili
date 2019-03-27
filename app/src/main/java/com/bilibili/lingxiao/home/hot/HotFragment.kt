package com.bilibili.lingxiao.home.hot

import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseFragment

class HotFragment :BaseFragment(){
    override val contentLayoutId: Int
        get() = R.layout.fragment_live
    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }

}