package com.bilibili.lingxiao.home.live.ui.play

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.adapter.FansAdapter
import com.bilibili.lingxiao.home.live.model.FansGoldListData
import com.bilibili.lingxiao.home.live.presenter.FansDetailPresenter
import com.bilibili.lingxiao.home.live.ui.LivePlayActivity
import com.bilibili.lingxiao.home.live.view.FansDetailView
import com.bilibili.lingxiao.utils.ToastUtil
import com.camera.lingxiao.common.app.BaseFragment
import kotlinx.android.synthetic.main.normal_refresh_view.*
import kotlinx.android.synthetic.main.normal_refresh_view.view.*

class FansDetailFragment :BaseFragment(), FansDetailView {
    var position = 0
    private var roomId = 0
    private var uid = 0
    private val presenter: FansDetailPresenter by lazy {
        FansDetailPresenter(this, this)
    }
    private lateinit var fansAdapter : FansAdapter
    private var fansList = arrayListOf<FansGoldListData.FansInfo>()
    override val contentLayoutId: Int
        get() = R.layout.normal_refresh_view


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
        fansAdapter = FansAdapter(R.layout.item_fans_list, fansList)
        fansAdapter.setDuration(800)
        fansAdapter.openLoadAnimation({ view ->
            arrayOf<Animator>(
                ObjectAnimator.ofFloat(
                    view,
                    "scaleY",
                    0f,
                    1.05f,
                    1f
                ), ObjectAnimator.ofFloat(view, "scaleX", 0f, 1.05f, 1f)
            )
        })
        var manager = LinearLayoutManager(context)

        root.recycerView.layoutManager = manager
        root.recycerView.adapter = fansAdapter
        //root.recycerView.setItemViewCacheSize(20)  //设置viewholder缓存大小，如果recyclerview数量太少，会存在数据错乱的问题
        root.refresh.setOnRefreshListener {
            if (position == 0){
                presenter.getFansGoldList(roomId,uid)
            }else if (position == 1){
                presenter.getLiveToDayList(roomId,uid)
            }else {
                presenter.getLiveFansList(roomId,uid)
            }
        }
        var emptyView = View.inflate(context,R.layout.layout_empty,null)
        var image = emptyView.findViewById<ImageView>(R.id.image_error)
        image.setImageDrawable(resources.getDrawable(R.drawable.ic_empty_cute_girl_box))
        fansAdapter.setEmptyView(emptyView)
        fansAdapter.setOnItemClickListener { adapter, view, position ->
            var act = activity as LivePlayActivity
            act.getUserInfo(fansList[position].uid)
        }
    }

    override fun onFirstVisiblity() {
        super.onFirstVisiblity()
        refresh.autoRefresh()
    }

    override fun onGetFansGoldList(fansList: FansGoldListData) {
        this.fansList.clear()
        fansAdapter.addData(fansList.list)
        refresh.finishRefresh()
    }

    override fun showDialog() {

    }

    override fun diamissDialog() {

    }

    override fun showToast(text: String?) {
        ToastUtil.show(text)
        refresh.finishRefresh()
    }
}