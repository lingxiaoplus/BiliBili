package com.bilibili.lingxiao.widget.behavior

import android.content.Context
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.ViewCompat.setY
import androidx.core.view.ViewCompat.setX
import android.opengl.ETC1.getWidth
import android.R.attr.dependency
import android.opengl.ETC1.getHeight



class TransferHeaderBehavior(context: Context?, attrs: AttributeSet?) :
    androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior<LinearLayout>(context, attrs) {
    /**
     * 处于中心时候原始X轴
     */
    private var mOriginalHeaderX = 0
    /**
     * 处于中心时候原始Y轴
     */
    private var mOriginalHeaderY = 0

    override fun layoutDependsOn(parent: androidx.coordinatorlayout.widget.CoordinatorLayout, child: LinearLayout, dependency: View): Boolean {
        return dependency is NestedScrollView
    }

    override fun onDependentViewChanged(parent: androidx.coordinatorlayout.widget.CoordinatorLayout, child: LinearLayout, dependency: View): Boolean {
        // 计算X轴坐标
        if (mOriginalHeaderX == 0) {
            this.mOriginalHeaderX = dependency.width  - child.width
        }
        // 计算Y轴坐标
        /*if (mOriginalHeaderY == 0) {
            mOriginalHeaderY = dependency.height - child.height
        }*/
        //X轴百分比
        var mPercentX = dependency.y / mOriginalHeaderX
        if (mPercentX >= 1) {
            mPercentX = 1f
        }
        //Y轴百分比
        var mPercentY = dependency.y / mOriginalHeaderY
        if (mPercentY >= 1) {
            mPercentY = 1f
        }

        var x = mOriginalHeaderX - mOriginalHeaderX * mPercentX
        if (x <= child.width) {
            x = child.width.toFloat()
        }
        // TODO 头像的放大和缩小没做

        child.x = x
        child.y = mOriginalHeaderY - mOriginalHeaderY * mPercentY
        return true
    }
}