package com.bilibili.lingxiao.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.IdRes
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.bilibili.lingxiao.R
import com.camera.lingxiao.common.utills.LogUtils



/**
 * 可折叠的Layout
 * @author lingxiao
 */
class FoldableLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) ,View.OnClickListener{

    //文字显示的最大行数
    private val MAX_COLLAPSED_LINES = 1
    //默认动画播放时长
    private val DEFAULT_ANIM_DURATION = 300

    //开关样式 默认为imagebutton
    private val EXPAND_INDICATOR_IMAGE_BUTTON = 0
    private val EXPAND_INDICATOR_TEXT_VIEW = 1
    private val DEFAULT_TOGGLE_TYPE = EXPAND_INDICATOR_IMAGE_BUTTON

    var mMaxCollapsedLines:Int = MAX_COLLAPSED_LINES
    var mAnimationDuration:Int = DEFAULT_ANIM_DURATION
    //字体总高度
    var mTextHeightWithMaxLines = 0

    @IdRes
    var mFirstViewId = R.id.expand_textview
    @IdRes
    var mExpandCollapseToggleId = R.id.expand_collapse
    @IdRes
    var mMessageViewId = R.id.expand_message_textview  //放置内容的id

    var mTextView:TextView? = null
    var mToggleView:View? = null
    var mMessageTextView:TextView? = null

    private var foldClickable = true //是否点击可折叠
    private var mCollapsed = true // 默认是被折叠了的
    private var mRelayout: Boolean = false
    private var mMarginBetweenTxtAndBottom: Int = 0
    private var mCollapsedHeight: Int = 0
    private var mUpdateListener: ValueAnimator.AnimatorUpdateListener? = null
    private var mAnimatorListener: AnimatorListenerAdapter? = null
    private var mExpandIndicatorController: ExpandIndicatorController? = null
    private var startHeight = 0
    private var endHeight = 0
    init {
        var typedArray = context.obtainStyledAttributes(attrs,R.styleable.FoldableLayout)
        mMaxCollapsedLines = typedArray.getInt(R.styleable.FoldableLayout_maxCollapsedLines,MAX_COLLAPSED_LINES)
        mAnimationDuration = typedArray.getInt(R.styleable.FoldableLayout_animDuration,DEFAULT_ANIM_DURATION)
        mFirstViewId = typedArray.getResourceId(R.styleable.FoldableLayout_textViewId,R.id.expand_textview)
        mExpandCollapseToggleId = typedArray.getResourceId(R.styleable.FoldableLayout_expandCollapseToggleId,R.id.expand_collapse)
        mMessageViewId = typedArray.getResourceId(R.styleable.FoldableLayout_messageViewId,R.id.expand_message_textview)
        foldClickable = typedArray.getBoolean(R.styleable.FoldableLayout_foldClickable,true)

        mExpandIndicatorController = setupExpandController(context,typedArray)
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
        mMessageTextView = findViewById(mMessageViewId)

        if (foldClickable){
            mTextView?.setOnClickListener(this)
            mToggleView?.let {
                it.setOnClickListener(this)
                mExpandIndicatorController?.setView(it)
            }
        }

        mExpandIndicatorController?.changeState(mCollapsed)
    }

    private var mMessageTextHeight = 0
    private var mMarginMessageTextAndBottom = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (!mRelayout){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }
        mRelayout = false
        mToggleView?.visibility = GONE
        mTextView?.maxLines = Int.MAX_VALUE
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //单行不显示
        /*if (mTextView!!.lineCount <= mMaxCollapsedLines){
            return
        }*/
        mTextHeightWithMaxLines = getRealTextViewHeight(mTextView!!)
        if (mCollapsed){
            mTextView!!.maxLines = mMaxCollapsedLines
        }
        mToggleView?.setVisibility(View.VISIBLE)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (mCollapsed){
            mTextView?.post({
                //标题和底部的距离，动画范围在这个差值里
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
            .ofFloat(0f, 1f)
            .setDuration(mAnimationDuration.toLong())
        animator.addUpdateListener(mUpdateListener)
        animator.addListener(mAnimatorListener)
        animator.start()
    }

    private fun initListener(){
        mUpdateListener = ValueAnimator.AnimatorUpdateListener { animation ->
            var interpolatedTime = animation.animatedValue as Float
            //从startHeight->endHeight
            val newHeight = (endHeight - startHeight) * interpolatedTime + startHeight
            mTextView?.maxHeight = (newHeight - mMarginBetweenTxtAndBottom).toInt()
            //val titleHeight = (mTextHeightWithMaxLines - mMarginBetweenTxtAndBottom) * interpolatedTime + mMarginBetweenTxtAndBottom
            //mTextView?.maxHeight = titleHeight.toInt()
            //mMessageTextView?.maxHeight = (newHeight - height + mMessageTextHeight).toInt()
            this@FoldableLayout.getLayoutParams().height = newHeight.toInt()
            this@FoldableLayout.requestLayout()
            LogUtils.d("Foldable变化：${mMarginBetweenTxtAndBottom}")
        }

        mAnimatorListener = object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                //动画播放完毕
            }

        }
    }

