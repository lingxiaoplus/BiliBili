package com.bilibili.lingxiao.home.find.ui

import android.content.Intent
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.bilibili.lingxiao.GlobalProperties
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.find.presenter.FindPresenter
import com.bilibili.lingxiao.home.find.FindView
import com.bilibili.lingxiao.home.find.model.HotWordsData
import com.bilibili.lingxiao.home.find.model.SearchResultData
import com.bilibili.lingxiao.user.LoginActivity
import com.bilibili.lingxiao.utils.ToastUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.bilibili.lingxiao.web.WebActivity
import com.bilibili.lingxiao.widget.LaybelLayout

import com.camera.lingxiao.common.app.BaseFragment
import kotlinx.android.synthetic.main.fragment_find.*
import kotlinx.android.synthetic.main.fragment_find.view.*

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
                intent.putExtra("title","小黑屋")
                startActivity(intent)
            }
            R.id.origin_rank_list -> {
                var intent = Intent(activity, RankListActivity::class.java)
                startActivity(intent)
            }
            R.id.all_rank_list -> {
                var intent = Intent(activity, RankListActivity::class.java)
                startActivity(intent)
            }
        }
    }


    override fun onGetSearchResult(result: SearchResultData) {

    }
}