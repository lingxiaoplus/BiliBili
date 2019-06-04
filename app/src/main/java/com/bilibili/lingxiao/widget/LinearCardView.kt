package com.bilibili.lingxiao.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import com.bilibili.lingxiao.R
import kotlinx.android.synthetic.main.linear_card_view.view.*

class LinearCardView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr){
    private var leftText = ""
    private var rightText = ""
    var showRightText = false
    init {
        LayoutInflater.from(context).inflate(R.layout.linear_card_view,this,true)
        var typedArray = context.obtainStyledAttributes(attrs,R.styleable.LinearCardView)
        if (typedArray.hasValue(R.styleable.LinearCardView_left_text)){
            leftText = typedArray.getString(R.styleable.LinearCardView_left_text)
        }
        if (typedArray.hasValue(R.styleable.LinearCardView_right_text)){
            rightText  = typedArray.getString(R.styleable.LinearCardView_right_text)
        }
        typedArray.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setTextLeft(leftText)
        setTextRight(rightText)
    }

    fun setTextLeft(text:String){
        text_left.setText(text)
    }

    fun setTextRight(text:String){
        text_right.setText(text)
    }
}