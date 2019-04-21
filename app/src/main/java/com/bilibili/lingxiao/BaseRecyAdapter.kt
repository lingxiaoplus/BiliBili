package com.bilibili.lingxiao

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

abstract class BaseRecyAdapter<T, K : BaseViewHolder>:
    BaseQuickAdapter<T, K> {
    constructor(data: List<T>?):super(data)
    constructor(layoutResId: Int):super(layoutResId)
    constructor(layoutResId: Int, data: List<T>?):super(layoutResId, data)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): K {
        return super.onCreateViewHolder(parent, viewType)
        setEmptyView(View.inflate(mContext,R.layout.layout_empty,null))
    }

}
