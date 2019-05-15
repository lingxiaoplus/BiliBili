package com.bilibili.lingxiao.play.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import com.bilibili.lingxiao.HttpTrans
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.play.DoubleCommentView
import com.bilibili.lingxiao.play.adapter.CommentAdapter
import com.bilibili.lingxiao.play.model.CommentData
import com.bilibili.lingxiao.utils.DateUtil
import com.bilibili.lingxiao.utils.StringUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.observer.HttpRxCallback
import com.camera.lingxiao.common.utills.LogUtils
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.fragment_comment.view.*
import kotlinx.android.synthetic.main.fragment_comment_detail.*
import kotlinx.android.synthetic.main.fragment_fans_detail.*
import kotlinx.android.synthetic.main.item_comment.*
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.layout_header_comment_detail.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class CommentDetailFragment : DialogFragment(){
    var height = 100
    private val levelImages = arrayOf(
        R.drawable.mall_mine_vip_level_0,
        R.drawable.mall_mine_vip_level_1,
        R.drawable.mall_mine_vip_level_2,
        R.drawable.mall_mine_vip_level_3,
        R.drawable.mall_mine_vip_level_4,
        R.drawable.mall_mine_vip_level_5,
        R.drawable.mall_mine_vip_level_6
    )
    private lateinit var mAdapter: CommentAdapter
    private var mCommentList = arrayListOf<CommentData.Reply>()
    private lateinit var headerRootView:View
    private var oid = 0
    private var rootId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.fragmentDialog)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window.setWindowAnimations(R.style.contextMenuAnim)
        //UIUtil.getUiComponent().inject(this)
        val root = inflater.inflate(R.layout.fragment_comment_detail, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var recommendManager = LinearLayoutManager(context)
        recycerView.layoutManager = recommendManager
        mAdapter = CommentAdapter(mCommentList)
        headerRootView = View.inflate(context,R.layout.layout_header_comment_detail,null)
        mAdapter.addHeaderView(headerRootView)
        recycerView.adapter = mAdapter
        image_close.setOnClickListener {
            dismiss()
        }
        refresh.setOnRefreshListener {
            getDoubleComment()
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        var win = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable( ColorDrawable(Color.WHITE))
        var  dm = DisplayMetrics()
        getActivity()!!.getWindowManager().getDefaultDisplay().getMetrics(dm)
        var params = win.getAttributes()
        params.gravity = Gravity.BOTTOM
        //使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width =  ViewGroup.LayoutParams.MATCH_PARENT
        params.height = height
        win.setAttributes(params)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onGetCommentEvent(reply : CommentData.Reply){
        EventBus.getDefault().removeStickyEvent(reply)
        this.oid = reply.oid
        this.rootId = reply.rpid
        refresh.autoRefresh()
    }

    fun getDoubleComment(){
        if (oid == 0 || rootId == 0){
            refresh.finishRefresh()
            refresh.finishLoadMoreWithNoMoreData()
            return
        }
        var httpTrans :HttpTrans = HttpTrans(parentFragment!! as CommentFragment)
        httpTrans.getDoubleComment(oid,rootId,20,object :HttpRxCallback<Any>(){
            override fun onSuccess(res: Any?) {
                mCommentList.clear()
                var lists = res as Array<*>
                var reply = lists[0] as CommentData.Reply
                //LogUtils.d("获取到的楼中楼回复${reply}")
                headerRootView.header.setImageURI(Uri.parse(reply.member.avatar))
                headerRootView.username.setText(reply.member.uname)
                headerRootView.comment_desc.setText(reply.content.message)
                headerRootView.recommend_num.setText(StringUtil.getBigDecimalNumber(reply.like))
                var level = reply.member.levelInfo.currentLevel
                if (level > 6 || level < 0) level = 0
                headerRootView.image_level.setImageResource(levelImages[level])
                var floor = "#" +reply.floor + "  " + DateUtil.convertTimeToFormat(reply.ctime)
                headerRootView.build_num.setText(floor)
                headerRootView.text_count.setText("相关回复共${reply.rcount}条")
                reply.replies?.let {
                    mAdapter.addData(it)
                }
                refresh.finishRefresh()
                refresh.finishLoadMoreWithNoMoreData()
            }

            override fun onError(code: Int, desc: String?) {

            }

            override fun onCancel() {

            }
        })
    }

}