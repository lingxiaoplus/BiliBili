package com.bilibili.lingxiao.play.ui

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.dagger.DaggerUiComponent
import com.bilibili.lingxiao.home.recommend.model.RecommendData
import com.bilibili.lingxiao.home.recommend.view.RecommendView
import com.bilibili.lingxiao.play.adapter.EndPageAdapter
import com.bilibili.lingxiao.play.adapter.VideoDetailAdapter
import com.bilibili.lingxiao.play.VideoPresenter
import com.bilibili.lingxiao.play.model.CommentData
import com.bilibili.lingxiao.play.model.VideoDetailData
import com.bilibili.lingxiao.play.model.VideoRecoData
import com.bilibili.lingxiao.utils.StringUtil
import com.bilibili.lingxiao.utils.ToastUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.bilibili.lingxiao.widget.FoldableLayout
import com.camera.lingxiao.common.app.BaseFragment
import com.camera.lingxiao.common.utills.LogUtils
import com.camera.lingxiao.common.utills.PopwindowUtil
import kotlinx.android.synthetic.main.fragment_introduce.*
import kotlinx.android.synthetic.main.fragment_introduce.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

class IntroduceFragment :BaseFragment(), RecommendView {
    val TAG = IntroduceFragment::class.java.simpleName
    var mEndPageList = arrayListOf<EndPageData>()
    var mRecommendList = arrayListOf<VideoRecoData.VideoInfo>()
    lateinit var endPageAdapter: EndPageAdapter
    lateinit var videoDetailAdapter: VideoDetailAdapter
    private var videoPresenter = VideoPresenter(this, this)
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
        endPageAdapter = EndPageAdapter(R.layout.item_endpage, mEndPageList)
        root.endpage_recycler.adapter = endPageAdapter
        root.endpage_recycler.isNestedScrollingEnabled = false
        //下面的推荐视频
        var recommendManager = LinearLayoutManager(context)
        root.recommend_recycler.layoutManager = recommendManager
        videoDetailAdapter =
            VideoDetailAdapter(R.layout.item_videodetail_recommend, mRecommendList)
        root.recommend_recycler.adapter = videoDetailAdapter
        root.recommend_recycler.isNestedScrollingEnabled = false

        root.fold_layout.setCollapseListener(object :FoldableLayout.CollapseListener{
            override fun onCollapseChanged(collapsed: Boolean) {
                root.fold_message.changeStatus()
            }
        })
        videoDetailAdapter.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.more ->{
                    val popwindowUtil = PopwindowUtil.PopupWindowBuilder(activity!!)
                        .setView(R.layout.pop_watch_later)
                        .setAnimationStyle(R.style.pop_watch_later_Anim)
                        //.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.black_alpha_128)))
                        .setFocusable(true)
                        .setTouchable(true)
                        .setOutsideTouchable(true)
                        .create()
                    var intArray = IntArray(2)
                    view.getLocationInWindow(intArray)
                    popwindowUtil.mContentView?.let {
                        it.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
                        intArray[0] = UIUtil.getScreenWidth(context) -
                                UIUtil.getDimen(R.dimen.pop_watch_later_xoffset) - it.measuredWidth
                        intArray[1] -= UIUtil.getDimen(R.dimen.pop_watch_later_yoffset) + it.measuredHeight
                        //popwindowUtil.showAsDropDown(view,0,-view.height,Gravity.LEFT or Gravity.TOP)
                        popwindowUtil.showAtLocation(view,intArray[0],intArray[1],Gravity.NO_GRAVITY,0.6f)
                        popwindowUtil.getView<View>(R.id.watch_later)!!.setOnClickListener {
                            ToastUtil.show("已添加到稍后再看列表")
                            popwindowUtil.dissmiss()
                        }
                    }
                }
            }
        }
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
        //针对sticky事件  eventBus会缓存在事件发射队列，
        // 若是订阅关系已经存在则发射出去，但不会销毁。下次再次订阅，会继续接收上一次事件。所以这里接收到了需要移除
        if (endPageAdapter.itemCount >= 5){
            EventBus.getDefault().removeStickyEvent(data)
            return
        }
        var recommend = EndPageData(
            resources.getDrawable(R.drawable.ic_recommend),
            "" + data.like
        )
        var dislike = EndPageData(
            resources.getDrawable(R.drawable.ic_dislike),
            "不喜欢"
        )
        var coin = EndPageData(
            resources.getDrawable(R.drawable.ic_coin),
            "" + data.coin
        )
        var collect = EndPageData(
            resources.getDrawable(R.drawable.ic_collect),
            "" + data.reply
        )
        var share = EndPageData(
            resources.getDrawable(R.drawable.ic_share),
            "" + data.share
        )
        endPageAdapter.addData(recommend)
        endPageAdapter.addData(dislike)
        endPageAdapter.addData(coin)
        endPageAdapter.addData(collect)
        endPageAdapter.addData(share)
        img_head.setImageURI(Uri.parse(data.face))
        username.setText(data.name)
        //var tNames  = data.tname.split("·")
        data.tname?.let {
            //type_name.setTitleText(it)
            type_name.setText(it)
        }
        fensi.text = "${StringUtil.getBigDecimalNumber(data.reply)}个粉丝"
        data.title?.let {
            fold_layout.setTitleText(it)
        }
        damku_num.text = StringUtil.getBigDecimalNumber(data.danmaku)
        av_num.text = "   av${data.param}"
        videoPresenter.getDetailInfo(1,data.param)
    }

    override fun onGetRecommendData(recommendData: List<RecommendData>) {

    }

    override fun onGetVideoDetail(data: VideoDetailData) {
        data.description?.let {
            fold_message.setTitleText(it)
        }
        //Log.d(TAG,"设置描述信息${data}")
        play_num.text = StringUtil.getBigDecimalNumber(data.play)
        data.created_at?.let {
            var dataArray = it.split("\\s+")
            if (dataArray.size > 1){
                av_num.text = "  ${dataArray[0] + av_num.text.toString()}"
            }else{
                av_num.text = "  ${it + av_num.text.toString()}"
            }
        }
        //先获取到视频信息，之后再获取推荐列表
        val random = Random()
        val ret = random.nextInt(100)
        videoPresenter.getRecommendList(data.tid,ret)
    }

    override fun onGetVideoRecommend(videoRecoData: VideoRecoData) {
        mRecommendList.addAll(videoRecoData.list)
        videoDetailAdapter.notifyDataSetChanged()
    }

    override fun onGetVideoComment(commentData: CommentData) {

    }

    override fun showDialog() {

    }

    override fun diamissDialog() {

    }

    override fun showToast(text: String?) {
        ToastUtil.show(text)
    }
}