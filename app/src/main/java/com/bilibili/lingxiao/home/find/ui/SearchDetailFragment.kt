package com.bilibili.lingxiao.home.find.ui

import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bilibili.lingxiao.GlobalProperties
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.find.model.SearchResultData
import com.bilibili.lingxiao.utils.StringUtil
import com.camera.lingxiao.common.app.BaseFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.normal_refresh_view.*
import kotlinx.android.synthetic.main.normal_refresh_view.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SearchDetailFragment :BaseFragment(){
    lateinit var videoDetailAdapter: VideoDetailAdapter
    var mRecommendList = arrayListOf<SearchResultData.Item>()
    override val contentLayoutId: Int
        get() = R.layout.normal_refresh_view

    override fun initWidget(root: View) {
        super.initWidget(root)
        var recommendManager = LinearLayoutManager(context)
        root.recycerView.layoutManager = recommendManager
        videoDetailAdapter =
            VideoDetailAdapter(R.layout.item_videodetail_recommend, mRecommendList)
        root.recycerView.adapter = videoDetailAdapter
        root.refresh.autoRefresh()
    }

    override fun isRegisterEventBus(): Boolean {
        return true
    }


    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onGetSearchData(item: List<SearchResultData.Item>){
        EventBus.getDefault().removeStickyEvent(item)
        mRecommendList.addAll(item)
        refresh.finishRefresh()
        refresh.finishLoadMore()
    }

    inner class VideoDetailAdapter : BaseQuickAdapter<SearchResultData.Item, BaseViewHolder> {
        constructor(layoutResId: Int, data: List<SearchResultData.Item>):super(layoutResId,data)
        override fun convert(helper: BaseViewHolder, item: SearchResultData.Item) {
            when(item.goto){
                "av" ->{
                    helper.setText(R.id.title,item.title)
                    helper.setText(R.id.up_name,item.author)
                    helper.setText(R.id.play_num, StringUtil.getBigDecimalNumber(item.play))
                    helper.setText(R.id.damku_num,StringUtil.getBigDecimalNumber(item.danmaku))
                    var image : SimpleDraweeView = helper.getView(R.id.cover_image)
                    image.setImageURI(Uri.parse(item.cover + GlobalProperties.IMAGE_RULE_240_150))
                    helper.addOnClickListener(R.id.more)
                }
                "author" -> {
                    //直播
                }
                "game" ->{

                }
            }
        }
    }
}