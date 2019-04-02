package com.bilibili.lingxiao.play

import android.graphics.drawable.Drawable
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.dagger.DaggerUiComponent
import com.camera.lingxiao.common.app.BaseFragment
import kotlinx.android.synthetic.main.fragment_introduce.view.*

class IntroduceFragment :BaseFragment(){
    var mEndPageList = arrayListOf<EndPageData>()
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
        setEndPageData()
        root.endpage_recycler.adapter = EndPageAdapter(R.layout.item_endpage,mEndPageList)
        root.endpage_recycler.isNestedScrollingEnabled = false
    }

    fun setEndPageData(){
        var recommend = EndPageData(resources.getDrawable(R.drawable.ic_recommend),"123")
        var dislike = EndPageData(resources.getDrawable(R.drawable.ic_dislike),"不喜欢")
        var coin = EndPageData(resources.getDrawable(R.drawable.ic_coin),"1")
        var collect = EndPageData(resources.getDrawable(R.drawable.ic_collect),"12")
        var share = EndPageData(resources.getDrawable(R.drawable.ic_share),"12")
        mEndPageList.add(recommend)
        mEndPageList.add(dislike)
        mEndPageList.add(coin)
        mEndPageList.add(collect)
        mEndPageList.add(share)
    }
    data class EndPageData(val icon:Drawable,val message:String){

    }
}