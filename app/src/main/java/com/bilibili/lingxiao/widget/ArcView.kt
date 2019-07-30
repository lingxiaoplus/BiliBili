package com.bilibili.lingxiao.widget

import android.content.Context
import android.graphics.*
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.bilibili.lingxiao.R

class ArcView  @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr){
    var mWidth = 0
    var mHeight = 0
    var mRadius = 0.0f
    lateinit var mRect:Rect
    lateinit var mCircleCenterPoint:PointF
    lateinit var mPaint:Paint

    init {
        mRect = Rect()
        mCircleCenterPoint = PointF()
        mPaint = Paint()
        mPaint.color = resources.getColor(R.color.colorPrimary)
        mPaint.isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        mRadius = w * 2 * 1.0f

        mRect.left = 0
        mRect.right = w
        mRect.top = 0
        mRect.bottom = h

        mCircleCenterPoint.x = w * 1.0f / 2
        mCircleCenterPoint.y = h * 1.0f - mRadius

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val canvasWidth = canvas.width * 1.0f
        val canvasHeight = canvas.height * 1.0f
        val layerId = canvas.saveLayer(0f,0f, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG)
        canvas.drawCircle(mCircleCenterPoint.x,mCircleCenterPoint.y,mRadius,mPaint)
        //设置PorterDuffXfermode
        mPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))

        canvas.drawRect(mRect,mPaint)
        mPaint.setXfermode(null)
        canvas.restoreToCount(layerId)
    }

}