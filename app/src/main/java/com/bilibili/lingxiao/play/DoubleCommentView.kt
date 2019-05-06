package com.bilibili.lingxiao.play

import com.bilibili.lingxiao.play.model.CommentData

interface DoubleCommentView {
    fun onGetComment(reply: CommentData.Reply)
}