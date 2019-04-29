package com.bilibili.lingxiao.home.live.ui.play

import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.adapter.FleetAdapter
import com.bilibili.lingxiao.home.live.model.*
import com.bilibili.lingxiao.home.live.presenter.FleetPresenter
import com.bilibili.lingxiao.home.live.ui.LivePlayActivity
import com.bilibili.lingxiao.home.live.view.LivePlayView
import com.bilibili.lingxiao.utils.ToastUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseFragment
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.fragment_fleet.*
import kotlinx.android.synthetic.main.fragment_fleet.view.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class FleetListFragment :BaseFragment(), LivePlayView {
    private val fleetPresenter: FleetPresenter by lazy {
        FleetPresenter(this, this)
    }
    private var uid = 0
    private var fleetList = arrayListOf<FleetListData.UserInfo>()
    private lateinit var mFleetAdapter: FleetAdapter
    private val headerView:View by lazy {
        View.inflate(activity,R.layout.item_fleet_header_top,null)
    }
    override val contentLayoutId: Int
        get() = R.layout.fragment_fleet

    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }

    override fun initWidget(root: View) {
        super.initWidget(root)
        root.recycerView.layoutManager = LinearLayoutManager(context)
        mFleetAdapter = FleetAdapter(R.layout.item_fleet_list, fleetList)
        root.recycerView.adapter = mFleetAdapter
        mFleetAdapter.addHeaderView(headerView)
        root.refresh.setOnRefreshListener {
            fleetPresenter.getFleetList(1,20,uid)
        }
        var emptyView = View.inflate(context,R.layout.layout_empty,null)
        var image = emptyView.findViewById<ImageView>(R.id.image_error)
        image.setImageDrawable(resources.getDrawable(R.drawable.ic_empty_list_not_found))
        mFleetAdapter.setEmptyView(emptyView)

        mFleetAdapter.setOnItemClickListener { adapter, view, position ->
            var act = activity as LivePlayActivity
            act.getUserInfo(fleetList[position].uid)
        }
    }

    override fun isRegisterEventBus(): Boolean {
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onGetUpInfoEvent(liveUpData: LiveUpData){
        //roomId = liveUpData.roomId
        uid = liveUpData.roomInfo.uid
    }

    override fun onFirstVisiblity() {
        super.onFirstVisiblity()
        refresh.autoRefresh()
    }

    override fun onGetUpInfo(liveUpData: LiveUpData) {

    }

    override fun onGetUpVideoList(list: List<UpInfoData>) {

    }
    override fun onGetFleetList(fleetListData: FleetListData) {
        fleetList.clear()
        mFleetAdapter.addData(fleetListData.list)
        updateHeaderView(fleetListData.top3)
        refresh.finishRefresh()
    }

    override fun onGetUserInfo(liveUpData: LiveUserData) {

    }
    private val topHeaders by lazy {
        arrayOf<SimpleDraweeView>(
            headerView.findViewById<SimpleDraweeView>(R.id.image_top1),
            headerView.findViewById<SimpleDraweeView>(R.id.image_top2),
            headerView.findViewById<SimpleDraweeView>(R.id.image_top3)
            )
    }
    private val topTexts by lazy {
        arrayOf<TextView>(
            headerView.findViewById(R.id.text_top1),
            headerView.findViewById(R.id.text_top2),
            headerView.findViewById(R.id.text_top3)
        )
    }
    private fun updateHeaderView(top3: List<FleetListData.Top3>) {
       /*for ((index,value) in top3.withIndex()){

       }*/
        for (top in top3){
            var rank = top.rank
            if (rank < 1) rank = 1
            if (rank > 3) rank = 3
            topHeaders[rank - 1].setImageURI(Uri.parse(top.face))
            topTexts[rank -1].setText(top.username)
        }
    }

    override fun onGetUpChatHistory(list: List<LiveChatData.Room>) {
    }
    override fun showDialog() {

    }

    override fun diamissDialog() {

    }

    override fun showToast(text: String?) {
        ToastUtil.show(text)
    }
}