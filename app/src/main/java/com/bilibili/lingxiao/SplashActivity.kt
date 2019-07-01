package com.bilibili.lingxiao

import android.view.View
import android.view.animation.Animation
import com.camera.lingxiao.common.app.BaseActivity
import android.view.animation.ScaleAnimation
import android.view.animation.AnimationSet
import com.bilibili.lingxiao.home.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : BaseActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_splash

    override fun initWidget() {
        super.initWidget()
        startAnim(splash_root)
    }

    private fun startAnim(view: View) {
        val animationSet = AnimationSet(true)
        val scaleAnimation = ScaleAnimation(
            0.98f, 1.0f, 0.98f, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleAnimation.duration = 1000
        //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
        animationSet.addAnimation(scaleAnimation)
        animationSet.fillAfter = true
        //启动动画
        view.startAnimation(animationSet)
        scaleAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                StartActivity(MainActivity::class.java, true)
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
    }
}
