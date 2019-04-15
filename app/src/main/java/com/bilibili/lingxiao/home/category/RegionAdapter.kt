package com.bilibili.lingxiao.home.category

import android.net.Uri
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.LiveData
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class RegionAdapter(layoutResId: Int, data: List<RegionData.Data>?) :
    BaseQuickAdapter<RegionData.Data, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: RegionData.Data) {
        helper.setText(R.id.item_live_title,item.name)
        var image :SimpleDraweeView = helper.getView(R.id.item_live_image)
        image.setImageURI(Uri.parse(item.logo))
    }
}
