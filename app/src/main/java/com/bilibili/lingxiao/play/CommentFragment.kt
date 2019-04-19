package com.bilibili.lingxiao.play

import android.support.design.widget.TabLayout
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
import kotlinx.android.synthetic.main.fragment_comment.*
import kotlinx.android.synthetic.main.fragment_comment.view.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.w3c.dom.Comment

class CommentFragment :BaseFragment(),RecommendView{
    private var videoPresenter = VideoPresenter(this,this)
    private lateinit var mAdapter:CommentAdapter
    private var mCommentList = arrayListOf<CommentData.Reply>()

    private var page = 1 //评论页数
    private var avNum = ""

    override val contentLayoutId: Int
        get() = R.layout.fragment_comment

    override fun initInject() {
        super.initInject()
        DaggerUiComponent.builder().build().inject(this)
    }

    override fun initWidget(root: View) {
        super.initWidget(root)
        var recommendManager = LinearLayoutManager(context)
        root.comment_recy.layoutManager = recommendManager
        mAdapter = CommentAdapter(mCommentList)
        root.comment_recy.adapter = mAdapter

        root.refresh.setOnRefreshListener({
            mCommentList.clear()
            videoPresenter.getComment(avNum,1)
        })
        root.refresh.setOnLoadMoreListener({
            page++
            videoPresenter.getComment(avNum,page)
        })
    }

    override fun isRegisterEventBus(): Boolean {
        return true
    }

    /**
     * 粘性事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public fun onGetVideoDetail(data: RecommendData) {
        avNum = data.param
        videoPresenter.getComment(avNum,page)
    }

    override fun onGetRecommendData(recommendData: List<RecommendData>) {

    }

    override fun onGetVideoDetail(videoDetailData: VideoDetailData) {

    }

    override fun onGetVideoRecommend(videoRecoData: VideoRecoData) {

    }
    //var hotView = View.inflate(context,R.layout.item_hot_segment,null)
    override fun onGetVideoComment(commentData: CommentData) {
        LogUtils.d("获取到评论："+commentData.toString())
        if (commentData.hots.size > 0 && page == 1){
            for (hot in commentData.hots){
                mAdapter.addData(hot)
            }
            var empty = commentData.hots.get(0)
            var e = empty.copy(viewType = CommentData.Reply.SEGMENT)
            mAdapter.addData(e)
            var tabView:TabLayout = (activity as PlayActivity).findViewById(R.id.skin_tabLayout)
            var tabLayout = tabView.getTabAt(1)
            tabLayout?.text = "评论 " + commentData.page.count
        }
        if (commentData.replies == null){
            refresh.finishRefresh()
            refresh.finishLoadMore()
        }else{
            mAdapter.addData(commentData.replies)
        }
        refresh.finishRefresh()
        refresh.finishLoadMore()
    }

    override fun showDialog() {

    }

    override fun diamissDialog() {

    }

    override fun showToast(text: String?) {
        ToastUtil.show(text)
        refresh.finishRefresh()
        refresh.finishLoadMore()
    }
}