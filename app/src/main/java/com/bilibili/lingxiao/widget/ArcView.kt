package com.bilibili.lingxiao.widget

import android.content.Context
import android.graphics.*
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.bilibili.lingxiao.R
import skin.support.content.res.SkinCompatResources
import skin.support.widget.SkinCompatHelper
import skin.support.widget.SkinCompatSupportable

class ArcView  @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr), SkinCompatSupportable {
    var mWidth = 0
    var mHeight = 0
    var mRadius = 0.0f
    lateinit var mRect:Rect
    lateinit var mCircleCenterPoint:PointF
    lateinit var mPaint:Paint
    private var mPrimaryColorId = SkinCompatHelper.INVALID_ID

    init {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.ArcView,
            defStyleAttr, 0
        )
        if (a.hasValue(R.styleable.ArcView_arcbackground)) {
            mPrimaryColorId = a.getResourceId(
                R.styleable.ArcView_arcbackground,
                SkinCompatHelper.INVALID_ID
            )
        }

        mRect = Rect()
        mCircleCenterPoint = PointF()
        mPaint = Paint()
        mPaint.color = a.getColor(R.styleable.ArcView_arcbackground,resources.getColor(R.color.colorPrimary))
        mPaint.isAntiAlias = true

        a.recycle()
        applySkin()
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


    override fun applySkin() {
        mPrimaryColorId = SkinCompatHelper.checkResourceId(mPrimaryColorId)
        if (mPrimaryColorId != SkinCompatHelper.INVALID_ID) {
            val primaryColor = SkinCompatResources.getColor(context, mPrimaryColorId)
            mPaint.color = primaryColor
            invalidate()
        }
    }
}