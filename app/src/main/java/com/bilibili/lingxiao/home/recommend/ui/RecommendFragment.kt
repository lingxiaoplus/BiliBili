package com.bilibili.lingxiao.home.recommend.ui

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.banner.BannerImageLoader
import com.bilibili.lingxiao.home.recommend.RecommendData
import com.bilibili.lingxiao.home.recommend.RecommendPresenter
import com.bilibili.lingxiao.home.recommend.RecommendView
import com.bilibili.lingxiao.utils.ToastUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseFragment
import com.camera.lingxiao.common.utills.LogUtils
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.layout_recommend.view.*
import kotlin.properties.Delegates

class RecommendFragment :BaseFragment(), RecommendView {

    private var recommendPresenter = RecommendPresenter(this, this)
    private var mRecommendList: List<RecommendData> = arrayListOf()
    private var mAdapter:RecommendRecyAdapter by Delegates.notNull()
    private var banner:Banner by Delegates.notNull()
    override val contentLayoutId: Int
        get() = R.layout.fragment_recommend

    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }

    override fun initWidget(root: View) {
        super.initWidget(root)
        mAdapter = RecommendRecyAdapter(R.layout.item_video,mRecommendList)
        banner = Banner(context)
        mAdapter.addHeaderView(banner)
        var manager = GridLayoutManager(context,2)
        //var manager = LinearLayoutManager()
        root.recycerView.adapter = mAdapter
        root.recycerView.layoutManager = manager
        root.recycerView.isNestedScrollingEnabled = false
        recommendPresenter.getRecommendList(1)
    }
    override fun onGetRecommendData(recommendData: List<RecommendData>) {
        ToastUtil.show("成功")
        var banner = recommendData.get(0)
        for (data in recommendData.subList(1,recommendData.size)){
            mAdapter.addData(data)
        }
        initBanner(banner.banner_item)
        LogUtils.e("RecommendFragment ==》》》" + banner.toString())
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