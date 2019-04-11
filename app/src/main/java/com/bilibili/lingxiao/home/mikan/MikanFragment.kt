package com.bilibili.lingxiao.home.mikan

import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseFragment

class MikanFragment :BaseFragment(){
    override val contentLayoutId: Int
        get() = R.layout.fragment_mikan

    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }
}