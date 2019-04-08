package com.bilibili.lingxiao.play

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.dagger.DaggerUiComponent
import com.bilibili.lingxiao.home.recommend.RecommendData
import com.bilibili.lingxiao.home.recommend.RecommendView
import com.bilibili.lingxiao.play.model.CommentData
import com.bilibili.lingxiao.play.model.VideoDetailData
import com.bilibili.lingxiao.play.model.VideoRecoData
import com.bilibili.lingxiao.utils.ToastUtil
import com.camera.lingxiao.common.app.BaseFragment
import com.camera.lingxiao.common.utills.LogUtils
import kotlinx.android.synthetic.main.fragment_comment.view.*

class CommentFragment :BaseFragment(),RecommendView{
    private var videoPresenter = VideoPresenter(this,this)
    private lateinit var mAdapter:CommentAdapter
    private var mCommentList = arrayListOf<CommentData.Reply>()
    override val contentLayoutId: Int
        get() = R.layout.fragment_comment

    override fun initInject() {
        super.initInject()
        DaggerUiComponent.builder().build().inject(this)
    }

    override fun initWidget(root: View) {
        super.initWidget(root)
        videoPresenter.getComment(9938411)

        var recommendManager = LinearLayoutManager(context)
        root.comment_recy.layoutManager = recommendManager
        mAdapter = CommentAdapter(R.layout.item_comment,mCommentList)
        root.comment_recy.adapter = mAdapter
    }

    override fun onGetRecommendData(recommendData: List<RecommendData>) {

    }

    override fun onGetVideoDetail(videoDetailData: VideoDetailData) {

    }

    override fun onGetVideoRecommend(videoRecoData: VideoRecoData) {

    }

    override fun onGetVideoComment(commentData: CommentData) {
        LogUtils.d("获取到评论："+commentData.toString())
        mAdapter.addData(commentData.replies)
    }

    override fun showDialog() {

    }

    override fun diamissDialog() {

    }

    override fun showToast(text: String?) {
        ToastUtil.show(text)
    }
}