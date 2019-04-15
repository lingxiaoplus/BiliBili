package com.bilibili.lingxiao.home.category

import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.utils.ToastUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseFragment
import kotlinx.android.synthetic.main.fragment_hot.view.*

class CategoryFragment :BaseFragment() ,RegionView{
    private var regionPresenter:RegionPresenter = RegionPresenter(this,this)
    private var regionList = arrayListOf<RegionData.Data>()
    private lateinit var regionAdapter:RegionAdapter
    override val contentLayoutId: Int
        get() = R.layout.fragment_hot
    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }

    override fun initWidget(root: View) {
        super.initWidget(root)
        var manager = GridLayoutManager(activity,4)
        root.category_recyclerview.layoutManager = manager
        regionAdapter = RegionAdapter(R.layout.item_live_category,regionList)
        root.category_recyclerview.adapter = regionAdapter
        regionPresenter.getRegion()
    }

    override fun onGetRegion(regions: List<RegionData.Data>) {
        regionList.clear()
        var list = regions
        if (regions.size > 16){
            list = regions.subList(0,16)
        }
        regionAdapter.addData(list)
    }

    override fun showDialog() {
    }

    override fun diamissDialog() {
    }

    override fun showToast(text: String?) {
        ToastUtil.show(text)
    }
}