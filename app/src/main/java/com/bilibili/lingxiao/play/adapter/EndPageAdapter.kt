package com.bilibili.lingxiao.play.adapter

import android.widget.ImageView
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.play.ui.IntroduceFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class EndPageAdapter :BaseQuickAdapter<IntroduceFragment.EndPageData,BaseViewHolder>{
    constructor(layoutResId: Int, data: List<IntroduceFragment.EndPageData>):super(layoutResId,data)
    override fun convert(helper: BaseViewHolder, item: IntroduceFragment.EndPageData) {
        helper.setText(R.id.item_title,item.message)
        var image : ImageView = helper.getView(R.id.item_image)
        image.setImageDrawable(item.icon)
    }

}