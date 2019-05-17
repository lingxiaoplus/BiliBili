package com.bilibili.lingxiao.home.live.ui.play

import android.net.Uri
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.GridLayoutManager
import android.text.Html
import android.util.Log
import android.view.View
import com.bilibili.lingxiao.GlobalProperties
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.live.model.*
import com.bilibili.lingxiao.home.live.presenter.UpVideoPresenter
import com.bilibili.lingxiao.home.live.view.LivePlayView
import com.bilibili.lingxiao.home.recommend.model.RecommendData
import com.bilibili.lingxiao.home.recommend.ui.RecommendRecyAdapter
import com.bilibili.lingxiao.utils.StringUtil
import com.bilibili.lingxiao.utils.ToastUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseFragment
import com.camera.lingxiao.common.app.BaseView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.fragment_up_info.*
import kotlinx.android.synthetic.main.fragment_up_info.view.*
import kotlinx.android.synthetic.main.layout_header_room_info.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class UpInfoFragment :BaseFragment() , LivePlayView {
    private val TAG = UpInfoFragment::class.java.simpleName
    private var ruid = 0
    private var videoList = arrayListOf<UpInfoData>()
    private lateinit var mAdapter: VideoRecyAdapter
    private val upVideoPresenter: UpVideoPresenter by lazy {
        UpVideoPresenter(this,this)
    }
    private var mPage = 1
    override val contentLayoutId: Int
        get() = R.layout.fragment_up_info
    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }

    override fun initWidget(root: View) {
        super.initWidget(root)
        var manager = GridLayoutManager(context,2)
        manager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                if(position == 0 && mAdapter.itemCount == 1){
                    return 2
                }else{
                    return 1
                }

            }
        })
        mAdapter = VideoRecyAdapter(R.layout.item_video,videoList)
        root.recycerView.adapter = mAdapter
        root.recycerView.layoutManager = manager
        mAdapter.setEmptyView(View.inflate(context,R.layout.layout_empty,null))
        root.refresh.setOnRefreshListener{
            mPage = 1
            videoList.clear()
            upVideoPresenter.getUpVideo(mPage,20,ruid)
        }
        root.refresh.setOnLoadMoreListener{
            mPage++
            upVideoPresenter.getUpVideo(mPage,20,ruid)
        }
    }

    override fun onFirstVisiblity() {
        super.onFirstVisiblity()
        refresh.autoRefresh()
    }
    override fun isRegisterEventBus(): Boolean {
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onGetHomeInfo(live: LiveData.RecommendDataBean.LivesBean){
        image_header.setImageURI(Uri.parse(live.owner.face))
        up_name.text = live.owner.name
        //category.text = live.area
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onGetUpInfoEvent(liveUpData: LiveUpData){
        ruid = liveUpData.roomInfo.uid
        people_num.text = "房间号：" + liveUpData.roomInfo.roomId
        fensi_num.text = "粉丝：" + StringUtil.getBigDecimalNumber(liveUpData.anchorInfo.relationInfo.attention)
        Log.d(TAG,"")
        certification.text = liveUpData.anchorInfo.baseInfo.officialInfo.title
        text_broadcast.text = liveUpData.roomInfo.title
        if (liveUpData.roomInfo.description != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //FROM_HTML_MODE_COMPACT 块元素之间使用一个换行符分隔
                text_broadcast.text = Html.fromHtml(liveUpData.roomInfo.description,Html.FROM_HTML_MODE_COMPACT)
            }else{
                //Bug，解析后的文本，块元素之间换行默认为两行，而且无法更改。
                text_broadcast.text = Html.fromHtml(liveUpData.roomInfo.description)
            }
        }else{
            text_broadcast.text = "暂时没有"
        }
    }

    override fun onGetUpVideoList(list: List<UpInfoData>) {
        mAdapter.addData(list)
        refresh.finishRefresh()
        refresh.finishLoadMore()
    }

    override fun onGetUserInfo(liveUpData: LiveUserData) {

    }
    override fun onGetFleetList(fleetListData: FleetListData) {
    }
    override fun onGetUpInfo(liveUpData: LiveUpData) {
    }

    override fun onGetUpChatHistory(list: List<LiveChatData.Room>) {
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

    inner class VideoRecyAdapter(layoutResId: Int, data: List<UpInfoData>?) :
        BaseQuickAdapter<UpInfoData, BaseViewHolder>(layoutResId, data) {
        override fun convert(helper: BaseViewHolder, item: UpInfoData) {
            helper.setText(R.id.play_title,item.title)
            helper.setText(R.id.play_number,StringUtil.getBigDecimalNumber(item.stat.favorite))
            helper.setText(R.id.comment_number,StringUtil.getBigDecimalNumber(item.stat.view))
            helper.setText(R.id.category_name,"" + item.tname)
            helper.getView<ConstraintLayout>(R.id.cons_category).visibility = View.GONE
            helper.addOnClickListener(R.id.image_more)
            var image : SimpleDraweeView = helper.getView(R.id.play_image)
            try {
                image.setImageURI(Uri.parse(item.pic + GlobalProperties.IMAGE_RULE_240_150))
            }catch (ex:Exception){
                ex.printStackTrace()
            }

        }
    }
}