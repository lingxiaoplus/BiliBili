package com.bilibili.lingxiao.home.live.play.fans

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.utils.ToastUtil
import com.camera.lingxiao.common.app.BaseFragment
import kotlinx.android.synthetic.main.fragment_fans_detail.*
import kotlinx.android.synthetic.main.fragment_fans_detail.view.*

class FansDetailFragment :BaseFragment(),FansDetailView{
    var position = 0
    private var roomId = 0
    private var uid = 0
    private val presenter:FansDetailPresenter by lazy {
        FansDetailPresenter(this,this)
    }
    private lateinit var fansAdapter :FansAdapter
    private var fansList = arrayListOf<FansGoldListData.FansInfo>()
    override val contentLayoutId: Int
        get() = R.layout.fragment_fans_detail


    override fun initArgs(bundle: Bundle?) {
        super.initArgs(bundle)
        bundle?.let {
            position = it.getInt("position")
            roomId = it.getInt("roomId", 0)
            uid = it.getInt("uid", 0)
        }
    }

    override fun initWidget(root: View) {
        super.initWidget(root)
        fansAdapter = FansAdapter(R.layout.item_fans_list,fansList)
        var manager = LinearLayoutManager(context)
        root.recycerView.layoutManager = manager
        root.recycerView.adapter = fansAdapter
        root.refresh.setOnRefreshListener {
            if (position == 0){
                presenter.getFansGoldList(roomId,uid)
            }else if (position == 1){
                presenter.getLiveToDayList(roomId,uid)
            }else {
                presenter.getLiveFansList(roomId,uid)
            }
        }
    }

    override fun onFirstVisiblity() {
        super.onFirstVisiblity()
        refresh.autoRefresh()
    }

    override fun onGetFansGoldList(fansList:FansGoldListData) {
        fansAdapter.setNewData(fansList.list)
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