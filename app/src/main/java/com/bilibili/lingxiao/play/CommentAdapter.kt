package com.bilibili.lingxiao.play

import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.play.model.CommentData
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentAdapter : BaseQuickAdapter<CommentData.Reply, BaseViewHolder> {
    constructor(layoutResId: Int, data: List<CommentData.Reply>):super(layoutResId,data)
    override fun convert(helper: BaseViewHolder, item: CommentData.Reply) {


        helper.setText(R.id.username,item.member.uname)
        helper.setText(R.id.build_num,"#"+item.floor)
        helper.setText(R.id.comment_desc,item.content.message)
        helper.setText(R.id.comment_number,""+item.like)
    }
}