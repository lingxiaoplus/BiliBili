package com.bilibili.lingxiao.home.live.adapter

import android.net.Uri
import android.util.Log
import com.bilibili.lingxiao.GlobalProperties
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.model.LiveData
import com.bilibili.lingxiao.utils.StringUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import java.math.BigDecimal

class PartitionVideoAdapter(layoutResId: Int, data: MutableList<LiveData.RecommendDataBean.LivesBean>) :
    BaseQuickAdapter<LiveData.RecommendDataBean.LivesBean, BaseViewHolder>(layoutResId, data) {
    var isScroller = false
    override fun convert(helper: BaseViewHolder, item: LiveData.RecommendDataBean.LivesBean) {
        if (!isScroller){
            var image :SimpleDraweeView = helper.getView(R.id.live_user_image)
            image.setImageURI(Uri.parse(item.cover.src  + GlobalProperties.IMAGE_RULE_240_150))
        }

        helper.setText(R.id.live_title,item.title)
        helper.setText(R.id.live_category_name,item.area)
        helper.setText(R.id.live_username,item.owner.name)
        helper.setText(R.id.live_people_number,StringUtil.getBigDecimalNumber(item.online))

        Log.d(TAG,"标题"+item.title + "类型" +item.area)
    }
}