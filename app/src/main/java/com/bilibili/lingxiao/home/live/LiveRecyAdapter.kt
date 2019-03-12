package com.bilibili.lingxiao.home.live

import android.net.Uri
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.banner.BannerImageLoader
import com.bilibili.lingxiao.home.live.category.LiveCategoryAdapter
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer

class LiveRecyAdapter : BaseQuickAdapter<LiveData.EntranceIconsBean, BaseViewHolder> {
    constructor(layout:Int,data: List<LiveData.EntranceIconsBean>?):super(layout,data)
    override fun convert(helper: BaseViewHolder, item: LiveData.EntranceIconsBean) {
        helper.setText(R.id.item_live_title,item.name)
        var image:SimpleDraweeView = helper.getView(R.id.item_live_image)
        image.setImageURI(Uri.parse(item.entrance_icon.src))
        Log.d("LiveRecyAdapter","地址："+item.entrance_icon.src)
    }

}