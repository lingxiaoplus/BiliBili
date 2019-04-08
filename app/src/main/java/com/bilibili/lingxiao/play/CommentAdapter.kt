package com.bilibili.lingxiao.play

import android.net.Uri
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.play.model.CommentData
import com.camera.lingxiao.common.utills.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import java.lang.Exception

class CommentAdapter : BaseQuickAdapter<CommentData.Reply, BaseViewHolder> {
    constructor(layoutResId: Int, data: List<CommentData.Reply>):super(layoutResId,data)
    override fun convert(helper: BaseViewHolder, item: CommentData.Reply) {
        var header:SimpleDraweeView = helper.getView(R.id.header)
        header.setImageURI(Uri.parse(item.member.avatar))
        helper.setText(R.id.username,item.member.uname)
        helper.setText(R.id.build_num,"#"+item.floor)
        helper.setText(R.id.comment_desc,item.content.message)
        helper.setText(R.id.recommend_num,""+item.like)
        LogUtils.e("头像转换："+Uri.parse(item.member.avatar))
    }
}