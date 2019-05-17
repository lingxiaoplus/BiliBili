package com.bilibili.lingxiao.home.category.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.bilibili.lingxiao.GlobalProperties
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.category.model.RegionDetailData
import com.bilibili.lingxiao.home.category.presenter.RegionDetailPresenter
import com.bilibili.lingxiao.home.category.view.RegionDetailView
import com.bilibili.lingxiao.home.live.BannerImageLoader
import com.bilibili.lingxiao.utils.StringUtil
import com.bilibili.lingxiao.utils.ToastUtil
import com.bilibili.lingxiao.web.WebActivity
import com.camera.lingxiao.common.app.BaseFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.fragment_region_detail.*
import kotlinx.android.synthetic.main.layout_banner.*
import kotlinx.android.synthetic.main.normal_refresh_view.*
import kotlinx.android.synthetic.main.normal_refresh_view.view.*
import java.lang.Exception

class RegionDetailFragment :BaseFragment(),RegionDetailView{
    private var presenter = RegionDetailPresenter(this,this)
    private var regionList = arrayListOf<RegionDetailData.Info>()
    private var tid = 1
    private lateinit var videoAdapter:VideoAdapter
    override val contentLayoutId: Int
        get() = R.layout.fragment_region_detail

    override fun initArgs(bundle: Bundle?) {
        super.initArgs(bundle)
        bundle?.let {
            tid = it.getInt("tid")
        }
    }
    override fun initWidget(root: View) {
        super.initWidget(root)
        videoAdapter =
            VideoAdapter(R.layout.item_video, regionList)
        var manager = GridLayoutManager(context,2)
        root.recycerView.adapter = videoAdapter
        root.recycerView.layoutManager = manager
        root.refresh.setOnRefreshListener {
            regionList.clear()
            presenter.getRegionDetail(tid)
        }
    }

    override fun initData() {
        super.initData()
        refresh.autoRefresh()
    }

    override fun onGetRegionDetail(data: RegionDetailData) {
        data.banner?.top?.let {
            root_banner.visibility = View.VISIBLE
            initBanner(live_banner,it)
        }
        videoAdapter.addData(data.recommend)
        videoAdapter.addData(data.new)
        refresh.finishRefresh()
        refresh.finishLoadMore()
    }

    override fun showDialog() {

    }

    override fun diamissDialog() {

    }

    override fun showToast(text: String?) {
        ToastUtil.show(text)
    }

    class VideoAdapter(layoutResId: Int, data: MutableList<RegionDetailData.Info>?) :
        BaseQuickAdapter<RegionDetailData.Info, BaseViewHolder>(layoutResId, data) {
        override fun convert(helper: BaseViewHolder, item: RegionDetailData.Info) {
            var image : SimpleDraweeView = helper.getView(R.id.play_image)
            image.setImageURI(Uri.parse(item.cover + GlobalProperties.IMAGE_RULE_240_150))
            helper.setText(R.id.play_title,item.title)
            helper.setText(R.id.play_number,StringUtil.getBigDecimalNumber(item.play))
            helper.setText(R.id.comment_number,StringUtil.getBigDecimalNumber(item.reply))
            helper.setText(R.id.category_name,item.rname)
            helper.addOnClickListener(R.id.image_more)
        }
    }

    private fun initBanner(banner:Banner,bannerData: List<RegionDetailData.Banner.Top>) {
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

        banner.setOnBannerListener(object : OnBannerListener {
            override fun OnBannerClick(position: Int) {
                var intent = Intent(context, WebActivity::class.java)
                intent.putExtra("uri",bannerData[position].uri)
                intent.putExtra("title",bannerData[position].title)
                intent.putExtra("image",bannerData[position].image)
                startActivity(intent)
            }

        })
    }
}