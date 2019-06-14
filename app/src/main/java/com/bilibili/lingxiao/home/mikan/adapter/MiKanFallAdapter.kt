package com.bilibili.lingxiao.home.mikan.adapter

import android.net.Uri
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.mikan.model.MiKanFallData
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class MiKanFallAdapter(layoutResId: Int, data: MutableList<MiKanFallData.Result>?) :
    BaseQuickAdapter<MiKanFallData.Result, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: MiKanFallData.Result) {
        var image:SimpleDraweeView = helper.getView(R.id.mikan_image)
        image.setImageURI(Uri.parse(item.cover))
        helper.setText(R.id.title,item.title)
        helper.setText(R.id.content,item.desc)
    }
}