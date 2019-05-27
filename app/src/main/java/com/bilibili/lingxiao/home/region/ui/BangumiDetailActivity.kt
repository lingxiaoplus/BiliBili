package com.bilibili.lingxiao.home.region.ui

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bilibili.lingxiao.R
import com.camera.lingxiao.common.app.BaseActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.github.zackratos.ultimatebar.UltimateBar
import kotlinx.android.synthetic.main.activity_bangumi_detail.*
import android.graphics.drawable.AnimationDrawable



class BangumiDetailActivity : BaseActivity() {
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
        showUrlBlur(image_bar,"http://i0.hdslb.com/bfs/archive/8d9c4f94c42ab92e477c8cfdb2d5c02eff65ef3d.jpg",
            6,10)
        image_cover.setImageURI(Uri.parse("http://i0.hdslb.com/bfs/bangumi/2fa3b4a2dbad3d307876cec4fa458fb2cbb50681.jpg@200w_266h_1e_1c.webp"))
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        //image_follow.set(R.drawable.bangumi_follow_animlist)
        //val animationDrawable = imageView.getDrawable() as AnimationDrawable
        //animationDrawable.start()
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
}
