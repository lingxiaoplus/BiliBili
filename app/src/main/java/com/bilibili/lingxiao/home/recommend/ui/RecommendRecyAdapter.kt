package com.bilibili.lingxiao.home.recommend.ui

import android.net.Uri
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.recommend.RecommendData
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class RecommendRecyAdapter(layoutResId: Int, data: List<RecommendData>?) :
    BaseQuickAdapter<RecommendData, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: RecommendData) {
        helper.setText(R.id.play_title,item.title)
        helper.setText(R.id.play_number,""+item.play)
        helper.setText(R.id.comment_number,""+item.reply)
        helper.setText(R.id.category_name,"" + item.tname)
        var image : SimpleDraweeView = helper.getView(R.id.play_image)
        image.setImageURI(Uri.parse(item.cover))
    }
}
