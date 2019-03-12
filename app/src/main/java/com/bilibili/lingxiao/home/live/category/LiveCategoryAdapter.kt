package com.bilibili.lingxiao.home.live.category

import android.net.Uri
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.LiveData
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.item_live_category.view.*

class LiveCategoryAdapter(layoutResId: Int, data: List<LiveData.EntranceIconsBean>?) :
    BaseQuickAdapter<LiveData.EntranceIconsBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: LiveData.EntranceIconsBean) {
        helper.setText(R.id.item_live_title,item.name)
        var image:SimpleDraweeView = helper.getView(R.id.item_live_image)
        image.setImageURI(Uri.parse(item.entrance_icon.src))
    }
}
