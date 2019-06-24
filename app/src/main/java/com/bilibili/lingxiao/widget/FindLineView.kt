package com.bilibili.lingxiao.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.bilibili.lingxiao.R
import kotlinx.android.synthetic.main.find_line_view.view.*

class FindLineView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr){
    var resourceId = -1
    var title = ""
    var showIcon = false
    init {
        LayoutInflater.from(context).inflate(R.layout.find_line_view,this,true)
        var typedArray = context.obtainStyledAttributes(attrs,R.styleable.FindLineView)
        if (typedArray.hasValue(R.styleable.FindLineView_src)){
            resourceId = typedArray.getResourceId(R.styleable.FindLineView_src,R.drawable.ic_group)

        }
        if (typedArray.hasValue(R.styleable.FindLineView_text)){
            title = typedArray.getString(R.styleable.FindLineView_text)
        }
        showIcon = typedArray.getBoolean(R.styleable.FindLineView_showRightIcon,false)
        typedArray.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (resourceId > 0) image_head.setImageResource(resourceId)
        if (title.isNotEmpty()) text_title.text = title
        if (!showIcon){
            image_right.visibility = View.GONE
        }
    }
}