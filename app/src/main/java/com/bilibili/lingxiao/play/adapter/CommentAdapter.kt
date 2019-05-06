package com.bilibili.lingxiao.play.adapter

import android.net.Uri
import android.text.SpannableString
import android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
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
    private val replies by lazy {
        arrayOf(
            R.id.comment_hot1,
            R.id.comment_hot2,
            R.id.comment_hot3
        )
    }
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
                var level = item.member.levelInfo.currentLevel
                if (level > 6 || level < 0) level = 0
                levelImage.setImageResource(levelImages[level])

                var floor = "#" +item.floor + "  " + DateUtil.convertTimeToFormat(item.ctime)
                helper.setText(R.id.build_num,floor)

                if (data.size > 4 && helper.position == 4){
                    var segment:ImageView = helper.getView(R.id.segment)
                    segment.visibility = View.GONE
                }
                helper.addOnClickListener(R.id.more)
                    .addOnClickListener(R.id.ll_comment_replie)

                item.replies?.let {
                    helper.getView<LinearLayout>(R.id.ll_comment_replie).visibility = View.VISIBLE
                    var more = helper.getView<TextView>(R.id.comment_hot_more)
                    if (it.size > 3){
                        for ((index,id) in replies.withIndex()){
                            var name = it[index].member.uname + ": "
                            var message = it[index].content.message
                            helper
                                .setText(id,getColorText(name,message))
                                .setVisible(id,true)
                        }
                        more.visibility = View.VISIBLE
                        more.text = "共${it.size}条回复 >"
                    }else{
                        more.visibility = View.GONE
                        for ((index,reply) in it.withIndex()){
                            var name = reply.member.uname + ": "
                            var message = reply.content.message
                            helper
                                .setText(replies[index],getColorText(name,message))
                                .setVisible(replies[index],true)
                        }
                    }
                }
            }
            CommentData.Reply.SEGMENT ->{

            }
        }
    }

    fun getColorText(name:String,message:String) :SpannableString{
        var spannableString = SpannableString(name + message)
        var foregroundColorSpan = ForegroundColorSpan(mContext.resources.getColor(R.color.blue300))
        spannableString.setSpan(foregroundColorSpan,0,name.length,SPAN_INCLUSIVE_INCLUSIVE)
        return spannableString
    }
}