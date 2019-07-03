package com.bilibili.lingxiao.home.recommend.ui

import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.BannerImageLoader
import com.bilibili.lingxiao.home.recommend.model.RecommendData
import com.bilibili.lingxiao.home.recommend.RecommendPresenter
import com.bilibili.lingxiao.home.recommend.view.RecommendView
import com.bilibili.lingxiao.play.model.CommentData
import com.bilibili.lingxiao.play.model.VideoDetailData
import com.bilibili.lingxiao.play.model.VideoRecoData
import com.bilibili.lingxiao.utils.ToastUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_recommend.*
import kotlinx.android.synthetic.main.fragment_recommend.view.*
import org.greenrobot.eventbus.EventBus
import kotlin.properties.Delegates
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.LinearLayout
import com.bilibili.lingxiao.web.WebActivity
import com.youth.banner.listener.OnBannerListener
import android.animation.Animator
import com.bilibili.lingxiao.GlobalProperties
import com.camera.lingxiao.common.utills.PopwindowUtil
import com.camera.lingxiao.common.utills.SpUtils
import kotlin.collections.ArrayList


class RecommendFragment :BaseFragment(), RecommendView {

    private var recommendPresenter = RecommendPresenter(this, this)
    private var mRecommendList: List<RecommendData> = arrayListOf()
    private var mAdapter:RecommendRecyAdapter by Delegates.notNull()
    private var banner:Banner by Delegates.notNull()
    private var operationState = 1  //1表示首次请求数据，有轮播图返回，2代表下拉刷新  3代表上拉加载更多
    override val contentLayoutId: Int
        get() = R.layout.fragment_recommend

    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }

    override fun initWidget(root: View) {
        super.initWidget(root)
        mAdapter = RecommendRecyAdapter(R.layout.item_video,mRecommendList)
        var view = View.inflate(context,R.layout.layout_banner,null)
        banner = view.findViewById(R.id.live_banner)
        mAdapter.addHeaderView(view)
        var colum = SpUtils.getInt(activity, GlobalProperties.HOME_COLUMNS,2)
        var manager = GridLayoutManager(context,colum)
        if (colum == 2){
            manager.setSpanSizeLookup(object :GridLayoutManager.SpanSizeLookup(){
                override fun getSpanSize(position: Int): Int {
                    if(position == 0){
                        return 2
                    }else{
                        return 1
                    }
                }
            })
        }

        root.recycerView.adapter = mAdapter
        root.recycerView.layoutManager = manager
        //root.recycerView.showShimmerAdapter()
        //root.recycerView.isNestedScrollingEnabled = false

        root.refresh.setOnRefreshListener {
            recommendPresenter.getRecommendList(operationState)
        }
        root.refresh.setOnLoadMoreListener {
            operationState = 3
            recommendPresenter.getRecommendList(operationState)
        }
        mAdapter.setDuration(1000)
        mAdapter.openLoadAnimation({ view ->
            arrayOf<Animator>(
                ObjectAnimator.ofFloat(
                    view,
                    "scaleY",
                    0f,
                    1.05f,
                    1f
                ), ObjectAnimator.ofFloat(view, "scaleX", 0f, 1.05f, 1f)
            )
        })
        mAdapter.isFirstOnly(false)
        mAdapter.setOnItemClickListener(object :BaseQuickAdapter.OnItemClickListener{
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                //val intent = Intent(context,PlayActivity::class.java)
                mRecommendList.get(position).let {
                    EventBus.getDefault().postSticky(it)
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(it.uri)
                    )
                    intent.putExtra("play_url",it.uri)
                    startActivity(intent)
                }

            }
        })
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.image_more-> showPopupWindow(mRecommendList.get(position))
            }
        }
        mAdapter.setOnItemLongClickListener { adapter, view, position ->
            showPopupWindow(mRecommendList.get(position))
            true
        }
        mAdapter.setEmptyView(View.inflate(context,R.layout.layout_empty,null))
    }

    override fun onFirstVisiblity() {
        super.onFirstVisiblity()
        refresh.autoRefresh()
    }

    override fun onVisiblityChanged(visiblity: Boolean) {
        super.onVisiblityChanged(visiblity)
        if (visiblity && mAdapter.itemCount - mAdapter.headerLayoutCount - mAdapter.footerLayoutCount < 1){
            refresh.autoRefresh()
        }
    }

    override fun onGetRecommendData(recommendData: List<RecommendData>) {
        if (operationState == 1){
            var banner = recommendData.get(0)
            for (data in recommendData.subList(1,recommendData.size)){
                mAdapter.addData(data)
            }
            initBanner(banner.banner_item)
            operationState = 2
        }else if (operationState == 2){
            mAdapter.setNewData(recommendData)
        }else{
            for (data in recommendData){
                if (data.title != null){
                    mAdapter.addData(data)
                }
            }
        }
        refresh.finishRefresh()
        refresh.finishLoadMore()
        //recycerView.hideShimmerAdapter()
    }
    override fun onGetVideoDetail(videoDetailData: VideoDetailData) {

    }
    override fun onGetVideoRecommend(videoRecoData: VideoRecoData) {

    }
    override fun onGetVideoComment(commentData: CommentData) {

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

    private fun initBanner(bannerData: List<RecommendData.BannerItem>) {
        var images = ArrayList<String>()
        for (image in bannerData){
            images?.add(image.image)
        }
        banner.setImageLoader(BannerImageLoader())
        //设置图片集合
        banner.setImages(images)
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage)
        //设置标题集合（当banner样式有显示title时）
        //live_banner.setBannerTitles(banner.get(0).title);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true)
        //设置轮播时间
        banner.setDelayTime(3000)
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER)
        //banner设置方法全部调用完毕时最后调用
        banner.start()

        banner.setOnBannerListener(object :OnBannerListener{
            override fun OnBannerClick(position: Int) {
                var intent = Intent(context,WebActivity::class.java)
                intent.putExtra("uri",bannerData[position].uri)
                intent.putExtra("title",bannerData[position].title)
                intent.putExtra("image",bannerData[position].image)
                startActivity(intent)
            }

        })
    }

    private fun showPopupWindow(data : RecommendData) {
        val popwindowUtil = PopwindowUtil.PopupWindowBuilder(activity!!)
            .setView(R.layout.pop_detail_menu)
            .size(LinearLayout.LayoutParams.MATCH_PARENT.toFloat(), LinearLayout.LayoutParams.WRAP_CONTENT.toFloat())
            .setAnimationStyle(R.style.contextMenuAnim)
            .setFocusable(true)
            .setTouchable(true)
            .setOutsideTouchable(true)
            .create()
        val rootview = LayoutInflater.from(activity)
            .inflate(R.layout.fragment_recommend, null)
        popwindowUtil.showAtLocation(rootview,0,0,Gravity.BOTTOM,0.6f)
        popwindowUtil.getView<TextView>(R.id.pop_up_name)!!.text = data.dislike_reasons[0].reason_name
        popwindowUtil.getView<TextView>(R.id.pop_cancel)!!.setOnClickListener({
            popwindowUtil.dissmiss()
        })
    }

}