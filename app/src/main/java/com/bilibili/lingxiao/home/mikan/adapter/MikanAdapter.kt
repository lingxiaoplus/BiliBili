package com.bilibili.lingxiao.home.mikan.adapter

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.mikan.model.MiKanFallData
import com.bilibili.lingxiao.home.mikan.model.MiKanRecommendData
import com.bilibili.lingxiao.home.mikan.model.MikanData
import com.bilibili.lingxiao.utils.StringUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.facebook.drawee.view.SimpleDraweeView

class MikanAdapter(data: MutableList<MikanData>?) :
    BaseQuickAdapter<MikanData, BaseViewHolder>(data) {

    init {
        multiTypeDelegate = object : MultiTypeDelegate<MikanData>() {
            override fun getItemType(entity: MikanData): Int {
                //根据你的实体类来判断布局类型
                return entity.type
            }
        }
        multiTypeDelegate
            .registerItemType(MikanData.LOGIN_HEADER,R.layout.mikan_header)
            .registerItemType(MikanData.TOP_BAR,R.layout.item_mikan_top_bar)
            .registerItemType(MikanData.BANGUMI_ITEM,R.layout.item_mikan_video)
            .registerItemType(MikanData.NEWS,R.layout.item_mikan_fall)
    }

    override fun convert(helper: BaseViewHolder, item: MikanData) {
        when(helper.itemViewType){
            MikanData.LOGIN_HEADER->{

            }
            MikanData.TOP_BAR->{
                helper.setText(R.id.text_title,item.titleBar.title)
                helper.setVisible(R.id.show_more,item.titleBar.showMore)
                var drawable:Drawable? = null
                when(item.titleBar.title){
                    mContext.resources.getString(R.string.bangumi_cn)->{
                        drawable = mContext.resources.getDrawable(R.drawable.bangumi_follow_home_ic_domestic)
                    }
                    mContext.resources.getString(R.string.bangumi_jp)->{
                        drawable = mContext.resources.getDrawable(R.drawable.bangumi_follow_home_ic_bangumi)
                    }
                    mContext.resources.getString(R.string.bangumi_edit)->
                        drawable = mContext.resources.getDrawable(R.drawable.bangumi_common_ic_editor_recommend)
                }
                if (drawable != null)
                    helper.setImageDrawable(R.id.imageview, drawable)
            }
            MikanData.BANGUMI_ITEM->{
                //中间的三个item
                var image:SimpleDraweeView = helper.getView(R.id.image_cover)
                image.setImageURI(Uri.parse(item.mikanRecommend.cover))
                helper.setText(R.id.text_num,StringUtil.getBigDecimalNumber(item.mikanRecommend.favourites.toInt())+"人追番")
                helper.setText(R.id.text_title,item.mikanRecommend.title)
                helper.setText(R.id.text_cover,"更新至第" + item.mikanRecommend.totalCount +"话")
                helper.getView<View>(R.id.mikan_video).setOnClickListener {
                    listener?.onRecommendClick(item.mikanRecommend)
                }
            }
            MikanData.NEWS->{
                //下面的news
                var image:SimpleDraweeView = helper.getView(R.id.mikan_image)
                image.setImageURI(Uri.parse(item.mikanFall.cover))
                helper.setText(R.id.title,item.mikanFall.title)
                helper.setText(R.id.content,item.mikanFall.desc)
                helper.getView<View>(R.id.mikan_foot).setOnClickListener {
                    listener?.onFootFallClick(item.mikanFall)
                }
            }
        }
    }
    private  var listener: OnMultiItemClickListener? = null
    fun setMultiItemClickListener(listener: OnMultiItemClickListener){
        this.listener = listener
    }
    interface OnMultiItemClickListener{
        fun onRecommendClick(data: MiKanRecommendData.Result.Recommend.Info)
        fun onFootFallClick(data: MiKanFallData.Result)
    }
}