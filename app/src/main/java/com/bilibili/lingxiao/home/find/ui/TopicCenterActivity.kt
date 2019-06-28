package com.bilibili.lingxiao.home.find.ui

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.find.TopicView
import com.bilibili.lingxiao.home.find.model.TopicCardData
import com.bilibili.lingxiao.home.find.presenter.TopicCenterPresenter
import com.bilibili.lingxiao.web.WebActivity
import com.camera.lingxiao.common.app.BaseActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.activity_topic_center.*
import kotlinx.android.synthetic.main.title_bar.*

class TopicCenterActivity : BaseActivity(),TopicView{
    private var mTopicList = arrayListOf<TopicCardData.Item>()
    private lateinit var mPresenter:TopicCenterPresenter
    private lateinit var mTopicAdapter :TopicAdapter
    private var page = 1
    private val pageSize = 20
    private val pageType by lazy {
        intent.getStringExtra("type")
    }
    override val contentLayoutId: Int
        get() = R.layout.activity_topic_center

    override fun initWidget() {
        super.initWidget()
        setToolbarBack(title_bar)
        title_bar.title = pageType
        mPresenter = TopicCenterPresenter(this,this)
        mTopicAdapter = TopicAdapter(R.layout.item_topic_card, mTopicList)
        recycerView.layoutManager = LinearLayoutManager(this)
        recycerView.adapter = mTopicAdapter
        refresh.autoRefresh()
        refresh.setOnRefreshListener {
            mTopicList.clear()
            if (pageType.equals(resources.getString(R.string.find_line_topic)))
                mPresenter.getTopicCenter(page,pageSize)
            else
                mPresenter.getActivityCenter(page,pageSize)
        }
        refresh.setOnLoadMoreListener{
            page++
            if (pageType.equals(resources.getString(R.string.find_line_topic)))
                mPresenter.getTopicCenter(page,pageSize)
            else
                mPresenter.getActivityCenter(page,pageSize)
        }
        mTopicAdapter.setOnItemClickListener { adapter, view, position ->
            var intent = Intent(applicationContext, WebActivity::class.java)
            intent.putExtra("uri",mTopicList[position].link)
            intent.putExtra("title",mTopicList[position].title)
            intent.putExtra("image",mTopicList[position].cover)
            startActivity(intent)
        }
    }

    override fun showDialog() {

    }

    override fun onGetTopicResult(data: TopicCardData) {
        mTopicAdapter.addData(data.list)
        refresh.finishRefresh()
        refresh.finishLoadMore()
    }

    override fun diamissDialog() {

    }

    override fun showToast(text: String?) {

    }

    inner class TopicAdapter(layout:Int,data: MutableList<TopicCardData.Item>?) :
        BaseQuickAdapter<TopicCardData.Item, BaseViewHolder>(layout,data) {
        override fun convert(helper: BaseViewHolder, item: TopicCardData.Item) {
            helper.setText(R.id.title,item.title)
            var imageCover = helper.getView<SimpleDraweeView>(R.id.image_cover)
            imageCover.setImageURI(Uri.parse(item.cover))
            if (item.state == null){
                helper.setVisible(R.id.image_state,false)
            }else {
                helper.setVisible(R.id.image_state,true)
                helper.setImageResource(R.id.image_state,
                    if (item.state==0)
                        R.drawable.ic_badge_going
                    else
                        R.drawable.ic_badge_end)
            }
        }
    }
}
