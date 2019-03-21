package com.bilibili.lingxiao.home.live

import android.net.Uri
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.banner.BannerImageLoader
import com.bilibili.lingxiao.home.live.category.LiveCategoryAdapter
import com.bilibili.lingxiao.home.live.partitions.PartitionAdapter
import com.bilibili.lingxiao.home.live.partitions.PartitionVideoAdapter
import com.bilibili.lingxiao.home.live.recommend.LiveRecommendAdapter
import com.bilibili.lingxiao.utils.ToastUtil
import com.camera.lingxiao.common.utills.LogUtils
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.facebook.drawee.view.SimpleDraweeView
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer

class LiveRecyAdapter : BaseMultiItemQuickAdapter<MultiItemLiveData, BaseViewHolder> {
    constructor(data: MutableList<MultiItemLiveData>) :super(data){
        addItemType(MultiItemLiveData.BANNER,R.layout.layout_banner)
        addItemType(MultiItemLiveData.CATEGORY,R.layout.item_live_category)
        addItemType(LiveData.RECOMMEND,R.layout.layout_recommend)
        addItemType(LiveData.PARTITION,R.layout.layout_partition)

        /*multiTypeDelegate = object : MultiTypeDelegate<LiveData>() {
            override fun getItemType(entity: LiveData): Int {
                //根据你的实体类来判断布局类型
                return entity.itemType
            }
        }
        multiTypeDelegate
            .registerItemType(LiveData.BANNER,R.layout.layout_banner)
            .registerItemType(LiveData.CATEGORY,R.layout.layout_category)
            .registerItemType(LiveData.RECOMMEND,R.layout.layout_recommend)
            .registerItemType(LiveData.PARTITION,R.layout.layout_category)*/
    }
    override fun convert(helper: BaseViewHolder, item: MultiItemLiveData) {
        when(helper.itemViewType){
            MultiItemLiveData.BANNER-> {
                var banner :Banner = helper.getView(R.id.live_banner)
                initBanner(banner,item.bannerList)
            }
            MultiItemLiveData.CATEGORY->{
                //initCategory(helper,item.entranceIcons)
                helper.setText(R.id.item_live_title,item.entranceIconsBean.name)
                var image : SimpleDraweeView = helper.getView(R.id.item_live_image)
                image.setImageURI(Uri.parse(item.entranceIconsBean.entrance_icon.src))
            }
            MultiItemLiveData.RECOMMEND->{
                initRecommend(helper,item.liveList)
            }
            MultiItemLiveData.PARTITION->{
                helper.setText(R.id.live_category_name,item.partitionsBean.partition.name)
                LogUtils.d("LiveRecyAdapter 获取到Partition name 的值-》》" + item.partitionsBean.partition.name)
                initPartition(helper,item.partitionsBean.lives)
                //initPartition(helper,item.partitions)
            }
        }
    }


    private fun initPartition(helper: BaseViewHolder, data: MutableList<LiveData.PartitionsBean.LivesBeanX>) {
        var list = data
        if (list.size > 4){
            list = list.subList(0,4)
        }
        var categoryAdapter = PartitionVideoAdapter(R.layout.item_live_video,list)
        var manager = GridLayoutManager(mContext,2)
        val recyclerView:RecyclerView = helper.getView(R.id.live_partition_recy)
        recyclerView.adapter = categoryAdapter
        recyclerView.layoutManager = manager
        recyclerView.isNestedScrollingEnabled = false

        recyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    categoryAdapter.isScroller = false
                    categoryAdapter.notifyDataSetChanged()
                }else{
                    categoryAdapter.isScroller = true
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        categoryAdapter.setOnItemClickListener(object :OnItemClickListener{
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                listener?.let {
                    it.onPartitionClick(data[position],position)
                }
            }

        })
    }

    private fun initRecommend(helper: BaseViewHolder, lives: MutableList<LiveData.RecommendDataBean.LivesBean>) {
        val recyclerView:RecyclerView = helper.getView(R.id.recycerView)
        recyclerView.setHasFixedSize(true)      //设置固定大小
        recyclerView.setLayoutManager(GridLayoutManager(mContext, 2,GridLayoutManager.VERTICAL,false))
        var liveRecommendAdapter = LiveRecommendAdapter(R.layout.item_live_video,lives)
        recyclerView.adapter = liveRecommendAdapter
        recyclerView.isNestedScrollingEnabled = false


        liveRecommendAdapter.setOnItemClickListener(object :OnItemClickListener{
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                ToastUtil.show(lives[position].playurl)
                listener?.let {
                    it.onRecommendClick(lives[position],position)
                }
            }
        })
    }


    private fun initBanner(banner :Banner,bannerData: List<LiveData.BannerBean>) {
        var images = ArrayList<String>()
        for (image in bannerData){
            images?.add(image.img)
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

    private  var listener: OnMultiItemClickListener? = null
    public fun setMultiItemClickListener(listener: OnMultiItemClickListener){
        this.listener = listener
    }
    public interface OnMultiItemClickListener{
        fun onRecommendClick(live: LiveData.RecommendDataBean.LivesBean,position:Int)
        fun onPartitionClick(live: LiveData.PartitionsBean.LivesBeanX,position:Int)
    }
}