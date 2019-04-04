package com.bilibili.lingxiao.play

import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.dagger.DaggerUiComponent
import com.bilibili.lingxiao.home.recommend.RecommendData
import com.bilibili.lingxiao.home.recommend.RecommendView
import com.bilibili.lingxiao.utils.ToastUtil
import com.camera.lingxiao.common.app.BaseFragment
import com.camera.lingxiao.common.utills.LogUtils
import kotlinx.android.synthetic.main.fragment_introduce.*
import kotlinx.android.synthetic.main.fragment_introduce.view.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class IntroduceFragment :BaseFragment(),RecommendView{

    var mEndPageList = arrayListOf<EndPageData>()
    lateinit var endPageAdapter: EndPageAdapter
    private var videoPresenter = VideoPresenter(this,this)
    override val contentLayoutId: Int
        get() = R.layout.fragment_introduce

    override fun initInject() {
        super.initInject()
        DaggerUiComponent.builder().build().inject(this)
    }
    override fun initWidget(root: View) {
        super.initWidget(root)
        var manager = GridLayoutManager(context,5)
        root.endpage_recycler.layoutManager = manager
        endPageAdapter = EndPageAdapter(R.layout.item_endpage,mEndPageList)
        root.endpage_recycler.adapter = endPageAdapter
        root.endpage_recycler.isNestedScrollingEnabled = false

    }

    data class EndPageData(val icon:Drawable,val message:String){

    }

    override fun isRegisterEventBus(): Boolean {
        return true
    }

    /**
     * 粘性事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public fun onGetVideoDetail(data: RecommendData) {
        var recommend = EndPageData(resources.getDrawable(R.drawable.ic_recommend),""+data.like)
        var dislike = EndPageData(resources.getDrawable(R.drawable.ic_dislike),"不喜欢")
        var coin = EndPageData(resources.getDrawable(R.drawable.ic_coin),""+data.coin)
        var collect = EndPageData(resources.getDrawable(R.drawable.ic_collect),""+data.reply)
        var share = EndPageData(resources.getDrawable(R.drawable.ic_share),""+data.share)
        endPageAdapter.addData(recommend)
        endPageAdapter.addData(dislike)
        endPageAdapter.addData(coin)
        endPageAdapter.addData(collect)
        endPageAdapter.addData(share)
        img_head.setImageURI(Uri.parse(data.face))
        username.setText(data.name)
        fensi.setText("" + data.reply + "个粉丝")
        fold_layout.setTitleText(data.title)
        videoPresenter.getDetailInfo(1,data.param)

    }

    override fun onGetRecommendData(recommendData: List<RecommendData>) {

    }

    override fun onGetVideoDetail(data: VideoDetailData) {
        fold_layout.setMessageText(data.description)
    }

    override fun showDialog() {

    }

    override fun diamissDialog() {

    }

    override fun showToast(text: String?) {
        ToastUtil.show(text)
    }
}