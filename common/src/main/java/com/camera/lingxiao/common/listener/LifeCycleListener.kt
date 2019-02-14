package com.camera.lingxiao.common.listener

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup

interface LifeCycleListener {
    /**
     * Activity相关生命周期回调
     */
    fun onCreate(savedInstanceState: Bundle?)

    fun onStart()

    fun onRestart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onDestroy()

    /**
     * Fragment(特有)相关生命周期回调
     */

    fun onAttach(activity: Activity)

    fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup, bundle: Bundle)

    fun onActivityCreated(bundle: Bundle?)

    fun onDestroyView()

    fun onDetach()
}
