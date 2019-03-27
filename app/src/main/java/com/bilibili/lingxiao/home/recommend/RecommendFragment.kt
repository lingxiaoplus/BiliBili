package com.bilibili.lingxiao.home.recommend

import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.utils.ToastUtil
import com.bilibili.lingxiao.utils.UIUtil
import com.camera.lingxiao.common.app.BaseFragment
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import kotlinx.android.synthetic.main.fragment_live.view.*

class RecommendFragment :BaseFragment(),RecommendView{

    private var recommendPresenter = RecommendPresenter(this,this)
    override val contentLayoutId: Int
        get() = R.layout.fragment_live

    override fun initInject() {
        super.initInject()
        UIUtil.getUiComponent().inject(this)

    }

    override fun initWidget(root: View) {
        super.initWidget(root)
        root.refresh.setRefreshFooter(BallPulseFooter(context!!))

        recommendPresenter.getRecommendList(1)
    }
    override fun onGetRecommendData(recommendData: RecommendData) {
        ToastUtil.show("成功")
    }

    override fun showDialog() {

    }

    override fun diamissDialog() {

    }

    override fun showToast(text: String?) {
        ToastUtil.show(text)
    }


}