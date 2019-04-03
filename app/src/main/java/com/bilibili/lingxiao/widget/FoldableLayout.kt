package com.bilibili.lingxiao.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.support.annotation.IdRes
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.bilibili.lingxiao.R

class FoldableLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {
    var isFolded = true //默认是被折叠了的
    //文字显示的最大行数
    private val MAX_COLLAPSED_LINES = 1
    //默认动画播放时长
    private val DEFAULT_ANIM_DURATION = 300
    var mMaxCollapsedLines:Int = MAX_COLLAPSED_LINES
    var mAnimationDuration:Int = DEFAULT_ANIM_DURATION
    //字体总高度
    var mTextHeightWithMaxLines = 0

    @IdRes
    var mFirstViewId = R.id.expand_textview
    @IdRes
    var mExpandCollapseToggleId = R.id.expand_collapse
    var mTextView:TextView? = null
    var mToggleView:View? = null
    private var mCollapsed = true // Show short version as default.
    private var mMarginBetweenTxtAndBottom: Int = 0
    private var mCollapsedHeight: Int = 0
    private var mUpdateListener: ValueAnimator.AnimatorUpdateListener? = null
    private var mAnimatorListener: AnimatorListenerAdapter? = null

    init {
        var typedArray = context.obtainStyledAttributes(attrs,R.styleable.FoldableLayout)
        mMaxCollapsedLines = typedArray.getInt(R.styleable.FoldableLayout_maxCollapsedLines,MAX_COLLAPSED_LINES)
        mAnimationDuration = typedArray.getInt(R.styleable.FoldableLayout_animDuration,DEFAULT_ANIM_DURATION)
        mFirstViewId = typedArray.getResourceId(R.styleable.FoldableLayout_textViewId,R.id.expand_textview)
        mExpandCollapseToggleId = typedArray.getResourceId(R.styleable.FoldableLayout_expandCollapseToggleId,R.id.expand_collapse)

        typedArray.recycle()
        orientation = VERTICAL
        initListener()
    }

    override fun setOrientation(orientation: Int) {
        if (HORIZONTAL == orientation) {
            throw IllegalArgumentException("FoldableLayout only supports Vertical Orientation.")
        }
        super.setOrientation(orientation)
    }

    /**
     * 当View中所有的子控件均被映射成xml后触发，即调用setContentView之后就会调用onFinishInflate这个方法
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        mTextView = findViewById(mFirstViewId)
        mToggleView = findViewById(mExpandCollapseToggleId)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mToggleView?.visibility = GONE
        mTextView?.maxLines = Int.MAX_VALUE
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (mTextView!!.lineCount <= mMaxCollapsedLines){
            return
        }
        mTextHeightWithMaxLines = getRealTextViewHeight(mTextView!!)

        if (mCollapsed){
            mTextView?.post({
                //获取一个差值，动画范围在这个差值里
                mMarginBetweenTxtAndBottom = height - mTextView!!.height
            })
            mCollapsedHeight = measuredHeight
        }

    }


    /**
     * 获取字体总高度
     */
    private  fun getRealTextViewHeight(textView: TextView): Int {
        var textHeight = textView.layout.getLineTop(textView.lineCount)
        var padding = textView.compoundPaddingTop + textView.compoundPaddingBottom
        return textHeight + padding
    }

    fun startAnimation(){
        val animator = ValueAnimator
            .ofFloat(0f, mMarginBetweenTxtAndBottom.toFloat())
            .setDuration(mAnimationDuration.toLong())
        animator.addUpdateListener(mUpdateListener)
        animator.start()
    }

    private fun initListener(){
        mUpdateListener = ValueAnimator.AnimatorUpdateListener { animation ->
            var interpolatedTime = (animation.animatedValue as Float).toInt()
            var endHeight = 0
            if (mCollapsed){
                endHeight = mCollapsedHeight
            }else{
                endHeight = height + mTextHeightWithMaxLines - mTextView!!.getHeight()
            }
            val newHeight = ((endHeight - height) * interpolatedTime + height).toInt()
            mTextView?.setMaxHeight(newHeight - mMarginBetweenTxtAndBottom)
            /*if (java.lang.Float.compare(mAnimAlphaStart, 1.0f) != 0) {
                applyAlphaAnimation(mTextView, mAnimAlphaStart + interpolatedTime * (1.0f - mAnimAlphaStart))
            }*/
            this@FoldableLayout.getLayoutParams().height = newHeight
            this@FoldableLayout.requestLayout()
        }

        mAnimatorListener = object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                //动画播放完毕
            }
        }
    }
}
