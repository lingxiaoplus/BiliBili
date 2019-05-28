package com.bilibili.lingxiao.home.region.ui

import android.net.Uri
import com.bilibili.lingxiao.R
import com.camera.lingxiao.common.app.BaseActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.github.zackratos.ultimatebar.UltimateBar
import kotlinx.android.synthetic.main.activity_bangumi_detail.*
import android.graphics.drawable.AnimationDrawable
import android.support.v7.widget.LinearLayoutManager
import com.bilibili.lingxiao.GlobalProperties
import com.bilibili.lingxiao.home.region.model.BangumiDetailData
import com.bilibili.lingxiao.home.region.presenter.BangumiDetailPresenter
import com.bilibili.lingxiao.home.region.view.BangumiView
import com.bilibili.lingxiao.utils.StringUtil
import com.bilibili.lingxiao.utils.ToastUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.content_bangumi_detail.*
import java.util.*


class BangumiDetailActivity : BaseActivity(), BangumiView{
    private var presenter = BangumiDetailPresenter(this,this)
    override val contentLayoutId: Int
        get() = R.layout.activity_bangumi_detail

    override fun initWidget() {
        super.initWidget()
        UltimateBar.newTransparentBuilder()
            .statusColor(getResources().getColor(R.color.colorTrans))        // 状态栏颜色
            .statusAlpha(100)               // 状态栏透明度
            .applyNav(false)                // 是否应用到导航栏
            .build(this)
            .apply();
        setToolbarBack(toolbar)
        var id = intent.getStringExtra("id")
        var type = intent.getStringExtra("type")
        presenter.getBangumiDetail(id,type)
    }



    override fun showDialog() {
        showProgressDialog("获取数据中...")
    }

    @Throws(Exception::class)
    override fun onGetBangumiDetail(data: BangumiDetailData) {
        if (data.result == null){
            return
        }
        data.result.episodes?.let {
            showUrlBlur(image_bar,it[0].cover,
                6,10)
            Collections.reverse(it)
            var tvAdapter = BangumiTvAdapter(R.layout.item_bangumi_tv,it)
            recycler_select_set.layoutManager = LinearLayoutManager(this@BangumiDetailActivity
                ,LinearLayoutManager.HORIZONTAL,false)
            recycler_select_set.adapter = tvAdapter
        }
        image_cover.setImageURI(Uri.parse(data.result.cover + GlobalProperties.IMAGE_RULE_200_266))
        toolbar.title = data.result.bangumiTitle
        text_count.text = "${data.result.totalCount}话全"
        text_status.text = data.result.media.episodeIndex.indexShow
        text_play_count.text = "播放：${StringUtil.getBigDecimalNumber(data.result.playCount.toInt())}"
        text_favorites.text = "追番：${StringUtil.getBigDecimalNumber(data.result.favorites.toInt())}"
        text_evaluate.text = data.result.evaluate
    }

    override fun diamissDialog() {
        cancleProgressDialog()
    }

    override fun showToast(text: String?) {
        ToastUtil.show(text)
    }

    @Throws(ClassCastException::class)
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        var animationDrawables = tv_chasing.compoundDrawables
        var drawable = animationDrawables[1] as AnimationDrawable
        drawable.start()
    }
    /**
     * @param iterations 迭代次数，越大越魔化。
     * @param blurRadius 模糊图半径，必须大于0，越大越模糊。
     */
    fun showUrlBlur(draweeView: SimpleDraweeView, url:String, iterations:Int, blurRadius:Int) {
        try {
            var uri = Uri.parse(url);
            var  request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(IterativeBoxBlurPostProcessor(iterations, blurRadius))
            .build();
            var controller = Fresco.newDraweeControllerBuilder()
                .setOldController(draweeView.getController())
                .setImageRequest(request)
                .build();
            draweeView.setController(controller);
        } catch (e:Exception) {
            e.printStackTrace();
        }
    }

    inner class BangumiTvAdapter(layout:Int,data: List<BangumiDetailData.Result.Episode>?) :
        BaseQuickAdapter<BangumiDetailData.Result.Episode, BaseViewHolder>(layout,data) {
        override fun convert(helper: BaseViewHolder, item: BangumiDetailData.Result.Episode?) {
            helper.setText(R.id.text_title,"第${item?.index}话")
            helper.setText(R.id.text_message,item?.indexTitle)
        }
    }
}
