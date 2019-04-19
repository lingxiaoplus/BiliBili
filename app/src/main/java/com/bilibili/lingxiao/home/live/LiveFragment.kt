package com.bilibili.lingxiao.home.live

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log

import android.view.View
import android.widget.Toast
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.utils.ToastUtil

import com.camera.lingxiao.common.app.BaseFragment
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_live.view.*
import kotlin.properties.Delegates
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.bilibili.lingxiao.home.live.banner.BannerImageLoader
import com.bilibili.lingxiao.utils.UIUtil
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_live.*
import kotlinx.android.synthetic.main.layout_banner.view.*
import java.util.zip.Inflater
import javax.inject.Inject

class LiveFragment :BaseFragment() ,LiveView{
    var livePresenter: LivePresenter = LivePresenter(this,this)
    val TAG = LiveFragment::class.java.simpleName
    var liveList = arrayListOf<MultiItemLiveData>()

    private var liveAdapter:LiveRecyAdapter by Delegates.notNull()
    private var refresh: SmartRefreshLayout by Delegates.notNull()

    private val topView by lazy {
        View.inflate(activity,R.layout.live_top_region,null)
    }

    private val footerShowAllView by lazy {
        View.inflate(activity,R.layout.footer_live_showall,null)
    }

    override val contentLayoutId: Int
        get() = R.layout.fragment_live

    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }
    override fun initWidget(root: View) {
        super.initWidget(root)
        /*var manager = GridLayoutManager(context,10,GridLayoutManager.VERTICAL,false)
        manager.setSpanSizeLookup(object :GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {

                //Log.e(TAG,"获取到的类型"+type)
                if (position == 0){
                    return 10
                }
                var type = liveAdapter.data.get(position).itemType
                when(type){
                    MultiItemLiveData.BANNER-> return 10
                    MultiItemLiveData.CATEGORY-> return 2
                    MultiItemLiveData.RECOMMEND-> return 10
                    MultiItemLiveData.PARTITION-> return 10
                    else-> return 0
                }
            }
        })*/
        var manager = LinearLayoutManager(context)
        //让互相嵌套的RecyclerView的item都进入同一个共享池
        val recycledViewPool = RecyclerView.RecycledViewPool()
        root.live_recy.setRecycledViewPool(recycledViewPool)
        liveAdapter = LiveRecyAdapter(liveList,recycledViewPool)
        root.live_recy.adapter = liveAdapter
        root.live_recy.layoutManager = manager
        refresh = root.refresh
        refresh.setOnRefreshListener {
            livePresenter.getLiveList()
        }

        liveAdapter.addHeaderView(topView)
        liveAdapter.addFooterView(footerShowAllView)
        liveAdapter.setMultiItemClickListener(object :LiveRecyAdapter.OnMultiItemClickListener{
            override fun onRecommendClick(live: LiveData.RecommendDataBean.LivesBean, position: Int) {
                val intent = Intent(context,LivePlayActivity::class.java)
                intent.putExtra("play_url",live.playurl)
                startActivity(intent)
            }

            override fun onPartitionClick(live: LiveData.PartitionsBean.LivesBeanX, position: Int) {
                val intent = Intent(context,LivePlayActivity::class.java)
                intent.putExtra("play_url",live.playurl)
                startActivity(intent)
            }

        })
    }

    override fun onFirstVisiblity() {
        super.onFirstVisiblity()
        refresh.autoRefresh()
    }

    override fun onVisiblityChanged(visiblity: Boolean) {
        super.onVisiblityChanged(visiblity)
        if (visiblity && liveAdapter.itemCount - liveAdapter.headerLayoutCount - liveAdapter.footerLayoutCount < 1){
            refresh.autoRefresh()
        }
    }

    //var bannerData = MultiItemLiveData(MultiItemLiveData.BANNER)
    var recommendData = MultiItemLiveData(MultiItemLiveData.RECOMMEND)
    override fun onGetLiveList(data: LiveData) {
        liveList.clear()

        //bannerData.bannerList = data.banner
        //liveList.add(bannerData)
        initBanner(topView.live_banner,data.banner)
        /*for (category in data.entranceIcons){
            var categoryData = MultiItemLiveData(MultiItemLiveData.CATEGORY)
            categoryData.entranceIconsBean = category
            liveList.add(categoryData)
        }*/

        recommendData.liveList = data.recommend_data.lives
        recommendData.partitionsBean = if (data.partitions.size > 0) data.partitions[0] else
            LiveData.PartitionsBean()
        recommendData.partitionsBean.partition = data.recommend_data.partition
        liveList.add(recommendData)

        for (partition in data.partitions){
            var partitionData = MultiItemLiveData(MultiItemLiveData.PARTITION)
            partitionData.partitionsBean = partition
            liveList.add(partitionData)
        }

        liveAdapter.notifyDataSetChanged()
        refresh.finishRefresh()
    }


    private var initBanner = false
    private fun initBanner(banner : Banner, bannerData: List<LiveData.BannerBean>) {
        if (initBanner) return
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
        initBanner = true
    }

    override fun showDialog() {

    }

    override fun diamissDialog() {

    }

    override fun showToast(text: String?) {
        ToastUtil.show(text)
    }
}