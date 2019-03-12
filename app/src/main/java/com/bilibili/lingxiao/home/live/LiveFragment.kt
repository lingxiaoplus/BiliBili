package com.bilibili.lingxiao.home.live

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.banner.BannerImageLoader
import com.bilibili.lingxiao.home.live.recommend.LiveRecommendAdapter
import com.camera.lingxiao.common.app.BaseFragment
import com.camera.lingxiao.common.utills.LogUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_live.*
import kotlinx.android.synthetic.main.fragment_live.view.*
import javax.inject.Inject

class LiveFragment :BaseFragment() ,LiveView{

    var livePresenter: LivePresenter = LivePresenter(this,this)
    val TAG = LiveFragment::class.java.simpleName
    var liveList = arrayListOf<LiveData.EntranceIconsBean>()

    private var liveAdapter:LiveRecyAdapter? = null

    private var liveRecommendAdapter:LiveRecommendAdapter? = null
    var liveRecommendList = arrayListOf<LiveData.RecommendDataBean.LivesBean>()
    override val contentLayoutId: Int
        get() = R.layout.fragment_live

    override fun initWidget(root: View) {
        super.initWidget(root)
        livePresenter.getLiveList(1)
        var manager = GridLayoutManager(context,5)
        liveAdapter = LiveRecyAdapter(R.layout.item_live_category,liveList)
        root.live_category_recy.adapter = liveAdapter
        root.live_category_recy.layoutManager = manager

        root.live_recommend_recy.setHasFixedSize(true)      //设置固定大小
        root.live_recommend_recy.setLayoutManager(
            StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
        )
        liveRecommendAdapter = LiveRecommendAdapter(R.layout.item_live_video,liveRecommendList)
        root.live_recommend_recy.adapter = liveRecommendAdapter
    }


    override fun onGetLiveList(data: LiveData) {
        liveAdapter?.addData(data.entranceIcons)
        liveRecommendAdapter?.addData(data.recommend_data.lives)
        initBanner(data.banner)
    }


    private fun initBanner(bannerData: List<LiveData.BannerBean>) {
        var images = ArrayList<String>()
        for (item in bannerData){
            images.add(item.img)
        }
        var banner: Banner = live_banner
        banner.setImageLoader(BannerImageLoader())
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        //live_banner.setBannerTitles(banner.get(0).title);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    override fun showDialog() {

    }

    override fun diamissDialog() {

    }

    override fun showToast(text: String?) {

    }
}