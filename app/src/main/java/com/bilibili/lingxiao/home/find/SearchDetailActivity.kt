package com.bilibili.lingxiao.home.find

import android.support.design.widget.TabLayout
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.find.model.HotWordsData
import com.bilibili.lingxiao.home.find.model.SearchResultData
import com.camera.lingxiao.common.app.BaseActivity
import com.camera.lingxiao.common.utills.LogUtils
import kotlinx.android.synthetic.main.activity_search_detail.*

class SearchDetailActivity : BaseActivity() ,FindView{
    lateinit var presenter:SearchDetailPresenter

    override val contentLayoutId: Int
        get() = R.layout.activity_search_detail

    override fun initWidget() {
        super.initWidget()
        var keyWord = intent.getStringExtra("keyWord")
        presenter = SearchDetailPresenter(this,this)
        presenter.getSearchResult(keyWord,1,20)
        edit_text.setText(keyWord)
        text_cancel.setOnClickListener {
            finish()
        }
    }

    override fun onGetSearchResult(result: SearchResultData) {
        LogUtils.d("获取到的结果>>$result")
        for (item in result.nav){
            var name = item.name
            if (item.total != 0) name+= "(${item.total})"
            tablayout.addTab(tablayout.newTab().setText(name))
        }
    }

    override fun onGetHotWords(wordsData: HotWordsData) {

    }
}
