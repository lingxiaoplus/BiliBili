package com.bilibili.lingxiao.home.mikan.adapter

import android.net.Uri
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.mikan.model.MiKanRecommendData
import com.bilibili.lingxiao.utils.StringUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class MikanAdapter(layoutResId: Int, data: MutableList<MiKanRecommendData.Result.RecommendCn.Recommend>?) :
    BaseQuickAdapter<MiKanRecommendData.Result.RecommendCn.Recommend, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: MiKanRecommendData.Result.RecommendCn.Recommend) {
        var image:SimpleDraweeView = helper.getView(R.id.image_cover)
        image.setImageURI(Uri.parse(item.cover))
        helper.setText(R.id.text_num,StringUtil.getBigDecimalNumber(item.favourites.toInt())+"人追番")
        helper.setText(R.id.text_title,item.title)
        helper.setText(R.id.text_cover,"更新至第" + item.totalCount +"话")
    }
}