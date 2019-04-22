package com.bilibili.lingxiao.widget.behavior

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View
import com.bilibili.lingxiao.ijkplayer.widget.SimplePlayerView

class PlayerBehavior : AppBarLayout.ScrollingViewBehavior {
    //player顶部和toolbar底部重合时，列表的滑动距离。
    private var deltaY: Float = 0.toFloat()

    constructor() {

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency is SimplePlayerView
    }


    /**
     * player的状态发生改变
     * @param parent  CoordinatorLayout
     * @param child  就是toolbar
     * @param dependency  player
     * @return
     */
    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        if (deltaY == 0f) {
            deltaY = dependency.y - child.height
        }
        var dy = dependency.y - child.height
        dy = if (dy < 0f) 0f else dy
        val alpha = 1f - dy / deltaY
        child.alpha = alpha
        return super.onDependentViewChanged(parent, child, dependency)
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {

        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        if (target is SimplePlayerView) {
            if (target.isPlaying()) {

            }
        }
    }
}
