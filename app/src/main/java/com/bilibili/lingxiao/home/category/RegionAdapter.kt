package com.bilibili.lingxiao.home.category

import android.net.Uri
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.category.model.MultiRegionData
import com.bilibili.lingxiao.home.category.model.RegionRecommendData
import com.bilibili.lingxiao.utils.StringUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.facebook.drawee.view.SimpleDraweeView
import kotlin.properties.Delegates

class RegionAdapter :BaseQuickAdapter<MultiRegionData, BaseViewHolder> {
    var recycledViewPool: RecyclerView.RecycledViewPool by Delegates.notNull()
    constructor(data:MutableList<MultiRegionData>, recycledViewPool:RecyclerView.RecycledViewPool) :super(data){
        this.recycledViewPool = recycledViewPool
        multiTypeDelegate = object : MultiTypeDelegate<MultiRegionData>() {
            override fun getItemType(entity: MultiRegionData): Int {
                //根据你的实体类来判断布局类型
                return entity.itemType
            }
        }
        multiTypeDelegate
            .registerItemType(MultiRegionData.REGION_ITEM,R.layout.item_live_category)
            .registerItemType(MultiRegionData.REGION_RECOMMEND,R.layout.item_region)
    }
    override fun convert(helper: BaseViewHolder, item: MultiRegionData) {
        when(helper.itemViewType){
            MultiRegionData.REGION_ITEM ->{
                helper.setText(R.id.item_live_title,item.regionData?.name)
                var image :SimpleDraweeView = helper.getView(R.id.item_live_image)
                image.setImageURI(Uri.parse(item.regionData?.logo))
            }
            MultiRegionData.REGION_RECOMMEND ->{
                helper.setText(R.id.region_name,item.recommendData?.title)
                var button_more:Button = helper.getView(R.id.button_more)
                button_more.setText("更多" + item.recommendData?.title)
                var adapter = RegionRecommendAdapter(R.layout.item_video,item.recommendData?.body)
                var manager = GridLayoutManager(mContext, 2)
                var recycler:RecyclerView = helper.getView(R.id.recyclerview)
                recycler.isNestedScrollingEnabled = false
                recycler.setRecycledViewPool(recycledViewPool)
                recycler.layoutManager = manager
                recycler.adapter = adapter


                var image_region:ImageView = helper.getView(R.id.image_logo)
                var logo = UIUtil.getMipMapId(mContext,"ic_category_t" + item.recommendData?.param)
                if (logo > 0){
                    image_region.setImageResource(logo)
                }

            }
        }
    }


    inner class RegionRecommendAdapter(layout:Int,data: List<RegionRecommendData.Data.Body>?) :
        BaseQuickAdapter<RegionRecommendData.Data.Body, BaseViewHolder>(layout,data) {
        override fun convert(helper: BaseViewHolder, item: RegionRecommendData.Data.Body) {
            var image :SimpleDraweeView = helper.getView(R.id.play_image)
            image.setImageURI(Uri.parse(item.cover))
            helper.setText(R.id.play_title,item.title)
            helper.setText(R.id.play_number,StringUtil.getBigDecimalNumber(item.play))
            helper.setText(R.id.comment_number,StringUtil.getBigDecimalNumber(item.danmaku))
            helper.getView<ConstraintLayout>(R.id.cons_category).visibility = View.GONE
        }
    }
}
