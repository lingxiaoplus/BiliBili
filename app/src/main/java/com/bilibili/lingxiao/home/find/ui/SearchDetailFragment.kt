package com.bilibili.lingxiao.home.find.ui

import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bilibili.lingxiao.GlobalProperties
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.find.model.SearchResultData
import com.bilibili.lingxiao.utils.StringUtil
import com.camera.lingxiao.common.app.BaseFragment
import com.camera.lingxiao.common.utills.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.layout_empty.view.*
import kotlinx.android.synthetic.main.normal_refresh_view.*
import kotlinx.android.synthetic.main.normal_refresh_view.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

class SearchDetailFragment :BaseFragment(){
    lateinit var videoDetailAdapter: VideoDetailAdapter
    var mRecommendList = arrayListOf<SearchResultData.Item>()
    var page = 1
    override val contentLayoutId: Int
        get() = R.layout.normal_refresh_view

    override fun initWidget(root: View) {
        super.initWidget(root)
        initRecyclerView(root)
    }

    private fun initRecyclerView(root: View) {
        var recommendManager = LinearLayoutManager(context)
        root.recycerView.layoutManager = recommendManager
        videoDetailAdapter =
            VideoDetailAdapter(R.layout.item_videodetail_recommend, mRecommendList)
        root.recycerView.adapter = videoDetailAdapter

        var empty = View.inflate(context,R.layout.layout_empty,null)
        empty.image_error.setImageResource(R.drawable.ic_search_holder_default)
        empty.text_desc.text = getString(R.string.find_searching)
        videoDetailAdapter.setEmptyView(empty)
        //root.refresh.autoRefresh()
        root.refresh.setOnRefreshListener {
            mRecommendList.clear()
            var act = activity as SearchDetailActivity
            act.getSearchResult(1)
        }
        root.refresh.setOnLoadMoreListener {
            var act = activity as SearchDetailActivity
            act.getSearchResult(++page)
        }
    }

    override fun isRegisterEventBus(): Boolean {
        return true
    }


    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onGetSearchData(item: List<SearchResultData.Item>){
        EventBus.getDefault().removeStickyEvent(item)
        videoDetailAdapter.addData(item)
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