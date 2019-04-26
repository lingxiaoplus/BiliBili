package com.bilibili.lingxiao.home.live.adapter

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.model.LiveData
import com.camera.lingxiao.common.utills.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class PartitionAdapter(layout:Int,data: MutableList<LiveData.PartitionsBean>)
    :BaseQuickAdapter<LiveData.PartitionsBean,BaseViewHolder>(layout,data){
    override fun convert(helper: BaseViewHolder, item: LiveData.PartitionsBean) {
        helper.setText(R.id.live_category_name,item.partition?.name)
        LogUtils.d("LiveRecyAdapter 获取到Partition  name 的值-》》" + item.partition?.name)
        initRecy(helper, item.lives)
    }
    //var categoryAdapter :PartitionVideoAdapter? = null
    private fun initRecy(helper: BaseViewHolder, data: MutableList<LiveData.RecommendDataBean.LivesBean>) {
        var list = data
        if (list.size > 4){
            list = list.subList(0,4)
        }
        var categoryAdapter =
            PartitionVideoAdapter(R.layout.item_live_video, list)
        var manager = GridLayoutManager(mContext,2)
        val recyclerView:RecyclerView = helper.getView(R.id.live_partition_recy)
        recyclerView.adapter = categoryAdapter
        recyclerView.layoutManager = manager
        recyclerView.isNestedScrollingEnabled = false
    }
}