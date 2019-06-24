package com.bilibili.lingxiao.home.find

import android.util.Log
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.utils.UIUtil
import com.bilibili.lingxiao.widget.LaybelLayout

import com.camera.lingxiao.common.app.BaseFragment
import kotlinx.android.synthetic.main.fragment_find.*
import kotlinx.android.synthetic.main.fragment_find.view.*

class FindFragment :BaseFragment(){
    private val TAG = FindFragment::class.java.simpleName
    override val contentLayoutId: Int
        get() = R.layout.fragment_find

    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }

    override fun initWidget(root: View) {
        super.initWidget(root)
        root.laybel.setAdapter(LaybelLayout.Adapter("hello","老番茄","大哥","fbvcbc","adda","fdgfdg","adsad",
            "hello","老番茄","大哥","fbvcbc","adda","fdgfdg","adsad"))
        root.show_more.setOnClickListener {
            root.laybel.startAnimation()
        }
    }
    override fun onFirstVisiblity() {
        super.onFirstVisiblity()
        Log.d(TAG,"子view的总个数${laybel.childCount}")
    }
}