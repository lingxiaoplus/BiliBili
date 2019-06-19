package com.bilibili.lingxiao.home.dynamic

import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseFragment

class DynamicFragment :BaseFragment(){
    override val contentLayoutId: Int
        get() = R.layout.fragment_dynamic

    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }

    override fun initWidget(root: View) {
        super.initWidget(root)
    }
}