package com.bilibili.lingxiao.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.nfc.Tag
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.utils.UIUtil
import java.util.*
import java.util.Arrays.asList
import kotlin.collections.HashMap
import android.widget.TextView
import kotlinx.android.synthetic.main.item_comment.view.*


class LaybelLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    :ViewGroup(context,attrs,defStyleAttr) ,View.OnClickListener{
    private val mChildView :MutableList<View> = ArrayList()
    private val mChildrenMap = HashMap<View, ChildLayoutParams>()
    private var mLinePadding: Int = 0 //行内上下边距
    private var textBackground: Int = 0
    private var showLines = 2 //折叠的时候显示行数

    private var mUpdateListener: ValueAnimator.AnimatorUpdateListener? = null
    private var mAnimatorListener: AnimatorListenerAdapter? = null
    private var mCollapsed = true // 默认是被折叠了的

    private val TAG = LaybelLayout::class.java.simpleName
    init {
        val t = context.obtainStyledAttributes(attrs, R.styleable.LaybelLayout)
        mLinePadding = UIUtil.dip2px(t.getFloat(R.styleable.LaybelLayout_line_padding, 0f)).toInt()
        //childMargin = UIUtil.dip2px(t.getInt(R.styleable.LaybelLayout_child_margin, 0))
        textBackground = t.getResourceId(R.styleable.LaybelLayout_text_background, R.drawable.radius_text_background)
        t.recycle()
        initListener()
    }

