package com.camera.lingxiao.common.app

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup

import com.camera.lingxiao.common.listener.LifeCycleListener

import java.lang.ref.Reference
import java.lang.ref.WeakReference

/**
 * @author lingxiao
 * 使用java弱引用，管理View的引用，以及activity的引用，
 * 避免强引用导致资源无法释放而造成的内存溢出
 * @param <V>
 * @param <T>
</T></V> */
open class BasePresenter<V, T>(view: V, activity: T) : LifeCycleListener {

    protected var mViewRef: Reference<V>? = null
    protected var mView: V ?= null;
    protected var mActivityRef: Reference<T>? = null
    protected var mActivity: T ?= null;

    /**
     * 获取view
     * @return
     */
    val view: V?
        get() = mViewRef?.get()

    /**
     * 获取activity
     * @return
     */
    val activity: T?
        get() = mActivityRef?.get()

    /**
     * view是否已经关联
     * @return
     */
    val isViewAttached: Boolean
        get() = mViewRef != null && mViewRef?.get() != null

    /**
     * activity是否已经关联
     * @return
     */
    val isActivityAttached: Boolean
        get() = mActivityRef != null && mActivityRef?.get() != null

    init {
        attachView(view)
        attachActivity(activity)
        setListener(activity)
    }

    /**
     * 设置生命周期监听
     * @param activity
     */
    private fun setListener(activity: T) {
        if (activity != null) {
            if (activity is BaseActivity) {
                (activity as BaseActivity).setOnLifeCycleListener(this)
            } else if (activity is BaseFragment) {
                (activity as BaseFragment).setOnLifeCycleListener(this)
            }
        }
    }

    /**
     * 关联act
     * @param activity
     */
    private fun attachActivity(activity: T) {
        mActivityRef = WeakReference(activity)
        mActivity = mActivityRef?.get()
    }

    /**
     * 关联view
     * @param view
     */
    private fun attachView(view: V) {
        mViewRef = WeakReference(view)
        mView = mViewRef?.get()
    }

    /**
     * 销毁view
     */
    private fun detachView() {
        if (isViewAttached) {
            mViewRef?.clear()
            mViewRef = null
        }
    }

    private fun detachActivity() {
        if (isActivityAttached) {
            mActivityRef?.clear()
            mActivityRef = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

    }

    override fun onStart() {

    }

    override fun onRestart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {
        detachActivity()
        detachView()
    }

    override fun onAttach(activity: Activity) {

    }

    override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup, bundle: Bundle) {

    }

    override fun onActivityCreated(bundle: Bundle?) {

    }

    override fun onDestroyView() {

    }

    override fun onDetach() {

    }
}
