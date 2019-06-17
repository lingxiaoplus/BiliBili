package com.bilibili.lingxiao.home.mikan.ui

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.mikan.adapter.MiKanFallAdapter
import com.bilibili.lingxiao.home.mikan.MiKanPresenter
import com.bilibili.lingxiao.home.mikan.adapter.MikanAdapter
import com.bilibili.lingxiao.home.mikan.MikanView
import com.bilibili.lingxiao.home.mikan.model.MiKanFallData
import com.bilibili.lingxiao.home.mikan.model.MiKanRecommendData
import com.bilibili.lingxiao.home.mikan.model.MikanData
import com.bilibili.lingxiao.home.region.ui.BangumiDetailActivity
import com.bilibili.lingxiao.utils.ToastUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.bilibili.lingxiao.web.WebActivity
import com.camera.lingxiao.common.app.BaseFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_mikan.*
import kotlinx.android.synthetic.main.fragment_mikan.view.*
import kotlinx.android.synthetic.main.item_mikan_fall.*
import kotlinx.android.synthetic.main.mikan_content_cn.*
import kotlinx.android.synthetic.main.mikan_content_cn.view.*
import kotlinx.android.synthetic.main.mikan_content_jp.view.*
import kotlin.properties.Delegates

class MikanFragment :BaseFragment(), MikanView {
    private var miKanPresenter: MiKanPresenter =
        MiKanPresenter(this, this)
    private var mMikanAdapter: MikanAdapter by Delegates.notNull()
    private var mMikanList = arrayListOf<MikanData>()

    override val contentLayoutId: Int
        get() = R.layout.fragment_mikan

    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }

    override fun initWidget(root: View) {
        super.initWidget(root)
        var manager = GridLayoutManager(activity,3)
        manager.setSpanSizeLookup(object :GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                var type = 0
                if (mMikanAdapter.data.size > 0){
                    type = mMikanAdapter.data.get(position).type
                }
                when(type){
                    MikanData.TOP_BAR,MikanData.NEWS,MikanData.LOGIN_HEADER
                    -> return 3
                    MikanData.BANGUMI_ITEM-> return 1
                    else-> return 3
                }

            }
        })
        mMikanAdapter = MikanAdapter(mMikanList)
        root.recyclerview_edit.adapter = mMikanAdapter
        root.recyclerview_edit.layoutManager = manager
        root.refresh.setOnRefreshListener({
            mMikanList.clear()
            miKanPresenter.getBanGuMiRecommend()
        })
        root.refresh.setOnLoadMoreListener {
            var cursor:Long? = mMikanAdapter.data.get(mMikanAdapter.itemCount -1).mikanFall.cursor
            if (cursor != null && cursor != 0L)
                miKanPresenter.getBanGuMiFall(cursor)
        }
        mMikanAdapter.setMultiItemClickListener(object :MikanAdapter.OnMultiItemClickListener{
            override fun onRecommendClick(data: MiKanRecommendData.Result.Recommend.Info) {
                val intent = Intent(
                    context,
                    BangumiDetailActivity::class.java
                )
                intent.putExtra("id",data.seasonId.toString())
                intent.putExtra("type","bangumi")
                startActivity(intent)
            }

            override fun onFootFallClick(data: MiKanFallData.Result) {
                var intent = Intent(context, WebActivity::class.java)
                intent.putExtra("uri",data.link)
                intent.putExtra("title",data.title)
                intent.putExtra("image",data.cover)
                startActivity(intent)
            }
        })
    }

    override fun onFirstVisiblity() {
        super.onFirstVisiblity()
        refresh.autoRefresh()
    }

    override fun onVisiblityChanged(visiblity: Boolean) {
        super.onVisiblityChanged(visiblity)
        if (visiblity && mMikanAdapter.itemCount - mMikanAdapter.headerLayoutCount - mMikanAdapter.footerLayoutCount < 1){
            refresh.autoRefresh()
        }
    }

    override fun onGetMikanRecommend(data: MiKanRecommendData) {
        var loginData = MikanData(MikanData.LOGIN_HEADER)
        mMikanAdapter.addData(loginData)
        assembleData(resources.getString(R.string.bangumi_cn),data.result.recommendCn.recommend,data.result.recommendCn.foot)
        assembleData(resources.getString(R.string.bangumi_jp),data.result.recommendJp.recommend,data.result.recommendJp.foot)

        var editTopBarData = MikanData(MikanData.TOP_BAR)
        editTopBarData.titleBar = MikanData.TitleBarData(resources.getString(R.string.bangumi_edit),false)
        mMikanAdapter.addData(editTopBarData)
        miKanPresenter.getBanGuMiFall(0L)
    }

    override fun onGetMikanFall(data: MiKanFallData) {
        for (item in data.result){
            var newsData = MikanData(MikanData.NEWS)
            newsData.mikanFall = item
            mMikanAdapter.addData(newsData)
        }

        refresh.finishRefresh()
        refresh.finishLoadMore()
    }

    fun assembleData(type:String,recommend:List<MiKanRecommendData.Result.Recommend.Info>,
                     foots:List<MiKanRecommendData.Result.Recommend.Foot>){
        var cnTopBarData = MikanData(MikanData.TOP_BAR)
        cnTopBarData.titleBar = MikanData.TitleBarData(type,true)
        mMikanAdapter.addData(cnTopBarData)
        for (item in recommend){
            var cnData = MikanData(MikanData.BANGUMI_ITEM)
            cnData.mikanRecommend = item
            mMikanAdapter.addData(cnData)
        }
        if (foots.size > 0){
            var cnNewsData = MikanData(MikanData.NEWS)
            cnNewsData.mikanFall = MiKanFallData.Result(
                foots[0].cover,
                0L,foots[0].desc,
                foots[0].id,0,
                foots[0].link,
                foots[0].title,0,
                foots[0].wid)
            mMikanAdapter.addData(cnNewsData)
        }
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
}