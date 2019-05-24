package com.bilibili.lingxiao.home.region.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.FrameLayout
import com.bilibili.lingxiao.GlobalProperties
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.region.model.RegionDetailData
import com.bilibili.lingxiao.home.region.presenter.RegionDetailPresenter
import com.bilibili.lingxiao.home.region.view.RegionDetailView
import com.bilibili.lingxiao.home.live.BannerImageLoader
import com.bilibili.lingxiao.utils.StringUtil
import com.bilibili.lingxiao.utils.ToastUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.bilibili.lingxiao.web.WebActivity
import com.camera.lingxiao.common.app.BaseFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.normal_refresh_view.*
import kotlinx.android.synthetic.main.normal_refresh_view.view.*

class RegionDetailFragment :BaseFragment(),RegionDetailView{
    private var presenter = RegionDetailPresenter(this,this)
    private var regionList = arrayListOf<RegionDetailData.Info>()
    private var tid = 1
    private var rid = 1
    private lateinit var videoAdapter:VideoAdapter
    override val contentLayoutId: Int
        get() = R.layout.normal_refresh_view    //fragment_region_detail

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
        manager.setSpanSizeLookup(object :GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                if (videoAdapter.headerLayoutCount > 0){
                    if(position == 0){
                        return 2
                    }else{
                        return 1
                    }
                }
                return 1
            }
        })
        root.recycerView.adapter = videoAdapter
        root.recycerView.layoutManager = manager
        root.refresh.setOnRefreshListener {
            regionList.clear()
            presenter.getRegionDetail(tid)
        }
        root.refresh.setOnLoadMoreListener {
            presenter.getRegionMore(rid)
        }
    }

    override fun onFirstVisiblity() {
        super.onFirstVisiblity()
        refresh.autoRefresh()
    }

    override fun onGetRegionDetail(data: RegionDetailData) {
        data.banner?.top?.let {
            initBanner(it)
        }
        data.recommend?.let {
            videoAdapter.addData(it)
            if (it.size > 0){
                rid = it[0].rid
            }
        }
        data.new?.let {
            videoAdapter.addData(it)
        }
        refresh.finishRefresh()

    }

    override fun onGetRegionMore(data: RegionDetailData) {
        data.new?.let {
            videoAdapter.addData(it)
        }
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
            //helper.setText(R.id.category_name,item.rname)
            helper.addOnClickListener(R.id.image_more)
            helper.getView<ConstraintLayout>(R.id.cons_category).visibility = View.GONE
        }
    }

    private var banner:Banner? = null
    private fun initBanner(bannerData: List<RegionDetailData.Banner.Top>) {
        if (banner != null){
            return
        }
        banner = Banner(context)
        var layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
            UIUtil.getDimen(R.dimen.banner_height))
        banner?.layoutParams = layoutParams
        var images = ArrayList<String>()
        for (image in bannerData){
            images.add(image.image)
        }
        banner?.setImageLoader(BannerImageLoader())
        //设置图片集合
        banner?.setImages(images)
        //设置banner动画效果
        banner?.setBannerAnimation(Transformer.ZoomOutSlide)
        //设置标题集合（当banner样式有显示title时）
        //banner?.setBannerTitles(titles)
        //设置自动轮播，默认为true
        banner?.isAutoPlay(true)
        //设置轮播时间
        banner?.setDelayTime(3000)
        //设置指示器位置（当banner模式中有指示器时）
        banner?.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        banner?.setIndicatorGravity(BannerConfig.RIGHT)
        //banner设置方法全部调用完毕时最后调用
        banner?.start()
        banner?.setOnBannerListener(object : OnBannerListener {
            override fun OnBannerClick(position: Int) {
                var intent = Intent(context, WebActivity::class.java)
                intent.putExtra("uri",bannerData[position].uri)
                intent.putExtra("title",bannerData[position].title)
                intent.putExtra("image",bannerData[position].image)
                startActivity(intent)
            }
        })
        videoAdapter.addHeaderView(banner)
    }
}