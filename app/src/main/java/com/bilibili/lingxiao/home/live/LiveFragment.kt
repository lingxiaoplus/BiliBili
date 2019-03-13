package com.bilibili.lingxiao.home.live

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager

import android.view.View
import android.widget.Toast
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.utils.ToastUtil

import com.camera.lingxiao.common.app.BaseFragment
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_live.view.*
import kotlin.properties.Delegates

class LiveFragment :BaseFragment() ,LiveView{

    var livePresenter: LivePresenter = LivePresenter(this,this)
    val TAG = LiveFragment::class.java.simpleName
    var liveList = arrayListOf<LiveData>()

    private var liveAdapter:LiveRecyAdapter? = null
    private var refresh: SmartRefreshLayout by Delegates.notNull()
    override val contentLayoutId: Int
        get() = R.layout.fragment_live

    override fun initWidget(root: View) {
        super.initWidget(root)
        var manager = LinearLayoutManager(context)
        liveAdapter = LiveRecyAdapter(liveList)
        root.live_recy.adapter = liveAdapter
        root.live_recy.layoutManager = manager
        refresh = root.refresh
        refresh.setOnRefreshListener {
            livePresenter.getLiveList(1)
        }
        livePresenter.getLiveList(1)
    }


    override fun onGetLiveList(data: LiveData) {
        liveList.clear()
        var bannerData = LiveData(LiveData.BANNER)
        bannerData.banner = data.banner

        var categoryData = LiveData(LiveData.CATEGORY)
        categoryData.entranceIcons = data.entranceIcons

        var recommendData = LiveData(LiveData.RECOMMEND)
        recommendData.recommend_data = data.recommend_data

        var partitionData = LiveData(LiveData.PARTITION)
        partitionData.partitions = data.partitions

        liveList.add(bannerData)
        liveList.add(categoryData)
        liveList.add(recommendData)
        liveList.add(partitionData)
        liveAdapter?.notifyDataSetChanged()
        refresh.finishRefresh()
    }




    override fun showDialog() {

    }

    override fun diamissDialog() {

    }

    override fun showToast(text: String?) {
        ToastUtil.show(text)
    }
}