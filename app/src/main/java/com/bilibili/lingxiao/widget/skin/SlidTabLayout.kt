package com.bilibili.lingxiao.widget.skin

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.bilibili.lingxiao.R
import com.flyco.tablayout.SlidingTabLayout
import skin.support.widget.SkinCompatBackgroundHelper
import skin.support.widget.SkinCompatHelper
import skin.support.widget.SkinCompatSupportable

class SlidTabLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    :SlidingTabLayout(context, attrs, defStyleAttr), SkinCompatSupportable {
    private var mBackgroundTintHelper: SkinCompatBackgroundHelper? = null
    init {
        mBackgroundTintHelper = SkinCompatBackgroundHelper(this)
        mBackgroundTintHelper?.loadFromAttributes(attrs, defStyleAttr)
    }

    override fun setBackgroundResource(resid: Int) {
        super.setBackgroundResource(resid)
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper?.onSetBackgroundResource(resid)
        }
    }
    override fun applySkin() {
        mBackgroundTintHelper?.applySkin()
    }
}