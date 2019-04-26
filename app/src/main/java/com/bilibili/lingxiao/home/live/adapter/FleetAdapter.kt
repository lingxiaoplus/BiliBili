package com.bilibili.lingxiao.home.live.adapter

import android.net.Uri
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.model.FleetListData
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class FleetAdapter (layoutResId: Int, data: MutableList<FleetListData.UserInfo>?) :
    BaseQuickAdapter<FleetListData.UserInfo, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: FleetListData.UserInfo) {
        var image_header = helper.getView<SimpleDraweeView>(R.id.image_header)
        image_header.setImageURI(Uri.parse(item.face))
        helper.setText(R.id.username,item.username)

    }
}