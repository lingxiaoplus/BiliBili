package com.bilibili.lingxiao.home.find

import android.util.Log
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.utils.UIUtil
import com.bilibili.lingxiao.widget.LaybelLayout

import com.camera.lingxiao.common.app.BaseFragment
import kotlinx.android.synthetic.main.fragment_find.*
import kotlinx.android.synthetic.main.fragment_find.view.*

class FindFragment :BaseFragment(),FindView{
    override fun onGetHotWords(wordsData: HotWordsData) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val TAG = FindFragment::class.java.simpleName
    private var presenter: FindPresenter = FindPresenter(this,this)
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
    }
    override fun onFirstVisiblity() {
        super.onFirstVisiblity()
        laybel.setAdapter(LaybelLayout.Adapter("hello","老番茄","大哥","fbvcbc","adda","fdgfdg","adsad",
            "hello","老番茄","大哥","fbvcbc","adda","fdgfdg","adsad"))
    }
}