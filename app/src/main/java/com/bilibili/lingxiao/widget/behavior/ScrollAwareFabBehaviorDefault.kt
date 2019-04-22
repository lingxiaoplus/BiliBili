package com.bilibili.lingxiao.widget.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View

/**
 * Created by lingxiao on 2017/10/22.
 */

class ScrollAwareFabBehaviorDefault(context: Context, attr: AttributeSet) : FloatingActionButton.Behavior() {

    private var visible = true //是否可见
    //列表（RecyclerView）刚开始滑动时候会回调该方法，需要在方法内设置滑动关联轴。这里只需要垂直方向上的滑动即可。
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton, directTargetChild: View,
        target: View, nestedScrollAxes: Int
    ): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(
            coordinatorLayout, child,
            directTargetChild, target, nestedScrollAxes
        )
    }

    //onNestedScroll：滑动的时候不断的回调该方法，通过dyConsumed来判断是上滑还是下滑。
    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton, target: View,
        dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int
    ) {
        super.onNestedScroll(
            coordinatorLayout,
            child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed
        )
        if (dyConsumed > 0 && visible) {
            // User scrolled down and the FAB is currently visible -> hide the FAB
            //fab只隐藏，不显示，在hide方法里设置为GONE，所以不会再调用onNestedScroll方法
            child.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
                override fun onHidden(fab: FloatingActionButton?) {
                    super.onHidden(fab)
                    fab!!.visibility = View.INVISIBLE
                }
            })
            visible = false
        } else if (dyConsumed < 0) {
            // User scrolled up and the FAB is currently not visible -> show the FAB
            child.show()
            visible = true
        }
    }


}
