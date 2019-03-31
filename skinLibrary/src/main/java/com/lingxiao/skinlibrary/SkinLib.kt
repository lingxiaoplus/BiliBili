package com.lingxiao.skinlibrary

import android.app.Application

import skin.support.SkinCompatManager
import skin.support.app.SkinCardViewInflater
import skin.support.constraint.app.SkinConstraintViewInflater
import skin.support.design.app.SkinMaterialViewInflater

object SkinLib {
    fun init(context: Application) {
        SkinCompatManager.withoutActivity(context)                         // 基础控件换肤初始化
            .addInflater(SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
            .addInflater(SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
            .addInflater(SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
            .loadSkin()
    }

    /**
     * 后缀加载
     * @param skinName R.color.windowBackgroundColor, 添加对应资源R.color.windowBackgroundColor_night
     */
    fun loadSkin(skinName: String) {
        SkinCompatManager.getInstance().loadSkin(skinName, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN) // 后缀加载
    }

    /**
     *
     * @param skinName
     */
    fun loadSkinBack(skinName: String) {
        SkinCompatManager.getInstance()
            .loadSkin(skinName, SkinCompatManager.SKIN_LOADER_STRATEGY_PREFIX_BUILD_IN) // 前缀加载
    }

    fun resetSkin() {
        // 恢复应用默认皮肤
        SkinCompatManager.getInstance().restoreDefaultTheme()
    }
}
