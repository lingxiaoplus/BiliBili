package com.bilibili.lingxiao.home.find.ui

import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.find.FindView
import com.bilibili.lingxiao.home.find.SearchDetailPresenter
import com.bilibili.lingxiao.home.find.model.HotWordsData
import com.bilibili.lingxiao.home.find.model.SearchResultData
import com.bilibili.lingxiao.home.live.adapter.PlayPagerAdapter
import com.camera.lingxiao.common.app.BaseActivity
import com.camera.lingxiao.common.utills.LogUtils
import com.flyco.tablayout.listener.CustomTabEntity
import kotlinx.android.synthetic.main.activity_search_detail.*
import org.greenrobot.eventbus.EventBus

class SearchDetailActivity : BaseActivity() , FindView {
    lateinit var presenter: SearchDetailPresenter
    private val mTabEntities = arrayListOf<CustomTabEntity>()

    override val contentLayoutId: Int
        get() = R.layout.activity_search_detail

    override fun initWidget() {
        super.initWidget()
        var keyWord = intent.getStringExtra("keyWord")
        presenter = SearchDetailPresenter(this, this)
        presenter.getSearchResult(keyWord,1,20)
        edit_text.setText(keyWord)
        text_cancel.setOnClickListener {
            finish()
        }

        for (name in resources.getStringArray(R.array.search_type)){
            mTabEntities.add(TabEntity(name))
        }
        common_tablayout.setTabData(mTabEntities)
        viewpager.adapter = PlayPagerAdapter(supportFragmentManager,
            arrayOf("综合"),
            arrayListOf(SearchDetailFragment()))
    }

    override fun onGetSearchResult(result: SearchResultData) {
        LogUtils.d("获取到的结果>>$result")
        for (item in result.nav){
            var name = item.name
            if (item.total != 0) name+= "(${item.total})"
            tablayout.addTab(tablayout.newTab().setText(name))
        }
        EventBus.getDefault().postSticky(result.item)
    }

    override fun onGetHotWords(wordsData: HotWordsData) {

    }

    private inner class TabEntity(var title:String): CustomTabEntity{
        override fun getTabUnselectedIcon(): Int {
            return R.drawable.ic_arrow_down_gray_round
        }

        override fun getTabSelectedIcon(): Int {
            return R.drawable.ic_arrow_up
        }

        override fun getTabTitle(): String {
            return title
        }

    }
}
