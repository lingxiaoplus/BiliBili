package com.bilibili.lingxiao.home.recommend.ui

import android.net.Uri
import android.widget.ImageView
import com.bilibili.lingxiao.BaseRecyAdapter
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.recommend.RecommendData
import com.bilibili.lingxiao.utils.StringUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import java.lang.Exception

class RecommendRecyAdapter(layoutResId: Int, data: List<RecommendData>?) :
    BaseQuickAdapter<RecommendData, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: RecommendData) {
        helper.setText(R.id.play_title,item.title)
        helper.setText(R.id.play_number,StringUtil.getBigDecimalNumber(item.play))
        helper.setText(R.id.comment_number,StringUtil.getBigDecimalNumber(item.reply))
        helper.setText(R.id.category_name,"" + item.tname)

        helper.addOnClickListener(R.id.image_more)
        var image : SimpleDraweeView = helper.getView(R.id.play_image)
        try {
            image.setImageURI(Uri.parse(item.cover))
        }catch (ex:Exception){
            ex.printStackTrace()
        }

    }
}
