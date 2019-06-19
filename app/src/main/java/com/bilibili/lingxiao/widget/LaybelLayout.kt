package com.bilibili.lingxiao.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.support.v4.content.res.TypedArrayUtils.getResourceId
import com.bilibili.lingxiao.widget.LaybelLayout
import android.content.res.TypedArray
import android.util.SparseArray
import com.bilibili.lingxiao.R
import android.R.attr.keySet
import java.util.Arrays.asList








class LaybelLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    :ViewGroup(context,attrs,defStyleAttr) ,View.OnClickListener{
    private var mChildView = arrayListOf<View>()
    private val mChildrenMap = HashMap<View, ChildLayoutParams>()
    init {
        initAttr(attrs);
    }

    private fun initAttr(attrs: AttributeSet?) {
        val t = context.obtainStyledAttributes(attrs, R.styleable.LaybelLayout)
        //mLinePadding = UIUtil.dip2px(t.getInt(R.styleable.LaybelLayout_line_padding, 0))
        //childMargin = UIUtil.dip2px(t.getInt(R.styleable.LaybelLayout_child_margin, 0))
        //textBackground = t.getResourceId(R.styleable.LaybelLayout_text_background, R.drawable.background)
        t.recycle()
    }


    private var minWidth = 0
    private var minHeight = 0//本控件的最小宽高
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        minWidth = paddingLeft + paddingRight
        minHeight = paddingTop + paddingBottom
        for (i in 0..childCount-1){
            var child = getChildAt(i)
            measureChild(child,widthMeasureSpec,heightMeasureSpec)
            var childLayoutParams  = child.layoutParams as MarginLayoutParams
            //如果单个View和本控件的padding加起来超过本控件的宽度，则让它的宽度 <= 本控件宽度 - Padding - margin
            var defSize = paddingLeft + childLayoutParams.leftMargin +
                    child.measuredWidth + paddingRight + childLayoutParams.rightMargin
            if (defSize > measuredWidth){
                defSize = measuredWidth - childLayoutParams.leftMargin - childLayoutParams.rightMargin
                - paddingLeft - paddingRight
                childLayoutParams.width = defSize
                measureChild(child,widthMeasureSpec,heightMeasureSpec)
            }
            if (!mChildView.contains(child))
                mChildView.add(child)
        }
        saveChildWidthAndHeight()
    }

    private var mLinePadding: Int = 0//行内上下边距
    private fun saveChildWidthAndHeight() {
        var lineHeight = 0//单行高度
        var lineHeightSum = 0//前面总高度
        var left = 0
        var top = 0
        var right = 0
        var bottom = 0
        var freeWidth = measuredWidth - paddingLeft - paddingRight//横向剩余空间
        var isFirst = true//是否是某一行的第一个
        var tmpWidth = 0
        for ((i,child) in mChildView.withIndex()){
            var layoutParams  = child.layoutParams as MarginLayoutParams
            var childWidth = layoutParams.leftMargin + layoutParams.rightMargin + child.measuredWidth
            if (childWidth > freeWidth){
                //如果当前child宽度超过了剩余的空间
                isFirst = true
                lineHeightSum += lineHeight
                lineHeight = 0
                freeWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();//设为初始剩余
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
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for ((child,param) in mChildrenMap) {
            child.layout(param.left, param.top, param.right, param.bottom)
        }
    }

    override fun onClick(v: View?) {

    }

    data class ChildLayoutParams(var left:Int,var right:Int,var top:Int,var bottom:Int)
}