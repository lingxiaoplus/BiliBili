package com.bilibili.lingxiao.home.region

import android.net.Uri
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import com.bilibili.lingxiao.GlobalProperties
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.region.model.MultiRegionData
import com.bilibili.lingxiao.home.region.model.RegionData
import com.bilibili.lingxiao.home.region.model.RegionRecommendData
import com.bilibili.lingxiao.utils.StringUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.facebook.drawee.view.SimpleDraweeView
import java.util.*
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
            .registerItemType(MultiRegionData.REGION_RECOMMEND,R.layout.item_video)
            .registerItemType(MultiRegionData.REGION_TOP_BAR,R.layout.item_region_top_bar)
            .registerItemType(MultiRegionData.REGION_BOTTOM_BAR,R.layout.item_region_bottom_bar)
    }

    override fun convert(helper: BaseViewHolder, item: MultiRegionData) {
        when(helper.itemViewType){
            MultiRegionData.REGION_ITEM ->{
                helper.setText(R.id.item_live_title,item.regionData?.name)
                var image :SimpleDraweeView = helper.getView(R.id.item_live_image)
                image.setImageURI(Uri.parse(item.regionData?.logo + GlobalProperties.IMAGE_RULE_90_90))
                //helper.addOnClickListener(R.id.item_live_image)
                this.setOnItemClickListener { adapter, view, position ->
                    listener?.onGridClick(item.regionData,position)
                }
            }
            MultiRegionData.REGION_TOP_BAR ->{
                helper.setText(R.id.region_name,item.recommendData?.title)
                var image_region:ImageView = helper.getView(R.id.image_logo)
                var logo = UIUtil.getMipMapId(mContext,"ic_category_t${item.recommendData?.param}")
                if (logo > 0){
                    image_region.setImageResource(logo)
                }
                helper.addOnClickListener(R.id.button_goto)
            }
            MultiRegionData.REGION_RECOMMEND ->{
                var image :SimpleDraweeView = helper.getView(R.id.play_image)
                image.setImageURI(Uri.parse(item.bangumiItemData.cover + GlobalProperties.IMAGE_RULE_240_150))
                helper.setText(R.id.play_title,item.bangumiItemData.title)
                helper.setText(R.id.play_number,StringUtil.getBigDecimalNumber(item.bangumiItemData.play))
                helper.setText(R.id.comment_number,StringUtil.getBigDecimalNumber(item.bangumiItemData.danmaku))
                helper.getView<ConstraintLayout>(R.id.cons_category).visibility = View.GONE
                helper.getView<LinearLayout>(R.id.ll_info).visibility = View.GONE
                helper.getView<View>(R.id.item_video).setOnClickListener {
                    listener?.onVideoClick(item.bangumiItemData,"bangumi")
                }
            }
            MultiRegionData.REGION_BOTTOM_BAR ->{
                helper.setText(R.id.text_new_number,"${Random().nextInt(2000)}条新动态，点击刷新！")
                var button_more:Button = helper.getView(R.id.button_more)
                button_more.setText("更多${item.recommendData?.title}")
                helper.addOnClickListener(R.id.button_more)
                helper.getView<View>(R.id.ll_refresh).setOnClickListener {
                    listener?.onRefreshClick(helper,item.recommendData,helper.position)
                }
            }
        }
    }



    inner class RegionRecommendAdapter(layout:Int,data: List<RegionRecommendData.Data.Body>?) :
        BaseQuickAdapter<RegionRecommendData.Data.Body, BaseViewHolder>(layout,data) {
        override fun convert(helper: BaseViewHolder, item: RegionRecommendData.Data.Body) {
            var image :SimpleDraweeView = helper.getView(R.id.play_image)
            image.setImageURI(Uri.parse(item.cover + GlobalProperties.IMAGE_RULE_240_150))
            helper.setText(R.id.play_title,item.title)
            helper.setText(R.id.play_number,StringUtil.getBigDecimalNumber(item.play))
            helper.setText(R.id.comment_number,StringUtil.getBigDecimalNumber(item.danmaku))
            helper.getView<ConstraintLayout>(R.id.cons_category).visibility = View.GONE
            helper.getView<LinearLayout>(R.id.ll_info).visibility = View.GONE
        }
    }

    private  var listener: OnMultiItemClickListener? = null
    fun setMultiItemClickListener(listener: OnMultiItemClickListener){
        this.listener = listener
    }
    interface OnMultiItemClickListener{
        fun onGridClick(data: RegionData.Data?, position:Int)
        fun onVideoClick(data: RegionRecommendData.Data.Body?,type:String)
        fun onRefreshClick(data: BaseViewHolder, recommendData: RegionRecommendData.Data?,position:Int)
    }
}
