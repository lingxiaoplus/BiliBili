package com.bilibili.lingxiao.play

import android.net.Uri
import android.widget.ImageView
import com.bilibili.lingxiao.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class EndPageAdapter :BaseQuickAdapter<IntroduceFragment.EndPageData,BaseViewHolder>{
    constructor(layoutResId: Int, data: List<IntroduceFragment.EndPageData>):super(layoutResId,data)
    override fun convert(helper: BaseViewHolder, item: IntroduceFragment.EndPageData) {
        helper.setText(R.id.item_title,item.message)
        var image : ImageView = helper.getView(R.id.item_image)
        image.setImageDrawable(item.icon)
    }

}