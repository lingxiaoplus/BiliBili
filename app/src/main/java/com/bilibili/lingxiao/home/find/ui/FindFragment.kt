package com.bilibili.lingxiao.home.find.ui

import android.content.Intent
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.find.FindPresenter
import com.bilibili.lingxiao.home.find.FindView
import com.bilibili.lingxiao.home.find.model.HotWordsData
import com.bilibili.lingxiao.home.find.model.SearchResultData
import com.bilibili.lingxiao.utils.UIUtil
import com.bilibili.lingxiao.widget.LaybelLayout

import com.camera.lingxiao.common.app.BaseFragment
import kotlinx.android.synthetic.main.fragment_find.*
import kotlinx.android.synthetic.main.fragment_find.view.*

class FindFragment :BaseFragment(), FindView {
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

    override fun onGetSearchResult(result: SearchResultData) {

    }
}