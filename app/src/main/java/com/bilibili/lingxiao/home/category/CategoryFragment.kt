package com.bilibili.lingxiao.home.category

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.utils.ToastUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseFragment
import kotlinx.android.synthetic.main.fragment_hot.*
import kotlinx.android.synthetic.main.fragment_hot.view.*

class CategoryFragment :BaseFragment() ,RegionView{
    private var regionPresenter:RegionPresenter = RegionPresenter(this,this)
    private var regionList = arrayListOf<MultiRegionData>()
    private lateinit var regionAdapter:RegionAdapter
    private val TAG = CategoryFragment::class.java.simpleName
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
                var type = regionAdapter.data.get(position).itemType
                when(type){
                    MultiRegionData.REGION_ITEM-> return 1
                    MultiRegionData.REGION_RECOMMEND-> return 4
                    else-> return 0
                }
            }
        })
        val recycledViewPool = RecyclerView.RecycledViewPool()
        regionAdapter = RegionAdapter(regionList,recycledViewPool)
        root.category_recyclerview.adapter = regionAdapter
        root.category_recyclerview.layoutManager = manager
        root.refresh.setOnRefreshListener({
            regionPresenter.getRegion()
        })
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
    }


    override fun onGetRegion(regions: List<RegionData.Data>) {
        regionList.clear()
        var list = regions
        if (regions.size > 16){
            list = regions.subList(0,16)
        }
        var multiData = MultiRegionData(MultiRegionData.REGION_ITEM)
        for (region in list){
            var data = multiData.copy(MultiRegionData.REGION_ITEM)
            data.regionData = region
            regionAdapter.addData(data)
        }
        regionPresenter.getRegionRecommend()
    }

    override fun onGetRegionRecommend(recommendList: List<RegionRecommendData.Data>) {
        for (recommend in recommendList){
            var data = MultiRegionData(MultiRegionData.REGION_RECOMMEND)
            data.recommendData = recommend
            regionAdapter.addData(data)
        }
        refresh.finishRefresh()
        refresh.finishLoadMore()
        regionAdapter.loadMoreEnd()

    }

    override fun showDialog() {

    }

    override fun diamissDialog() {
    }

    override fun showToast(text: String?) {
        ToastUtil.show(text)
    }
}