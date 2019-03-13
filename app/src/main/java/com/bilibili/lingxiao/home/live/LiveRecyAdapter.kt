package com.bilibili.lingxiao.home.live

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.banner.BannerImageLoader
import com.bilibili.lingxiao.home.live.category.LiveCategoryAdapter
import com.bilibili.lingxiao.home.live.partitions.PartitionAdapter
import com.bilibili.lingxiao.home.live.recommend.LiveRecommendAdapter
import com.camera.lingxiao.common.utills.LogUtils
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer

class LiveRecyAdapter : BaseMultiItemQuickAdapter<LiveData, BaseViewHolder> {
    constructor(data: MutableList<LiveData>) :super(data){
        addItemType(LiveData.BANNER,R.layout.layout_banner)
        addItemType(LiveData.CATEGORY,R.layout.layout_category)
        addItemType(LiveData.RECOMMEND,R.layout.layout_recommend)
        addItemType(LiveData.PARTITION,R.layout.layout_category)
    }
    override fun convert(helper: BaseViewHolder, item: LiveData) {
        when(helper.itemViewType){
            LiveData.BANNER-> {
                var banner:Banner = helper.getView(R.id.live_banner)
                initBanner(banner,item.banner)
            }
            LiveData.CATEGORY->{
                initCategory(helper,item.entranceIcons)
            }
            LiveData.RECOMMEND->{
                initRecommend(helper,item.recommend_data.lives)
            }
            LiveData.PARTITION->{
                initPartition(helper,item.partitions)
            }
        }
    }
    var partitionAdapter:PartitionAdapter? = null
    private fun initPartition(helper: BaseViewHolder, partitions: MutableList<LiveData.PartitionsBean>) {
        if (partitionAdapter == null){
            var manager = LinearLayoutManager(mContext)
            val recyclerView:RecyclerView = helper.getView(R.id.recycerView)
            partitionAdapter = PartitionAdapter(R.layout.layout_partition,partitions)
            recyclerView.adapter = partitionAdapter
            recyclerView.layoutManager = manager
            LogUtils.d("LiveRecyAdapter 获取到Partition的值-》》" + partitions.size)
        }else{
            partitionAdapter?.addData(partitions)
        }
    }

    private fun initRecommend(helper: BaseViewHolder, lives: MutableList<LiveData.RecommendDataBean.LivesBean>) {
        val recyclerView:RecyclerView = helper.getView(R.id.recycerView)
        recyclerView.setHasFixedSize(true)      //设置固定大小
        recyclerView.setLayoutManager(GridLayoutManager(mContext, 2,GridLayoutManager.VERTICAL,false))
        var liveRecommendAdapter = LiveRecommendAdapter(R.layout.item_live_video,lives)
        recyclerView.adapter = liveRecommendAdapter
        recyclerView.isNestedScrollingEnabled = false
        /*liveRecommendAdapter.setSpanSizeLookup(object :BaseQuickAdapter.SpanSizeLookup{
            override fun getSpanSize(gridLayoutManager: GridLayoutManager?, position: Int): Int {
                data.get(position).getSpanSize();
            }
        })*/
    }

    private fun initCategory(helper: BaseViewHolder, data: MutableList<LiveData.EntranceIconsBean>) {
        var categoryAdapter = LiveCategoryAdapter(R.layout.item_live_category,data)
        var manager = GridLayoutManager(mContext,5)
        val recyclerView:RecyclerView = helper.getView(R.id.recycerView)
        recyclerView.adapter = categoryAdapter
        recyclerView.layoutManager = manager
        recyclerView.isNestedScrollingEnabled = false
    }


    private fun initBanner(banner :Banner,bannerData: MutableList<LiveData.BannerBean>) {
        var images = ArrayList<String>()
        for (item in bannerData){
            images.add(item.img)
        }
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

}