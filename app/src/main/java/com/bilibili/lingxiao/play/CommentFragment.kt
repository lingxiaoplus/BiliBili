package com.bilibili.lingxiao.play

import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.dagger.DaggerUiComponent
import com.camera.lingxiao.common.app.BaseFragment

class CommentFragment :BaseFragment(){
    override val contentLayoutId: Int
        get() = R.layout.fragment_comment

    override fun initInject() {
        super.initInject()
        DaggerUiComponent.builder().build().inject(this)
    }
}