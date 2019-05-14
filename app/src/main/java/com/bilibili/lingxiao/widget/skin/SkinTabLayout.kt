package com.bilibili.lingxiao.widget.skin

import android.content.Context
import android.content.res.TypedArray
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewCompat
import android.support.v7.content.res.AppCompatResources
import android.util.AttributeSet

import com.bilibili.lingxiao.R
import skin.support.content.res.SkinCompatResources
import skin.support.design.widget.SkinMaterialTabLayout
import skin.support.widget.SkinCompatHelper
import skin.support.widget.SkinCompatSupportable

import skin.support.widget.SkinCompatHelper.INVALID_ID

class SkinTabLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    TabLayout(context, attrs, defStyleAttr), SkinCompatSupportable {
    private var mTabIndicatorColorResId = INVALID_ID
    private var mTabTextColorsResId = INVALID_ID
    private var mTabSelectedTextColorResId = INVALID_ID
    private var mTabBackgroundColorResId = INVALID_ID

    init {
        val a = context.obtainStyledAttributes(
            attrs, skin.support.design.R.styleable.TabLayout,
            defStyleAttr, 0
        )

        mTabIndicatorColorResId =
            a.getResourceId(skin.support.design.R.styleable.TabLayout_tabIndicatorColor, INVALID_ID)

        val tabTextAppearance = a.getResourceId(
            skin.support.design.R.styleable.TabLayout_tabTextAppearance,
            skin.support.design.R.style.TextAppearance_Design_Tab
        )

        // Text colors/sizes come from the text appearance first
        val ta = context.obtainStyledAttributes(tabTextAppearance, skin.support.design.R.styleable.SkinTextAppearance)
        try {
            mTabTextColorsResId =
                ta.getResourceId(skin.support.design.R.styleable.SkinTextAppearance_android_textColor, INVALID_ID)
        } finally {
            ta.recycle()
        }

        if (a.hasValue(skin.support.design.R.styleable.TabLayout_tabTextColor)) {
            // If we have an explicit text color set, use it instead
            mTabTextColorsResId = a.getResourceId(skin.support.design.R.styleable.TabLayout_tabTextColor, INVALID_ID)
        }

        if (a.hasValue(skin.support.design.R.styleable.TabLayout_tabSelectedTextColor)) {
            // We have an explicit selected text color set, so we need to make merge it with the
            // current colors. This is exposed so that developers can use theme attributes to set
            // this (theme attrs in ColorStateLists are Lollipop+)
            mTabSelectedTextColorResId =
                a.getResourceId(skin.support.design.R.styleable.TabLayout_tabSelectedTextColor, INVALID_ID)
        }

        mTabBackgroundColorResId = a.getResourceId(skin.support.design.R.styleable.TabLayout_tabBackground, INVALID_ID)
        a.recycle()
        applySkin()
    }

    override fun applySkin() {
        mTabIndicatorColorResId = SkinCompatHelper.checkResourceId(mTabIndicatorColorResId)
        if (mTabIndicatorColorResId != INVALID_ID) {
            setBackgroundColor(SkinCompatResources.getColor(context, mTabIndicatorColorResId))
            //setSelectedTabIndicatorColor(SkinCompatResources.getColor(getContext(), mTabIndicatorColorResId));
            setSelectedTabIndicatorColor(resources.getColor(R.color.white))
        }
        mTabTextColorsResId = SkinCompatHelper.checkResourceId(mTabTextColorsResId)
        if (mTabTextColorsResId != INVALID_ID) {
            tabTextColors = SkinCompatResources.getColorStateList(context, mTabTextColorsResId)
        }
        mTabSelectedTextColorResId = SkinCompatHelper.checkResourceId(mTabSelectedTextColorResId)
        if (mTabSelectedTextColorResId != INVALID_ID) {
            val selected = SkinCompatResources.getColor(context, mTabSelectedTextColorResId)
            if (tabTextColors != null) {
                //不改变文字颜色
                setTabTextColors(tabTextColors!!.defaultColor, selected)
            }
        }
    }
}
