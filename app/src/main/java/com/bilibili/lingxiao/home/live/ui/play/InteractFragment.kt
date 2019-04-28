package com.bilibili.lingxiao.home.live.ui.play

import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.model.*
import com.bilibili.lingxiao.home.live.presenter.InteractPresenter
import com.bilibili.lingxiao.home.live.ui.LivePlayActivity
import com.bilibili.lingxiao.home.live.view.LivePlayView
import com.bilibili.lingxiao.utils.ToastUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseFragment
import com.camera.lingxiao.common.utills.PopwindowUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_interact.*
import kotlinx.android.synthetic.main.fragment_interact.view.*
import kotlinx.android.synthetic.main.layout_header_room_info.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.Exception


class InteractFragment :BaseFragment(),LivePlayView{
    private val TAG = InteractFragment::class.java.simpleName
    private var chatList = arrayListOf<LiveChatData.Room>()
    private lateinit var chatAdapter: ChatAdapter
    private var presenter = InteractPresenter(this,this)
    private var room_id = 0
    override val contentLayoutId: Int
        get() = R.layout.fragment_interact

    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }

    override fun initWidget(root: View) {
        super.initWidget(root)
        chatAdapter = ChatAdapter(R.layout.item_live_chat,chatList)
        root.recycerView.layoutManager = LinearLayoutManager(context)
        root.recycerView.adapter = chatAdapter
        root.refresh.setOnLoadMoreListener {
            presenter.getChatHistory(room_id)
        }
        root.refresh.setHeaderHeight(0f)

        chatAdapter.setOnItemClickListener { adapter, view, position ->
            var act = activity as LivePlayActivity
            act.getUserInfo(chatList[position].uid)
        }
    }

    override fun isRegisterEventBus(): Boolean {
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onGetHomeInfo(live: LiveData.RecommendDataBean.LivesBean){
        image_header.setImageURI(Uri.parse(live.owner.face))
        up_name.text = live.owner.name
        people_num.text = "人气：" + live.online
        //fensi_num.text = "粉丝：" +
        category.text = live.area
        room_id = live.room_id
        presenter.getChatHistory(room_id)
    }

    override fun onGetUpInfo(liveUpData: LiveUpData) {

    }

    override fun onGetFleetList(fleetListData: FleetListData) {

    }

    override fun onGetUpVideoList(list: List<UpInfoData>) {
    }

    override fun onGetUpChatHistory(list: List<LiveChatData.Room>) {
        for (room in list){
            if (chatList.contains(room)){
                continue
            }
            chatAdapter.addData(room)
        }
        refresh.finishLoadMore()
    }

    override fun onGetUserInfo(liveUpData: LiveUserData) {

    }
    override fun showDialog() {
    }

    override fun diamissDialog() {
    }

    override fun showToast(text: String?) {
        ToastUtil.show(text)
    }

    inner class ChatAdapter(layoutResId: Int, data: MutableList<LiveChatData.Room>?) :
        BaseQuickAdapter<LiveChatData.Room, BaseViewHolder>(layoutResId, data) {
        override fun convert(helper: BaseViewHolder, item: LiveChatData.Room) {
            helper.setText(R.id.user_level,"UL " + item.userLevel[0])
            helper.setText(R.id.username, item.nickname + ": ")
            helper.setText(R.id.message,item.text)
            /*if (item.userLevel.size > 3){
                try {
                    printRGBA(item.userLevel[2] as Double)
                }catch (ex :Exception){
                    ex.printStackTrace()
                }
            }*/
        }

        @Throws
        fun printRGBA(color: Int) {
            val alpha = color.ushr(24)
            val r = color and 0xff0000 shr 16
            val g = color and 0xff00 shr 8
            val b = color and 0xff
            Log.e(TAG,"颜色rgb值$alpha, $r, $g, $b")
        }
    }
}