    fun setTitleText(title: CharSequence){
        mRelayout = true
        mTextView?.text = title
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        requestLayout()
    }
    fun setMessageText(message: CharSequence){
        mMessageTextView?.text = message
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        requestLayout()
    }
    override fun onClick(v: View?) {
        changeStatus()
    }

    fun changeStatus(){
        mToggleView?.let {
            if (it.visibility != visibility){
                return
            }
        }
        mCollapsed = !mCollapsed
        mCollapseListener?.onCollapseChanged(mCollapsed)
        mExpandIndicatorController?.changeState(mCollapsed)
        startHeight = height
        if (mCollapsed){
            endHeight = mCollapsedHeight
        }else{
            //真正的高度 + 被折叠的高度 + message的高度
            endHeight = height + mTextHeightWithMaxLines - mTextView!!.height
        }
        startAnimation()
    }

    fun setupExpandController(context: Context,typedArray: TypedArray): ExpandIndicatorController{
        val expandToggleType = typedArray.getInt(R.styleable.FoldableLayout_expandToggleType, DEFAULT_TOGGLE_TYPE)
        var expandIndicatorController: ExpandIndicatorController? = null
        when(expandToggleType){
            EXPAND_INDICATOR_IMAGE_BUTTON->{
                var expandDrawable = typedArray.getDrawable(R.styleable.FoldableLayout_expandIndicator)
                var collapseDrawable = typedArray.getDrawable(R.styleable.FoldableLayout_collapseIndicator)

                if (expandDrawable == null){
                    expandDrawable = context.resources.getDrawable(R.drawable.ic_img_toggle_down)
                }
                if (collapseDrawable == null){
                    collapseDrawable = context.resources.getDrawable(R.drawable.ic_img_toggle_up)
                }
                expandIndicatorController = ImageButtonExpandController(expandDrawable,collapseDrawable)
            }
            EXPAND_INDICATOR_TEXT_VIEW->{
                val expandText = typedArray.getString(R.styleable.FoldableLayout_expandIndicator)
                val collapseText = typedArray.getString(R.styleable.FoldableLayout_collapseIndicator)
                expandIndicatorController = TextViewExpandController(expandText,collapseText)
            }
            else-> IllegalAccessException("必须指定开关的样式！")
        }

        return expandIndicatorController!!
    }

    interface ExpandIndicatorController {
        fun changeState(collapsed: Boolean)
        fun setView(toggleView: View)
    }

    /**
     * 图片开关
     */
    inner class ImageButtonExpandController(expand: Drawable, collapse:Drawable) :ExpandIndicatorController{
        private var imageButton: ImageButton? = null
        private val expandDrawable: Drawable = expand
        private val collapseDrawable: Drawable = collapse
        override fun changeState(collapsed: Boolean) {
            imageButton?.setImageDrawable(if (collapsed) expandDrawable else collapseDrawable)
        }

        override fun setView(toggleView: View) {
            imageButton = toggleView as ImageButton
        }
    }

    /**
     * 文字开关
     */
    inner class TextViewExpandController(expand: String, collapse:String) :ExpandIndicatorController{
        private var textView: TextView? = null
        private val expandStr: String = expand
        private val collapseStr: String = collapse
        override fun changeState(collapsed: Boolean) {
            textView?.setText(if (collapsed) expandStr else collapseStr)
        }

        override fun setView(toggleView: View) {
            textView = toggleView as TextView
        }
    }

    private var mCollapseListener:CollapseListener? = null
    fun setCollapseListener(collapseListener:CollapseListener) {
        mCollapseListener = collapseListener
    }
    interface CollapseListener{
        fun onCollapseChanged(collapsed :Boolean)
    }
}
