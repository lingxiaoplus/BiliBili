package com.bilibili.lingxiao.home.region.ui

import android.content.Intent
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
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.TextView
import com.bilibili.lingxiao.GlobalProperties
import com.bilibili.lingxiao.home.region.model.BangumiDetailData
import com.bilibili.lingxiao.home.region.model.BangumiRecommendData
import com.bilibili.lingxiao.home.region.presenter.BangumiDetailPresenter
import com.bilibili.lingxiao.home.region.view.BangumiView
import com.bilibili.lingxiao.utils.StringUtil
import com.bilibili.lingxiao.utils.ToastUtil
import com.camera.lingxiao.common.utills.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.content_bangumi_detail.*


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
            showUrlBlur(
                image_bar, it[0].cover,
                6, 10
            )
            //Collections.reverse(it)
            var tvAdapter = BangumiTvAdapter(R.layout.item_bangumi_tv, it)
            recycler_select_set.layoutManager = LinearLayoutManager(
                this@BangumiDetailActivity
                , LinearLayoutManager.HORIZONTAL, true
            )  //最后一个参数 ，逆序排列
            recycler_select_set.smoothScrollToPosition(0)
            recycler_select_set.adapter = tvAdapter
            recycler_select_set.isNestedScrollingEnabled = false
            recycler_select_set.isVerticalScrollBarEnabled = false
            tvAdapter.setOnItemClickListener { adapter, view, position ->
                LogUtils.d("点击了番剧的播放: ${adapter.data[position]}")
                //Snackbar.make(view,"通过扫码查找", Snackbar.LENGTH_SHORT).show()
            }
        }
        image_cover.setImageURI(Uri.parse(data.result.cover + GlobalProperties.IMAGE_RULE_200_266))
        toolbar.title = data.result.bangumiTitle
        text_count.text = "${data.result.totalCount}话全"
        text_status.text = data.result.media.episodeIndex.indexShow
        text_play_count.text = "播放：${StringUtil.getBigDecimalNumber(data.result.playCount.toInt())}"
        text_favorites.text = "追番：${StringUtil.getBigDecimalNumber(data.result.favorites.toInt())}"
        text_evaluate.text = data.result.evaluate

        presenter.getBangumiRecommend(data.result.seasonId)
    }

    var recommendAdapter:BangumiRecommendAdapter? = null
    var recommendList = arrayListOf<BangumiRecommendData.Result.BangumiInfo>()
    var random = 0
    override fun onGetBangumiRecommend(data: BangumiRecommendData) {
        if (data.result == null){
            return
        }
        recommendAdapter = BangumiRecommendAdapter(R.layout.item_mikan_video, recommendList)
        recycler_recommend.layoutManager = GridLayoutManager(
            this@BangumiDetailActivity,3)
        recycler_recommend.adapter = recommendAdapter
        recycler_recommend.setHasFixedSize(true)
        recycler_recommend.isNestedScrollingEnabled = false
        recycler_recommend.isVerticalScrollBarEnabled = false
        data.result.list?.let {
            if (it.size > 6){
                recommendAdapter?.addData(it.subList(0,6))
            }else{
                recommendAdapter?.addData(it)
            }
        }
        text_refresh_recommend.setOnClickListener {
            data.result.list?.let {
                if (it.size > 6){
                    random += 6
                    if (random + 6 > it.size) random = 0
                    recommendList.clear()
                    recommendList.addAll(it.subList(random, random + 6))
                    recommendAdapter?.notifyDataSetChanged()
                }
            }
            Log.d(BangumiDetailActivity::class.java.simpleName,"数据大小: ${recommendList.size}")
        }
        recommendAdapter?.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(
                this@BangumiDetailActivity,
                BangumiDetailActivity::class.java
            )
            intent.putExtra("id",recommendList[position].seasonId)
            intent.putExtra("type","bangumi")
            startActivity(intent)
        }
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

    inner class BangumiRecommendAdapter(layout:Int,data: List<BangumiRecommendData.Result.BangumiInfo>?) :
        BaseQuickAdapter<BangumiRecommendData.Result.BangumiInfo, BaseViewHolder>(layout,data) {
        override fun convert(helper: BaseViewHolder, item: BangumiRecommendData.Result.BangumiInfo) {
            var image:SimpleDraweeView = helper.getView(R.id.image_cover)
            image.setImageURI(Uri.parse(item.cover + GlobalProperties.IMAGE_RULE_200_266))
            helper.setText(R.id.text_num,StringUtil.getBigDecimalNumber(item.follow.toInt())+"人追番")
            helper.setText(R.id.text_title,item.title)
            helper.getView<TextView>(R.id.text_cover).visibility = View.GONE
            //helper.setText(R.id.text_cover,"更新至第" + item.totalCount +"话")
        }
    }
}
