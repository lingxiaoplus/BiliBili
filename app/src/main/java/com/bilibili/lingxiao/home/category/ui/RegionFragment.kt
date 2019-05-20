package com.bilibili.lingxiao.home.category.ui

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.category.RegionAdapter
import com.bilibili.lingxiao.home.category.presenter.RegionPresenter
import com.bilibili.lingxiao.home.category.view.RegionView
import com.bilibili.lingxiao.home.category.model.MultiRegionData
import com.bilibili.lingxiao.home.category.model.RegionData
import com.bilibili.lingxiao.home.category.model.RegionRecommendData
import com.bilibili.lingxiao.utils.ToastUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseFragment
import com.camera.lingxiao.common.utills.LogUtils
import kotlinx.android.synthetic.main.fragment_hot.*
import kotlinx.android.synthetic.main.fragment_hot.view.*
import org.greenrobot.eventbus.EventBus

class RegionFragment :BaseFragment() , RegionView {
    private var regionPresenter: RegionPresenter =
        RegionPresenter(this, this)
    private var regionList = arrayListOf<MultiRegionData>()
    private lateinit var regionAdapter: RegionAdapter
    private val TAG = RegionFragment::class.java.simpleName
    override val contentLayoutId: Int
        get() = R.layout.fragment_hot
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
                    MultiRegionData.REGION_RECOMMEND-> return 4
                    else-> return 4
                }
            }
        })
        val recycledViewPool = RecyclerView.RecycledViewPool()
        regionAdapter = RegionAdapter(regionList, recycledViewPool)
        root.category_recyclerview.adapter = regionAdapter
        root.category_recyclerview.layoutManager = manager
        root.refresh.setOnRefreshListener({
            regionPresenter.getRegion()
        })
        //root.refresh.setEnableAutoLoadMore(false)
        var emptyView = View.inflate(context,R.layout.layout_empty,null)
        var image = emptyView.findViewById<ImageView>(R.id.image_error)
        image.setImageDrawable(resources.getDrawable(R.drawable.bilipay_common_error_tip))
        regionAdapter.setEmptyView(emptyView)
        regionAdapter.setMultiItemClickListener(object :RegionAdapter.OnMultiItemClickListener{
            override fun onVideoClick(data: RegionRecommendData.Data.Body?, position: Int) {
                ToastUtil.show(data?.title)
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
                R.id.button_goto -> {
                    regionList[position].recommendData?.let {
                        var intent = Intent(context,RegionTabActivity::class.java)
                        intent.putExtra("title",it.title)
                        startActivity(intent)
                        EventBus.getDefault().postSticky(it)
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
        category_recyclerview.smoothScrollToPosition(0)
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
            var data =
                MultiRegionData(MultiRegionData.REGION_RECOMMEND)
            data.recommendData = recommend
            regionAdapter.addData(data)
        }
        refresh.finishRefresh()
        refresh.finishLoadMore()
        regionAdapter.loadMoreEnd()
        category_recyclerview.smoothScrollToPosition(0)
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