package com.camera.lingxiao.common.app

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.camera.lingxiao.common.R
import com.camera.lingxiao.common.listener.LifeCycleListener
import com.camera.lingxiao.common.utills.LogUtils
import com.trello.rxlifecycle2.components.support.RxFragment

import pub.devrel.easypermissions.EasyPermissions

import android.content.ContentValues.TAG
import org.greenrobot.eventbus.EventBus
import pub.devrel.easypermissions.AppSettingsDialog


abstract class BaseFragment : RxFragment(), EasyPermissions.PermissionCallbacks {
    protected var mRoot: View ? = null

    //private var mRootUnbinder: Unbinder? = null
    var mListener: LifeCycleListener? = null
    private var progressDialog: ProgressDialog? = null
    private var firstVisibility = true
    protected var reuseView = true
    protected abstract val contentLayoutId: Int

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mListener?.onAttach(activity)
        initArgs(arguments)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mListener != null) {
            mListener!!.onCreate(savedInstanceState)
        }
        firstVisibility = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        /*if (mRoot == null){
            int layId = getContentLayoutId();
            //初始化当前的跟布局，但是不在创建时就添加到container中
            View root = inflater.inflate(layId,container,false);
            initWidget(root);
            mRoot = root;
            LogUtils.i("BaseFragment是空：");
        }else {
            if (mRoot.getParent() != null){
                //把当前root从父控件中移除
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
                LogUtils.i("BaseFragment不是空,并且执行了移除");
            }
            LogUtils.i("BaseFragment不是空：");
        }*/
        // TODO: 18-6-29 上面的方式会报空指针 ,因为我在p层使用的弱引用
        val layId = contentLayoutId
        val root = inflater.inflate(layId, container, false)
        initInject()
        initWidget(root)
        mRoot = root
        return mRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (mRoot == null){
            mRoot = view
        }
        super.onViewCreated(if (reuseView) mRoot!! else view, savedInstanceState)
        if (userVisibleHint){
            if (firstVisibility){
                onFirstVisiblity()
                firstVisibility = false
            }
            onVisiblityChanged(true)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mListener?.onActivityCreated(savedInstanceState)
        initData()
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //单个的fragment不会调用这个方法
        if (mRoot == null){
            return
        }
        if (firstVisibility){
            if (isVisibleToUser){
                onFirstVisiblity()
                firstVisibility = false
            }
        }else{
            onVisiblityChanged(isVisibleToUser)
        }
    }

    override fun onStart() {
        super.onStart()
        mListener?.onStart()
        if (isRegisterEventBus()){
            EventBus.getDefault().register(this)
        }
    }

    override fun onResume() {
        super.onResume()
        mListener?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mListener?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mListener?.onStop()
        if (isRegisterEventBus()){
            EventBus.getDefault().unregister(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mListener?.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        mListener?.onDestroy()
        Log.e(TAG, "onDestroy: fragment销毁了")
        //移除view绑定
        //mRootUnbinder?.unbind()
    }

    override fun onDetach() {
        super.onDetach()
        mListener?.onDetach()
    }

    /**
     * 初始化dagger注入
     */
    protected open fun initInject(){

    }

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected open fun isRegisterEventBus() :Boolean{
        return false
    }

    /**
     * 初始化控件
     */
    protected open fun initWidget(root: View) {
        //mRootUnbinder = ButterKnife.bind(this, root)
    }

    /**
     * 初始化数据
     */
    protected open fun initData() {

    }

    /**
     * 初始化相关参数
     */
    protected open fun initArgs(bundle: Bundle?) {}

    /**
     * 是否开启懒加载，如果只有一个fragment，则重写该方法
     * @return
     */
    protected fun setLazyMode(): Boolean {
        return false
    }

    /**
     * fragment首次可见
     */
    protected open fun onFirstVisiblity(){
        LogUtils.e("fragment对用户可见了")
    }

    protected open fun onVisiblityChanged(visiblity:Boolean){

    }

    /**
     * 返回按键出发
     * @return true代表拦截
     */
    fun onBackPressed(): Boolean {
        return false
    }

    protected fun setSwipeColor(swipeLayout: SwipeRefreshLayout) {
        swipeLayout.setColorSchemeResources(
            R.color.colorPrimary,
            android.R.color.holo_blue_light,
            android.R.color.holo_red_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_green_light
        )
    }

    /**
     * 滑动显示隐藏floatingactionbutton
     * @param recyclerView
     * @param fab
     */
    protected fun floatingBtnToogle(recyclerView: RecyclerView, fab: FloatingActionButton) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    fab.hide()
                } else {
                    fab.show()
                }
            }
        })

        fab.setOnClickListener { recyclerView.smoothScrollToPosition(0) }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    /**
     * 设置生命周期回调函数
     *
     * @param listener
     */
    fun setOnLifeCycleListener(listener: LifeCycleListener) {
        mListener = listener
    }

    /**
     * 显示进度条
     */
    fun showProgressDialog(msg: String, context: Context) {
        progressDialog = ProgressDialog(context)
        progressDialog?.setMessage(msg)
        progressDialog?.show()
    }

    fun cancleProgressDialog() {
        progressDialog?.dismiss()
    }
}
