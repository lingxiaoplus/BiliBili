package com.bilibili.lingxiao.home.find

import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseFragment

class FindFragment :BaseFragment(){
    override val contentLayoutId: Int
        get() = R.layout.fragment_find

    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }


}