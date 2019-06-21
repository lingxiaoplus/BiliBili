package com.bilibili.lingxiao.widget

import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.widget.TextView
import android.graphics.Color.parseColor
import android.util.TypedValue
import com.bilibili.lingxiao.widget.ColorTextView
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import com.bilibili.lingxiao.R
import java.util.*
import android.view.View
import android.graphics.RectF
import com.bilibili.lingxiao.utils.UIUtil


class ColorTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : AppCompatTextView(context, attrs, defStyleAttr){
    /**
     * 文本内容
     */
    private var mTitleText: String = ""
    /**
     * 文本的颜色
     */
    private var mTitleTextColor: Int = 0
    /**
     * 文本的大小
     */
    private var mTitleTextSize: Float = 0f

    private var ctvBackgroundColor: Int = 0

    /**
     * 圆角大小
     */
    private var mCornerSize: Float = 0f

    /**
     * 绘制时控制文本绘制的范围
     */
    private lateinit var mtitleBound: Rect
    private var textWidthMargin:Int = 10
    private lateinit var mtitlePaint: Paint
    private lateinit var mBackgroundPaint: Paint
    private val colors = arrayOf(
        "#4CAF50",
        "#E57373",
        "#64B5F6",
        "#7986CB",
        "#9575CD",
        "#F06292",
        "#ffb74d",
        "#4db6ac",
        "#4fc3f7",
        "#4dd0e1"
    )

    init {

        /**
         * 获得我们所定义的自定义样式属性
         */
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.ColorTextView, defStyleAttr, 0)
        val n = a.indexCount
        for (i in 0 until n) {
            val attr = a.getIndex(i)
            when (attr) {

                R.styleable.ColorTextView_ctvText -> mTitleText = a.getString(attr)
                R.styleable.ColorTextView_ctvTextColor ->
                    // 默认颜色设置为黑色
                    mTitleTextColor = a.getColor(attr, Color.BLACK)
                R.styleable.ColorTextView_ctvTextSize ->
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    mTitleTextSize = a.getDimensionPixelSize(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16f, resources.displayMetrics).toInt()).toFloat()
                R.styleable.ColorTextView_ctvBackground ->
                    //默认为白色
                    ctvBackgroundColor = a.getColor(attr, Color.WHITE)
                R.styleable.ColorTextView_ctvCornerSize ->
                    //默认圆角为0
                    mCornerSize = a.getInteger(attr, 0).toFloat()

            }
        }
        a.recycle()

        //val random = Random()
        //val ret = random.nextInt(colors.size - 1)
        //ctvBackgroundColor = Color.parseColor(colors[ret])
        mtitlePaint = Paint()
        mtitlePaint.setTextSize(mTitleTextSize.toFloat())
        mtitleBound = Rect()
        mtitlePaint.getTextBounds(mTitleText, 0, mTitleText.length, mtitleBound)

        mBackgroundPaint = Paint(Paint.FILTER_BITMAP_FLAG)
        mBackgroundPaint.isAntiAlias = true
        mBackgroundPaint.color = ctvBackgroundColor
    }

    fun setCtvBackgroundColor(ctvBackgroundColor: Int) {
        this.ctvBackgroundColor = ctvBackgroundColor
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        this.mTitleText = text.toString()
    }

    /*fun setTitleText(text: String) {
        this.mTitleText = text
    }*/

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        var width: Int
        var height: Int
        if (widthMode == View.MeasureSpec.EXACTLY) {
            width = widthSize + textWidthMargin * 2
        } else {
            mtitlePaint.setTextSize(mTitleTextSize)
            mtitlePaint.getTextBounds(mTitleText, 0, mTitleText.length, mtitleBound)
            //measureText 是字体整体宽度 getTextBounds获得的是字符串的最小矩形区域
            width = Math.min(paddingLeft + mtitleBound.width() + paddingRight + textWidthMargin * 2, widthSize)
        }

        if (heightMode == View.MeasureSpec.EXACTLY) {
            height = heightSize
        } else {
            mtitlePaint.setTextSize(mTitleTextSize)
            mtitlePaint.getTextBounds(mTitleText, 0, mTitleText.length, mtitleBound)
            val desired = paddingTop + mtitleBound.height() + paddingBottom
            height = Math.max(desired, heightSize)
        }
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val rec = RectF(0f, 0f, measuredWidth.toFloat() , measuredHeight.toFloat())
        canvas.drawRoundRect(rec, mCornerSize, mCornerSize, mBackgroundPaint)
        mtitlePaint.color = mTitleTextColor
        val fontMetrics = mtitlePaint.fontMetricsInt
        val baseline = (measuredHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top
        canvas.drawText(mTitleText, paddingLeft.toFloat() + textWidthMargin.toFloat() , baseline.toFloat(), mtitlePaint)
    }
}