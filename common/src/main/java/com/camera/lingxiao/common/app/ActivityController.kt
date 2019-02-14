package com.camera.lingxiao.common.app

import android.app.Activity

import java.util.ArrayList

/**
 * Created by lingxiao on 2017/8/3.
 */

object ActivityController {
    var activities: MutableList<Activity> = ArrayList()
    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    fun finishAll() {
        for (activity in activities) {
            activity.finish()
        }
        activities.clear()
    }
}
