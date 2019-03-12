package com.bilibili.lingxiao.widget.skin

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet

import com.scwang.smartrefresh.layout.SmartRefreshLayout

import skin.support.content.res.SkinCompatResources
import skin.support.widget.SkinCompatHelper
import skin.support.widget.SkinCompatSupportable

import skin.support.widget.SkinCompatHelper.INVALID_ID

class SmartSkinRefreshLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SmartRefreshLayout(context, attrs, defStyleAttr), SkinCompatSupportable {
    private var mPrimaryColorId = INVALID_ID
    private val mAccentColor: Int
    init {
        val a = context.obtainStyledAttributes(
            attrs, com.scwang.smartrefresh.layout.R.styleable.SmartRefreshLayout,
            defStyleAttr, 0
        )
        if (a.hasValue(com.scwang.smartrefresh.layout.R.styleable.SmartRefreshLayout_srlPrimaryColor)) {
            mPrimaryColorId = a.getResourceId(
                com.scwang.smartrefresh.layout.R.styleable.SmartRefreshLayout_srlPrimaryColor,
                INVALID_ID
            )
        }
        mAccentColor = a.getColor(com.scwang.smartrefresh.layout.R.styleable.SmartRefreshLayout_srlAccentColor, 0)
        a.recycle()
        applySkin()
    }

    override fun applySkin() {
        mPrimaryColorId = SkinCompatHelper.checkResourceId(mPrimaryColorId)
        if (mPrimaryColorId != INVALID_ID) {
            val primaryColor = SkinCompatResources.getColor(context, mPrimaryColorId)
            if (mAccentColor != INVALID_ID) {
                mPrimaryColors = intArrayOf(primaryColor, mAccentColor)
            } else {
                mPrimaryColors = intArrayOf(primaryColor)
            }
            setPrimaryColors(*mPrimaryColors)
        }
    }

    companion object {

        private val TAG = SmartSkinRefreshLayout::class.java.simpleName
    }
}
