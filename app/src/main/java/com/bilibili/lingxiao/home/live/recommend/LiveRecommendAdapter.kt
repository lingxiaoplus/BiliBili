package com.bilibili.lingxiao.home.live.recommend

import android.net.Uri
import android.util.Log
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.LiveData
import com.bilibili.lingxiao.utils.StringUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import java.math.BigDecimal


class LiveRecommendAdapter(layout:Int,data:List<LiveData.RecommendDataBean.LivesBean>)
    :BaseQuickAdapter<LiveData.RecommendDataBean.LivesBean,BaseViewHolder>(layout,data){
    val TAG = LiveRecommendAdapter::class.java.simpleName
    override fun convert(helper: BaseViewHolder, item: LiveData.RecommendDataBean.LivesBean) {
        var image:SimpleDraweeView = helper.getView(R.id.live_user_image)
        image.setImageURI(Uri.parse(item.cover?.src))
        helper.setText(R.id.live_title,item.title)
        helper.setText(R.id.live_category_name,item.area_v2_name)
        helper.setText(R.id.live_username,item.owner?.name)
        helper.setText(R.id.live_people_number,StringUtil.getBigDecimalNumber(item.online))

        //Log.d(TAG,"标题"+item.title + "类型" +item.area_v2_name)
    }

}