package com.bilibili.lingxiao.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.content.ContextWrapper



class RippleAnimation(
    context: Context,
    private val mStartX: Float,
    private val mStartY: Float,
    private val mStartRadius: Int
) : View(context) {

    private val mRootView: ViewGroup
    private val mPaint: Paint
    private var mMaxRadius: Int = 0
    private var isStarted = false
    private var mBackground: Bitmap? = null
    private var mCurrentRadius: Int = 0
    private var mAnimatorListener: AnimatorListenerAdapter? = null
    private var mUpdateListener: ValueAnimator.AnimatorUpdateListener? = null
    private var mDuration: Long = 1000

    private val animator: ValueAnimator
        get() {
            val animator = ValueAnimator.ofFloat(0f, mMaxRadius.toFloat()).setDuration(mDuration)
            animator.addUpdateListener(mUpdateListener)
            animator.addListener(mAnimatorListener)
            return animator
        }

    var endListener: RippleEndListener? = null

    init {
        //获取activity的根视图，用来添加本view
        mRootView = unwrap(getContext()).window.decorView as ViewGroup
        mPaint = Paint()
        mPaint.isAntiAlias = true
        //设置成擦除模式
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        updateMaxRadius()
        initListener()
    }

    private fun unwrap(context: Context): Activity {
        var context = context
        while (context !is Activity && context is ContextWrapper) {
            context = context.baseContext
        }

        return context as Activity
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var layer = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            layer = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)
        }
        canvas.drawBitmap(mBackground!!, 0f, 0f, null)
        canvas.drawCircle(mStartX, mStartY, mCurrentRadius.toFloat(), mPaint)
        canvas.restoreToCount(layer)
    }

    private fun updateMaxRadius() {
        //将屏幕分为四个小矩形
        val leftTop = RectF(0f, 0f, mStartX + mStartRadius, mStartY + mStartRadius)
        val rightTop = RectF(leftTop.right, 0f, mRootView.right.toFloat(), leftTop.bottom)
        val leftBottom = RectF(0f, leftTop.bottom, leftTop.right, mRootView.bottom.toFloat())
        val rightBottom = RectF(leftBottom.right, leftTop.bottom, mRootView.right.toFloat(), leftBottom.bottom)
        //获取对角线长度
        val leftTopHypoten =
            Math.sqrt(Math.pow(leftTop.width().toDouble(), 2.0) + Math.pow(leftTop.height().toDouble(), 2.0))
        val rightTopYopHypoten =
            Math.sqrt(Math.pow(rightTop.width().toDouble(), 2.0) + Math.pow(rightTop.height().toDouble(), 2.0))
        val leftBottomHypoten =
            Math.sqrt(Math.pow(leftBottom.width().toDouble(), 2.0) + Math.pow(leftBottom.height().toDouble(), 2.0))
        val rightBottomHypoten =
            Math.sqrt(Math.pow(rightBottom.width().toDouble(), 2.0) + Math.pow(rightBottom.height().toDouble(), 2.0))

        //取最大值
        mMaxRadius = Math.max(
            Math.max(leftTopHypoten, rightTopYopHypoten),
            Math.max(leftBottomHypoten, rightBottomHypoten)
        ).toInt()
    }

    private fun initListener() {
        mAnimatorListener = object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                //动画播放完毕，移除view
                detachFromRootView()
                if (endListener != null) {
                    endListener!!.onEnd()
                }
                isStarted = false
            }
        }
        mUpdateListener = ValueAnimator.AnimatorUpdateListener { animation ->
            mCurrentRadius = (animation.animatedValue as Float).toInt() + mStartRadius
            postInvalidate()
        }
    }

    private fun detachFromRootView() {
        mRootView.removeView(this)
    }

    fun start() {
        if (!isStarted) {
            isStarted = true
            updateBackground()
            attachToRootView()
            animator.start()
        }
    }

    /**
     * 添加到根视图
     */
    private fun attachToRootView() {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        mRootView.addView(this)
    }

    /**
     * 更新屏幕截图
     */
    private fun updateBackground() {
        if (mBackground != null && !mBackground!!.isRecycled) {
            mBackground!!.recycle()
        }
        mRootView.isDrawingCacheEnabled = true
        mBackground = mRootView.drawingCache
        mBackground = Bitmap.createBitmap(mBackground!!)
        mRootView.isDrawingCacheEnabled = false
    }

    fun setDuration(duration: Long): RippleAnimation {
        mDuration = duration
        return this
    }

    fun setRippleListener(listener: RippleEndListener) {
        this.endListener = listener
    }

    interface RippleEndListener {
        fun onEnd()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return true
    }

    companion object {

        fun create(onClickView: View): RippleAnimation {
            val context = onClickView.context
            val newWidth = onClickView.width / 2
            val newHeight = onClickView.height / 2
            //计算起点坐标
            val startX = getAbsoluteX(onClickView) + newWidth
            val startY = getAbsoluteY(onClickView) + newHeight

            //起始半径 （避免遮挡按钮）
            val radius = Math.max(newWidth, newHeight)
            return RippleAnimation(context, startX, startY, radius)
        }

        /**
         * 递归 获取绝对坐标
         * @param view
         * @return
         */
        private fun getAbsoluteX(view: View): Float {
            var x = view.x
            val parent = view.parent
            if (parent != null && parent is View) {
                x += getAbsoluteX(parent as View)
            }
            return x
        }

        private fun getAbsoluteY(view: View): Float {
            var y = view.y
            val parent = view.parent
            if (parent != null && parent is View) {
                y += getAbsoluteY(parent as View)
            }
            return y
        }
    }
}
