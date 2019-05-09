package com.bilibili.lingxiao.play.ui

import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.dagger.DaggerUiComponent
import com.bilibili.lingxiao.home.recommend.model.RecommendData
import com.bilibili.lingxiao.home.recommend.view.RecommendView
import com.bilibili.lingxiao.play.adapter.CommentAdapter
import com.bilibili.lingxiao.play.VideoPresenter
import com.bilibili.lingxiao.play.model.CommentData
import com.bilibili.lingxiao.play.model.VideoDetailData
import com.bilibili.lingxiao.play.model.VideoRecoData
import com.bilibili.lingxiao.utils.ToastUtil
import com.camera.lingxiao.common.app.BaseFragment

import com.camera.lingxiao.common.utills.PopwindowUtil
import kotlinx.android.synthetic.main.fragment_comment.*
import kotlinx.android.synthetic.main.fragment_comment.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class CommentFragment :BaseFragment(), RecommendView {
    private var videoPresenter = VideoPresenter(this, this)
    private lateinit var mAdapter: CommentAdapter
    private var mCommentList = arrayListOf<CommentData.Reply>()

    //@Inject
    lateinit var  commentDetailFragment: CommentDetailFragment
    private var next = 0 //评论游标
    //private var allCount = 0 //评论的总共楼层
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
            videoPresenter.getComment(avNum,0)
        })
        root.refresh.setOnLoadMoreListener({
            videoPresenter.getComment(avNum,next)
        })
        var emptyView = View.inflate(context,R.layout.layout_empty,null)
        var image = emptyView.findViewById<ImageView>(R.id.image_error)
        image.setImageDrawable(resources.getDrawable(R.drawable.bilipay_common_error_tip))
        mAdapter.setEmptyView(emptyView)

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.more ->{
                    val popwindowUtil = PopwindowUtil.PopupWindowBuilder(activity!!)
                        .setView(R.layout.pop_comment)
                        .setFocusable(true)
                        .setTouchable(true)
                        .setOutsideTouchable(true)
                        .create()
                    popwindowUtil.showAsDropDown(view,0,-view.getHeight());
                    popwindowUtil.getView<View>(R.id.pop_add_blacklist)!!.setOnClickListener {
                        popwindowUtil.dissmiss()
                    }
                    popwindowUtil.getView<View>(R.id.pop_report)!!.setOnClickListener {
                            v -> popwindowUtil.dissmiss()
                    }
                }
                R.id.ll_comment_replie ->{
                    var act = activity as PlayActivity
                    //act.showCommentDetail("" + mCommentList[position].oid)
                    commentDetailFragment = CommentDetailFragment()
                    EventBus.getDefault().postSticky(mCommentList[position])
                    commentDetailFragment.height = act.findViewById<RelativeLayout>(R.id.rl_video_detail).height
                    commentDetailFragment.show(childFragmentManager,"" + mCommentList[position].oid)
                }
            }
        }
       
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
        refresh.autoRefresh(50)  //延迟获取评论数据
        //videoPresenter.getComment(avNum,next)
    }

    override fun onGetRecommendData(recommendData: List<RecommendData>) {

    }

    override fun onGetVideoDetail(videoDetailData: VideoDetailData) {

    }

    override fun onGetVideoRecommend(videoRecoData: VideoRecoData) {

    }
    //var hotView = View.inflate(context,R.layout.item_hot_segment,null)
    override fun onGetVideoComment(commentData: CommentData) {
        //LogUtils.d("获取到评论："+commentData.toString())
        next = commentData.cursor.next
        if (next == 1){ //说明评论已经到底了
            refresh.finishLoadMoreWithNoMoreData()
            mAdapter.loadMoreEnd()
        }
        commentData.hots?.let {
            for (hot in it){
                mAdapter.addData(hot)
            }
            //随便放一条数据，用于显示热门评论
            var empty = it.get(0)
            var e = empty.copy(viewType = CommentData.Reply.SEGMENT)
            mAdapter.hotSegmentPosition = it.size -1
            mAdapter.addData(e)
            var tabView:TabLayout = (activity as PlayActivity).findViewById(R.id.skin_tabLayout)
            var tabLayout = tabView.getTabAt(1)
            tabLayout?.text = "评论 ${commentData.cursor.allCount}"

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