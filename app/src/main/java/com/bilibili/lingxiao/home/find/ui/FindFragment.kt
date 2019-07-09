package com.bilibili.lingxiao.home.find.ui

import android.content.Intent
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bilibili.lingxiao.GlobalProperties
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.database.RegionTable
import com.bilibili.lingxiao.home.find.presenter.FindPresenter
import com.bilibili.lingxiao.home.find.FindView
import com.bilibili.lingxiao.home.find.model.HotWordsData
import com.bilibili.lingxiao.home.find.model.SearchResultData
import com.bilibili.lingxiao.home.region.model.RegionData
import com.bilibili.lingxiao.user.LoginActivity
import com.bilibili.lingxiao.utils.ToastUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.bilibili.lingxiao.web.WebActivity
import com.bilibili.lingxiao.widget.LaybelLayout

import com.camera.lingxiao.common.app.BaseFragment
import com.camera.lingxiao.common.utills.PopwindowUtil
import com.raizlabs.android.dbflow.sql.language.SQLite
import kotlinx.android.synthetic.main.fragment_find.*
import kotlinx.android.synthetic.main.fragment_find.view.*
import org.greenrobot.eventbus.EventBus

class FindFragment :BaseFragment(), FindView,View.OnClickListener{
    private val TAG = FindFragment::class.java.simpleName
    private var presenter: FindPresenter =
        FindPresenter(this, this)
    override val contentLayoutId: Int
        get() = R.layout.fragment_find

    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)
    }

    override fun initWidget(root: View) {
        super.initWidget(root)
        root.interest_group.setOnClickListener(this)
        root.topic_center.setOnClickListener(this)
        root.activity_center.setOnClickListener(this)
        root.black_door.setOnClickListener(this)
        root.origin_rank_list.setOnClickListener(this)
        root.all_rank_list.setOnClickListener(this)
        root.round_shop.setOnClickListener(this)
        root.show_more.setOnClickListener {
            if (root.laybel.isCollapsed()){
                text_show_more.text = "折叠"
                image_show_more.setImageResource(R.drawable.ic_arrow_up)
            }else{
                image_show_more.setImageResource(R.drawable.ic_arrow_down_gray_round)
                text_show_more.text = "查看更多"
            }
            root.laybel.startAnimation()
        }
        root.laybel.setOnLaybelClickListener(object : LaybelLayout.LaybelItemClickListener{
            override fun onLaybelItemClick(keyWord: String) {
                Snackbar.make(root.laybel,keyWord,Snackbar.LENGTH_SHORT).show()
                var intent = Intent(activity, SearchDetailActivity::class.java)
                intent.putExtra("keyWord",keyWord)
                startActivity(intent)
            }
        })
        root.search.setOnClickListener {
            val popwindowUtil = PopwindowUtil.PopupWindowBuilder(context!!)
                .setView(R.layout.fragment_dialog_search)
                .size(ViewGroup.LayoutParams.MATCH_PARENT.toFloat(), ViewGroup.LayoutParams.WRAP_CONTENT.toFloat())
                .setFocusable(true)
                .setTouchable(true)
                .setOutsideTouchable(true)
                .create()
            popwindowUtil.showAtLocation(it,0,-it.getHeight(), Gravity.TOP,0.5f)
            popwindowUtil.getView<ImageView>(R.id.image_exit)!!.setOnClickListener {
                popwindowUtil.dissmiss()
            }
        }
        root.search_qr.setOnClickListener {
            Snackbar.make(it,"通过扫码查找",Snackbar.LENGTH_SHORT).show()
        }
    }
    override fun onFirstVisiblity() {
        super.onFirstVisiblity()
        presenter.getHotWords(50)
    }

    override fun onGetHotWords(wordsData: HotWordsData) {
        var words = arrayListOf<String>()
        Log.d(TAG,"获取到的热门搜索$wordsData")
        for (item in wordsData.list){
            words.add(item.keyword)
        }
        laybel.setAdapter(LaybelLayout.Adapter(words))
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.interest_group -> {
                if (!GlobalProperties.userLogined()){
                    ToastUtil.show(resources.getString(R.string.login_then_next))
                    startActivity(Intent(activity,LoginActivity::class.java))
                }
            }
            R.id.topic_center -> {
                val intent = Intent(activity,TopicCenterActivity::class.java)
                intent.putExtra("type",resources.getString(R.string.find_line_topic))
                startActivity(intent)
            }
            R.id.activity_center -> {
                val intent = Intent(activity,TopicCenterActivity::class.java)
                intent.putExtra("type",resources.getString(R.string.find_line_activity))
                startActivity(intent)
            }
            R.id.black_door -> {
                var intent = Intent(activity, WebActivity::class.java)
                intent.putExtra("uri",GlobalProperties.BLACK_DOOR)
                intent.putExtra("title",resources.getString(R.string.find_line_blackdoor))
                startActivity(intent)
            }
            R.id.origin_rank_list -> {
                var intent = Intent(activity, RankListActivity::class.java)
                intent.putExtra("type",resources.getString(R.string.find_line_top_original))
                startActivity(intent)
            }
            R.id.all_rank_list -> {
                //先查询是否有分区的数据
                /*var tables = SQLite.select().from(RegionTable::class.java).queryList()
                if (tables == null || tables.isEmpty()){
                    presenter.getRegion()
                    showProgressDialog("请求数据中...",context!!);
                    return
                }*/
                presenter.getRegion()
                showProgressDialog("请求数据中...",context!!);
            }
            R.id.round_shop ->{
                var intent = Intent(activity, WebActivity::class.java)
                intent.putExtra("uri",GlobalProperties.ROUND_SHOP)
                intent.putExtra("title",resources.getString(R.string.find_line_shop))
                startActivity(intent)
            }
        }
    }

    override fun onGetRegion(list: List<RegionData.Data>) {
        cancleProgressDialog()
        EventBus.getDefault().postSticky(list)
        var intent = Intent(activity, RankListActivity::class.java)
        intent.putExtra("type",resources.getString(R.string.find_line_top_all))
        startActivity(intent)
    }
    override fun onGetSearchResult(result: SearchResultData) {

    }
}