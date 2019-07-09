package com.bilibili.lingxiao.home.find.ui

import android.support.v7.widget.SearchView
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.home.find.FindView
import com.bilibili.lingxiao.home.find.presenter.SearchDetailPresenter
import com.bilibili.lingxiao.home.find.model.HotWordsData
import com.bilibili.lingxiao.home.find.model.SearchResultData
import com.bilibili.lingxiao.home.live.adapter.PlayPagerAdapter
import com.bilibili.lingxiao.home.region.model.RegionData
import com.camera.lingxiao.common.app.BaseActivity
import com.flyco.tablayout.listener.CustomTabEntity
import kotlinx.android.synthetic.main.activity_search_detail.*
import org.greenrobot.eventbus.EventBus

class SearchDetailActivity : BaseActivity() , FindView {
    lateinit var presenter: SearchDetailPresenter
    private val mTabEntities = arrayListOf<CustomTabEntity>()
    private var page = 1
    private val pageSize = 20
    private var keyWord = ""
    override val contentLayoutId: Int
        get() = R.layout.activity_search_detail

    override fun initWidget() {
        super.initWidget()
        keyWord = intent.getStringExtra("keyWord")
        presenter = SearchDetailPresenter(this, this)
        presenter.getSearchResult(keyWord,page,pageSize)
        initSearchView()
        text_cancel.setOnClickListener {
            finish()
        }
        /*close.setOnClickListener {
            searchview.setQuery("",false)
        }*/
        for (name in resources.getStringArray(R.array.search_type)){
            mTabEntities.add(TabEntity(name))
        }
        common_tablayout.setTabData(mTabEntities)
        viewpager.adapter = PlayPagerAdapter(supportFragmentManager,
            arrayOf("综合"),
            arrayListOf(SearchDetailFragment()))

    }

    private fun initSearchView() {
        //设置左侧有放大镜(在搜索框中) 右侧有叉叉
        //searchview.setIconified(false)
        searchview.onActionViewExpanded();
        var textView =
            searchview.findViewById<SearchView.SearchAutoComplete>(R.id.search_src_text)
        textView.setTextColor(resources.getColor(R.color.black_alpha_160))
        textView.setHintTextColor(resources.getColor(R.color.black_alpha_112))
        textView.setText(keyWord)
        searchview.setOnQueryTextFocusChangeListener { v, hasFocus ->

        }
        searchview.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(text: String): Boolean {
                keyWord = text
                getSearchResult(page)
                return true
            }

            override fun onQueryTextChange(text: String): Boolean {
                return true
            }

        })
    }

    /**
     * 给fragment调用
     */
    fun getSearchResult(page :Int){
        presenter.getSearchResult(keyWord,page,pageSize)
    }

    override fun onGetSearchResult(result: SearchResultData) {
        if (tablayout.tabCount < result.nav.size){
            for (item in result.nav){
                var name = item.name
                if (item.total != 0) name+= "(${item.total})"
                tablayout.addTab(tablayout.newTab().setText(name))
            }
        }
        EventBus.getDefault().postSticky(result.item)
    }

    override fun onGetHotWords(wordsData: HotWordsData) {

    }

    override fun onGetRegion(list: List<RegionData.Data>) {

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
