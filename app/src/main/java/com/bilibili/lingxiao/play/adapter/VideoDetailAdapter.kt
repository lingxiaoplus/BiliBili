package com.bilibili.lingxiao.play.adapter

import android.net.Uri
import com.bilibili.lingxiao.GlobalProperties
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.play.model.VideoRecoData
import com.bilibili.lingxiao.utils.StringUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import java.net.URI

class VideoDetailAdapter : BaseQuickAdapter<VideoRecoData.VideoInfo, BaseViewHolder> {
    constructor(layoutResId: Int, data: List<VideoRecoData.VideoInfo>):super(layoutResId,data)
    override fun convert(helper: BaseViewHolder, item: VideoRecoData.VideoInfo) {
        helper.setText(R.id.title,item.title)
        helper.setText(R.id.up_name,item.author)
        helper.setText(R.id.play_num,StringUtil.getBigDecimalNumber(item.play))
        helper.setText(R.id.damku_num,StringUtil.getBigDecimalNumber(item.videoReview))
        var image : SimpleDraweeView = helper.getView(R.id.cover_image)
        image.setImageURI(Uri.parse(item.pic + GlobalProperties.IMAGE_RULE_240_150))
        helper.addOnClickListener(R.id.more)
    }
}