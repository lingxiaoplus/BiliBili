package com.bilibili.lingxiao.home.find

import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.utils.UIUtil
import com.bilibili.lingxiao.widget.LaybelLayout
import com.camera.lingxiao.common.app.BaseFragment
import kotlinx.android.synthetic.main.fragment_find.*
import kotlinx.android.synthetic.main.fragment_find.view.*

class FindFragment :BaseFragment(){
    override val contentLayoutId: Int
        get() = R.layout.fragment_find

    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }

    override fun onFirstVisiblity() {
        super.onFirstVisiblity()
        laybel.setAdapter(LaybelLayout.Adapter("hello","老番茄","大哥"))
    }
}