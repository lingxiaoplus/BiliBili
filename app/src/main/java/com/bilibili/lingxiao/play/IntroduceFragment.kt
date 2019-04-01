package com.bilibili.lingxiao.play

import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.dagger.DaggerUiComponent
import com.camera.lingxiao.common.app.BaseFragment

class IntroduceFragment :BaseFragment(){
    override val contentLayoutId: Int
        get() = R.layout.fragment_introduce

    override fun initInject() {
        super.initInject()
        DaggerUiComponent.builder().build().inject(this)
    }
    override fun initWidget(root: View) {
        super.initWidget(root)

    }
}