package com.bilibili.lingxiao.home.recommend.ui

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.banner.BannerImageLoader
import com.bilibili.lingxiao.home.recommend.RecommendData
import com.bilibili.lingxiao.home.recommend.RecommendPresenter
import com.bilibili.lingxiao.home.recommend.RecommendView
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
        var manager = GridLayoutManager(context,2)
        manager.setSpanSizeLookup(object :GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                if(position == 0){
                    return 2
                }else{
                    return 1
                }
            }
        })
        root.recycerView.adapter = mAdapter
        root.recycerView.layoutManager = manager
        //root.recycerView.isNestedScrollingEnabled = false
        recommendPresenter.getRecommendList(operationState)

        root.refresh.autoRefresh()
        root.refresh.setOnRefreshListener {
            operationState = 2
            recommendPresenter.getRecommendList(operationState)
        }
        root.refresh.setOnLoadMoreListener {
            operationState = 3
            recommendPresenter.getRecommendList(operationState)
        }
        mAdapter.setOnItemClickListener(object :BaseQuickAdapter.OnItemClickListener{
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                //val intent = Intent(context,PlayActivity::class.java)
                var recommendData:RecommendData = mRecommendList.get(position)
                EventBus.getDefault().postSticky(recommendData)
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(mRecommendList.get(position).uri)
                )
                intent.putExtra("play_url",mRecommendList.get(position).uri)
                startActivity(intent)
            }

        })
    }


    override fun onGetRecommendData(recommendData: List<RecommendData>) {
        if (operationState == 1){

            var banner = recommendData.get(0)
            for (data in recommendData.subList(1,recommendData.size)){
                mAdapter.addData(data)
            }
            initBanner(banner.banner_item)
        }else{
            for (data in recommendData){
                if (mAdapter.itemCount > 0){
                    mAdapter.addData(1,data)
                }else{
                    mAdapter.addData(data)
                }

            }
        }

        refresh.finishRefresh()
        refresh.finishLoadMore()
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
    }
}