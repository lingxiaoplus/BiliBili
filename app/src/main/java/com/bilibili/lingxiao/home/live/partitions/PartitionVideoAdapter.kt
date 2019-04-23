package com.bilibili.lingxiao.home.live.partitions

import android.net.Uri
import android.util.Log
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.LiveData
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
            image.setImageURI(Uri.parse(item.cover.src))
        }

        helper.setText(R.id.live_title,item.title)
        helper.setText(R.id.live_category_name,item.area)
        helper.setText(R.id.live_username,item.owner.name)
        helper.setText(R.id.live_people_number,getPeopleNumber(item.online))

        Log.d(TAG,"标题"+item.title + "类型" +item.area)
    }


    private fun getPeopleNumber(num:Int): String{
        if (num < 1000){
            return num.toString()
        }
        var float = num / 1000.0
        val b = BigDecimal(float)
        val f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).toFloat()  //表明四舍五入，保留两位小数
        return f1.toString() + "万"
    }


}