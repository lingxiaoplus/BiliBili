package com.bilibili.lingxiao.home.region.ui

import android.content.Intent
import android.hardware.usb.UsbManager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.region.RegionAdapter
import com.bilibili.lingxiao.home.region.presenter.RegionPresenter
import com.bilibili.lingxiao.home.region.view.RegionView
import com.bilibili.lingxiao.home.region.model.MultiRegionData
import com.bilibili.lingxiao.home.region.model.RegionData
import com.bilibili.lingxiao.home.region.model.RegionRecommendData
import com.bilibili.lingxiao.utils.ToastUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseFragment
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_region.*
import kotlinx.android.synthetic.main.fragment_region.view.*
import org.greenrobot.eventbus.EventBus
import java.util.*

class RegionFragment :BaseFragment() , RegionView {
    private var regionPresenter: RegionPresenter =
        RegionPresenter(this, this)
    private var regionList = arrayListOf<MultiRegionData>()
    private lateinit var regionAdapter: RegionAdapter
    private val TAG = RegionFragment::class.java.simpleName
    override val contentLayoutId: Int
        get() = R.layout.fragment_region
    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }

    override fun initWidget(root: View) {
        super.initWidget(root)
        var manager = GridLayoutManager(activity,4)
        manager.setSpanSizeLookup(object :GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                //先设置adapter 再设置manager才会调用
                var type = 0
                if (regionAdapter.data.size > 0){
                    type = regionAdapter.data.get(position).itemType
                }
                when(type){
                    MultiRegionData.REGION_ITEM-> return 1
                    MultiRegionData.REGION_TOP_BAR,MultiRegionData.REGION_BOTTOM_BAR-> return 4
                    MultiRegionData.REGION_RECOMMEND-> return 2
                    else-> return 4
                }
            }
        })
        val recycledViewPool = RecyclerView.RecycledViewPool()
        regionAdapter = RegionAdapter(regionList, recycledViewPool)
        root.category_recyclerview.adapter = regionAdapter
        root.category_recyclerview.layoutManager = manager
        root.category_recyclerview.setItemViewCacheSize(200)
        root.refresh.setOnRefreshListener({
            regionPresenter.getRegion()
        })
        //root.refresh.setEnableAutoLoadMore(false)
        var emptyView = View.inflate(context,R.layout.layout_empty,null)
        var image = emptyView.findViewById<ImageView>(R.id.image_error)
        image.setImageDrawable(resources.getDrawable(R.drawable.bilipay_common_error_tip))
        regionAdapter.setEmptyView(emptyView)
        regionAdapter.setMultiItemClickListener(object :RegionAdapter.OnMultiItemClickListener{
            override fun onRefreshClick(
                holde: BaseViewHolder,
                data: RegionRecommendData.Data?,
                position:Int
            ) {
                data?.let {
                    regionPresenter.refreshRegion(it.type,Random().nextInt(10),it.param.toInt())
                    regionPosition = position
                }
            }

            override fun onVideoClick(data: RegionRecommendData.Data.Body?, type:String) {
                if ("bangumi".equals(type)){
                    //分区是番剧
                    val intent = Intent(
                        context,
                        BangumiDetailActivity::class.java
                    )
                    intent.putExtra("id",data?.param)
                    //regionList[position].recommendData?.type
                    intent.putExtra("type",type)
                    startActivity(intent)
                }else{

                }
            }

            override fun onGridClick(data: RegionData.Data?, position: Int) {
                regionList[position].regionData?.let {
                    var intent = Intent(context,RegionTabActivity::class.java)
                    intent.putExtra("title",it.name)
                    startActivity(intent)
                    EventBus.getDefault().postSticky(it)
                }
            }

        })
        regionAdapter.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.item_live_image ->{
                    var intent = Intent(context,RegionTabActivity::class.java)
                    intent.putExtra("title",regionList[position].regionData?.name)
                    startActivity(intent)
                    EventBus.getDefault().postSticky(regionList[position].regionData)
                }
                R.id.button_goto, R.id.button_more -> {
                    regionList[position].recommendData?.let {
                        var intent = Intent(context,RegionTabActivity::class.java)
                        intent.putExtra("title",it.title)
                        startActivity(intent)
                        EventBus.getDefault().postSticky(it)
                    }
                }
                R.id.ll_refresh ->{
                    regionList[position].recommendData?.let {
                        regionPresenter.refreshRegion(it.type,Random().nextInt(10),it.param.toInt())
                    }

                }
            }
        }
    }

    override fun onFirstVisiblity() {
        super.onFirstVisiblity()
        refresh.autoRefresh()
    }

    override fun onVisiblityChanged(visiblity: Boolean) {
        super.onVisiblityChanged(visiblity)
        if (visiblity && regionAdapter.itemCount - regionAdapter.headerLayoutCount - regionAdapter.footerLayoutCount < 1){
            refresh.autoRefresh()
        }
        //category_recyclerview.smoothScrollToPosition(0)
    }


    override fun onGetRegion(regions: List<RegionData.Data>) {
        regionList.clear()
        var list = regions
        if (regions.size > 16){
            list = regions.subList(0,16)
        }
        var multiData =
            MultiRegionData(MultiRegionData.REGION_ITEM)
        for (region in list){
            var data = multiData.copy(MultiRegionData.REGION_ITEM)
            data.regionData = region
            regionAdapter.addData(data)
        }
        regionPresenter.getRegionRecommend()
    }

    override fun onGetRegionRecommend(recommendList: List<RegionRecommendData.Data>) {
        for (recommend in recommendList){
            var topData =
                MultiRegionData(MultiRegionData.REGION_TOP_BAR)
            topData.recommendData = recommend
            regionAdapter.addData(topData)
            for (bangumi in recommend.body){
                var data =
                    MultiRegionData(MultiRegionData.REGION_RECOMMEND)
                data.bangumiItemData = bangumi
                regionAdapter.addData(data)
            }
            var bottomData = topData.copy(MultiRegionData.REGION_BOTTOM_BAR)
            bottomData.recommendData = recommend
            regionAdapter.addData(bottomData)
        }
        refresh.finishRefresh()
        refresh.finishLoadMore()
        regionAdapter.loadMoreEnd()
        category_recyclerview.smoothScrollToPosition(0)
    }

    private var regionPosition = 0
    override fun onRefreshRegion(list: List<RegionRecommendData.Data.Body>) {
        //这里获取到的list为4条 regionPosition是bottombar的position，需要更新的是bottombar的上面的四宫格
        regionPosition = regionPosition - list.size
        if (regionPosition < 0) return
        for (item in list){
            var recommendData =
                MultiRegionData(MultiRegionData.REGION_RECOMMEND)
            recommendData.bangumiItemData = item
            //regionAdapter.setData(regionPosition++,recommendData)
            regionAdapter.data.set(regionPosition,recommendData)
            regionAdapter.notifyItemChanged(regionPosition++)
        }
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