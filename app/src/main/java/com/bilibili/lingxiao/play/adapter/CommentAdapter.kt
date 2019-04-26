package com.bilibili.lingxiao.play.adapter

import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.play.model.CommentData
import com.bilibili.lingxiao.utils.DateUtil
import com.bilibili.lingxiao.utils.StringUtil
import com.camera.lingxiao.common.utills.LogUtils
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import java.lang.Exception

class CommentAdapter : BaseMultiItemQuickAdapter<CommentData.Reply, BaseViewHolder> {
    private val levelImages = arrayOf(
        R.drawable.mall_mine_vip_level_0,
        R.drawable.mall_mine_vip_level_1,
        R.drawable.mall_mine_vip_level_2,
        R.drawable.mall_mine_vip_level_3,
        R.drawable.mall_mine_vip_level_4,
        R.drawable.mall_mine_vip_level_5,
        R.drawable.mall_mine_vip_level_6
    )
    constructor(data: List<CommentData.Reply>):super(data){
        addItemType(CommentData.Reply.REPLIE,R.layout.item_comment)
        addItemType(CommentData.Reply.SEGMENT,R.layout.item_hot_segment)
    }
    override fun convert(helper: BaseViewHolder, item: CommentData.Reply) {
        when(helper.itemViewType){
            CommentData.Reply.REPLIE ->{
                var header:SimpleDraweeView = helper.getView(R.id.header)
                header.setImageURI(Uri.parse(item.member.avatar))
                helper.setText(R.id.username,item.member.uname)

                helper.setText(R.id.comment_desc,item.content.message)
                helper.setText(R.id.recommend_num,StringUtil.getBigDecimalNumber(item.like))
                var levelImage:SimpleDraweeView = helper.getView(R.id.image_level)
                var level = item.member.level_info.current_level
                if (level > 6 || level < 0) level = 0
                levelImage.setImageResource(levelImages[level])

                var floor = "#" +item.floor + "  " + DateUtil.convertTimeToFormat(item.ctime)
                helper.setText(R.id.build_num,floor)

                if (data.size > 4 && helper.position == 4){
                    var segment:ImageView = helper.getView(R.id.segment)
                    segment.visibility = View.GONE
                }
                helper.addOnClickListener(R.id.more)
            }
            CommentData.Reply.SEGMENT ->{

            }
        }

    }
}