    private var minWidth = 0
    private var minHeight = 0//本控件的最小宽高
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        minWidth = paddingLeft + paddingRight
        minHeight = paddingTop + paddingBottom
        for (i in 0 until childCount){
            var child = getChildAt(i)
            measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,0)
            var childLayoutParams  = child.layoutParams as MarginLayoutParams
            //如果单个View和本控件的padding加起来超过本控件的宽度，则让它的宽度 <= 本控件宽度 - Padding - margin
            var defSize = paddingLeft + childLayoutParams.leftMargin +
                    child.measuredWidth + paddingRight + childLayoutParams.rightMargin
            if (defSize > measuredWidth){
                defSize = measuredWidth - childLayoutParams.leftMargin - childLayoutParams.rightMargin
                - paddingLeft - paddingRight
                //childLayoutParams.width = defSize
                //measureChild(child,widthMeasureSpec,heightMeasureSpec)
                val widthSpec = MeasureSpec.makeMeasureSpec(defSize, MeasureSpec.AT_MOST)

                //根据measureChildWithMargins里面获取高度 Spec 的方式，重新获取到高度的Spec
                val heightSpec = getChildMeasureSpec(heightMeasureSpec,
                    paddingTop + paddingBottom + childLayoutParams.topMargin
                    + childLayoutParams.bottomMargin, childLayoutParams.height)
                child.measure(widthSpec, heightSpec)
            }
            if (!mChildView.contains(child))
                mChildView.add(child)
        }
        saveChildWidthAndHeight()


        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightMode = MeasureSpec.getMode(heightMeasureSpec)
        if (widthMode != MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY){
            //setMeasuredDimension(minWidth, minHeight)
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }else if (widthMode != MeasureSpec.EXACTLY){
            setMeasuredDimension(minWidth, View.getDefaultSize(suggestedMinimumHeight,heightMeasureSpec))
        }else if (heightMode != MeasureSpec.EXACTLY){
            setMeasuredDimension(View.getDefaultSize(suggestedMinimumWidth,widthMeasureSpec), minHeight)
        }
    }


    private fun saveChildWidthAndHeight() {
        var lineHeight = 0//单行高度
        var lineHeightSum = 0//前面总高度
        var left = 0
        var top = 0
        var right = 0
        var bottom = 0
        var freeWidth = measuredWidth - paddingLeft - paddingRight //横向剩余空间
        var isFirst = true //是否是某一行的第一个
        var tmpWidth = 0
        for ((i,child) in mChildView.withIndex()){
            var layoutParams  = child.layoutParams as MarginLayoutParams
            var childWidth = layoutParams.leftMargin + layoutParams.rightMargin + child.measuredWidth
            if (childWidth > freeWidth){
                //如果当前child宽度超过了剩余的空间
                isFirst = true
                lineHeightSum += lineHeight
                lineHeight = 0
                freeWidth = measuredWidth - paddingLeft - paddingRight //设为初始剩余
            }
            if (isFirst) {
                left = paddingLeft + layoutParams.leftMargin
                isFirst = false
                if (tmpWidth > minWidth)
                    minWidth = tmpWidth
                tmpWidth = childWidth
            } else {
                val prView = mChildView[i-1]
                val ll = prView.getLayoutParams() as ViewGroup.MarginLayoutParams
                left += prView.getMeasuredWidth() + ll.rightMargin + layoutParams.leftMargin
                tmpWidth += childWidth
            }
            top = paddingTop + lineHeightSum + mLinePadding + layoutParams.topMargin
            right = left + child.getMeasuredWidth()
            bottom = top + child.getMeasuredHeight()
            val tmpHeight = (mLinePadding * 2
                    + layoutParams.topMargin
                    + child.getMeasuredHeight()
                    + layoutParams.bottomMargin)
            if (tmpHeight > lineHeight)
            //选出一行当中占用高度最多的作为行高
                lineHeight = tmpHeight
            freeWidth -= childWidth

            if (mChildrenMap.containsKey(child))
                mChildrenMap.remove(child)
            mChildrenMap.put(child, ChildLayoutParams(left, top, right, bottom))
        }
        lineHeightSum += lineHeight;//加上最后一行的高度
        minHeight += lineHeightSum;
        startHeight = lineHeight * showLines
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for ((child,param) in mChildrenMap) {
            Log.d(TAG,"onLayout>>>>>>>  $param")
            child.layout(param.left, param.top, param.right, param.bottom)
        }
    }



    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

    }


    fun startAnimation(){
        var animator:ValueAnimator
        if (mCollapsed){
            animator = ValueAnimator
                .ofFloat(0f, 1f)
                .setDuration(500L)
        }else{
            animator = ValueAnimator
                .ofFloat(1f, 0f)
                .setDuration(500L)
        }
        animator.addUpdateListener(mUpdateListener)
        animator.addListener(mAnimatorListener)
        animator.start()
    }

    private var startHeight = 0
    private fun initListener(){
        mUpdateListener = ValueAnimator.AnimatorUpdateListener { animation ->
            var interpolatedTime = animation.animatedValue as Float
            val newHeight = (minHeight - startHeight) * interpolatedTime + startHeight
            this@LaybelLayout.getLayoutParams().height = newHeight.toInt()
            this@LaybelLayout.requestLayout()
        }

        mAnimatorListener = object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                //动画播放完毕
                mCollapsed = !mCollapsed
                /*var tempHeight = minHeight
                minHeight = startHeight
                startHeight = tempHeight
                Log.d(TAG,"结束后的高度startHeight： $startHeight, minHeight: $minHeight")*/
            }

        }
    }


    fun isCollapsed() :Boolean{
        return mCollapsed
    }

    private var mAdapter: Adapter? = null
    private fun addChildView() {
        mAdapter?.let {
            for (i in 0 until it.count) {
                val child = TextView(context)
                val params = generateDefaultLayoutParams() as MarginLayoutParams
                params.leftMargin = UIUtil.dip2px(5f).toInt()
                params.rightMargin = UIUtil.dip2px(5f).toInt()
                child.setBackgroundDrawable(context.resources.getDrawable(textBackground))
                child.setText(it.getItem(i))
                child.setOnClickListener{
                    laybelClickListener?.onLaybelItemClick(child.text.toString())
                }
                it.onDataSet(child, it.getItem(i))
                addView(child, params)
            }
        }

    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(super.generateDefaultLayoutParams())
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context,attrs)
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }

    override fun onClick(v: View?) {

    }

    private var laybelClickListener :LaybelItemClickListener? = null
    fun setOnLaybelClickListener(listener :LaybelItemClickListener){
        this.laybelClickListener = listener
    }
    interface LaybelItemClickListener{
        fun onLaybelItemClick(keyWord :String)
    }

    fun setAdapter(adapter:Adapter){
        this.mAdapter = adapter
        addChildView()
        this.post {
            this@LaybelLayout.getLayoutParams().height = startHeight
            this@LaybelLayout.requestLayout()
        }
    }
    data class ChildLayoutParams(var left:Int,var top:Int,var right:Int,var bottom:Int)

    class Adapter {
        lateinit var datas: ArrayList<String>
            private set

        val count: Int
            get() = datas.size

        /**
         * if you want to use custom child view, you can overide this method,
         * otherwise,the default view can be set
         *
         * @return your custom view
         */
        val view: View?
            get() = null

        constructor(datas: ArrayList<String>) {
            this.datas = datas
        }

        constructor(vararg datas: String) {
            this.datas = ArrayList()
            datas.forEach {
                this.datas.add(it)
            }
        }

        fun getItem(position: Int): String {
            return if (datas.size < 1) "" else datas[position]
        }

        //called when data set by LaybelLayout
        fun onDataSet(v: View, data: String) {

        }
    }

}