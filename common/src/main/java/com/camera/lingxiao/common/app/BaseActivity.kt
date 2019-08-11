package com.camera.lingxiao.common.app

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.Toolbar
import android.util.Log
import com.camera.lingxiao.common.Common

import com.camera.lingxiao.common.R
import com.camera.lingxiao.common.rxbus.RxBus
import com.camera.lingxiao.common.rxbus.SkinChangedEvent
import com.camera.lingxiao.common.listener.LifeCycleListener
import com.camera.lingxiao.common.utills.SpUtils
import com.github.zackratos.ultimatebar.UltimateBar
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.reactivestreams.Subscription

import java.io.File

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

abstract class BaseActivity : RxAppCompatActivity() ,EasyPermissions.PermissionCallbacks{

    private var mPmanager: PackageManager? = null
    private var versionCode: Int = 0
    var mListener: LifeCycleListener? = null
    //protected var unBinder: Unbinder? = null

    private var mBarcolor: Int = 0
    private val mRxBus: Subscription? = null

    private var progressDialog: ProgressDialog? = null

    /**
     * 得到当前界面的资源文件id
     */
    protected abstract val contentLayoutId: Int
    /**
     * 初始化dagger注入
     */
    protected open fun initInject(){


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         * 设置为横屏
         */
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onCreate(savedInstanceState)
        //在界面未初始化之前调用的初始化窗口
        initWindows()
        mListener?.onCreate(savedInstanceState)
        if (initArgs(intent.extras)) {
            // 得到界面Id并设置到Activity界面中
            val layId = contentLayoutId
            if (layId != 0) {
                setContentView(layId)
            }
            initInject()
            initBefore()
            initWidget()
            initData()
        } else {
            finish()
        }
    }

    /**
     * 初始化控件调用之前
     */
    protected open fun initBefore() {
        mBarcolor = SpUtils.getInt(this, Common.SKIN_ID, ContextCompat.getColor(this,R.color.colorPrimary))
        //半透明
        UltimateBar.newColorBuilder()
            .statusColor(mBarcolor)   // 状态栏颜色
            .applyNav(true)             // 是否应用到导航栏
            .navColor(mBarcolor)         // 导航栏颜色
            .navDepth(0)            // 导航栏颜色深度
            .build(this)
            .apply()
        ActivityController.addActivity(this)
    }

    /**
     * 初始化
     */
    private fun initWindows() {

    }

    /**
     * 初始化相关参数
     * 如果参数正确返回True，错误返回False
     */
    protected fun initArgs(bundle: Bundle?): Boolean {
        return true
    }

    /**
     * 初始化控件
     */
    protected open fun initWidget() {
        //unBinder = ButterKnife.bind(this)
        //initSubscription()
    }

    /**
     * 初始化数据
     */
    protected fun initData() {

    }

    /**
     * 皮肤改变调用
     */
    protected open fun isSkinChanged() :Boolean{
        return true
    }

    fun StartActivity(clzz: Class<*>, isFinish: Boolean) {
        startActivity(Intent(applicationContext, clzz))
        if (isFinish) {
            finish()
        }
    }

    /**
     * 订阅消息
     */
    private fun initSubscription() {
        val regist = RxBus.getInstance().register(SkinChangedEvent::class.java) { skinChangedEvent ->
            UltimateBar.newColorBuilder()
                .statusColor(ContextCompat.getColor(applicationContext, skinChangedEvent.color))   // 状态栏颜色
                .applyNav(true)             // 是否应用到导航栏
                .navColor(ContextCompat.getColor(applicationContext, skinChangedEvent.color))         // 导航栏颜色
                .build(this@BaseActivity)
                .apply()
        }
        RxBus.getInstance().addSubscription(this, regist)
    }


    /**
     * 检查更新
     */
    fun checkUpdate(): Boolean {

        mPmanager = packageManager
        val serverVersion = SpUtils
            .getInt(this@BaseActivity, ContentValue.VERSION_CODE, 1)
        val info = mPmanager!!.getPackageInfo(packageName, 0)
        versionCode = info.versionCode
        return if (versionCode < serverVersion) {
            true
        } else {
            false
        }
    }


    private fun showDialog(url: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("检测到新版本")
        builder.setMessage(SpUtils.getString(this, ContentValue.VERSION_DES, ""))
        builder.setPositiveButton("下载apk") { dialogInterface, i ->
            //下载
            //showDownLoadDia();
            //downLoadApk(url);
            goToMarket(this@BaseActivity, packageName)
        }
        builder.setNegativeButton("取消") { dialogInterface, i -> dialogInterface.cancel() }
        builder.show()
    }

    /**
     * 下载成功后安装
     */
    private fun installApk(file: File) {
        if (Build.VERSION.SDK_INT >= 24) {//判读版本是否在7.0以上
            val apkUri = FileProvider.getUriForFile(
                this,
                "com.lingxiaosuse.picture.tudimension.fileprovider",
                file
            )//在AndroidManifest中的android:authorities值
            val install = Intent(Intent.ACTION_VIEW)
            install.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            install.setDataAndType(apkUri, "application/vnd.android.package-archive")
            startActivity(install)
        } else {
            val install = Intent(Intent.ACTION_VIEW)
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
            install.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(install)
        }
    }

    /**
     * 设置toolbar的返回键
     */
    fun setToolbarBack(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)
        }
        //toolbar.title = ""
    }

    //跳转到网页
    fun goToInternet(context: Context, marketUrl: String) {
        val uri = Uri.parse(marketUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            //拒绝授权后，从系统设置了授权后，返回APP进行相应的操作
        }
    }
    override fun onBackPressed() {
        // 得到当前Activity下的所有Fragment
        @SuppressLint("RestrictedApi")
        val fragments = supportFragmentManager.fragments
        // 判断是否为空
        if (fragments.size > 0) {
            for (fragment in fragments) {
                // 判断是否为我们能够处理的Fragment类型
                if (fragment is BaseFragment) {
                    // 判断是否拦截了返回按钮
                    if (fragment.onBackPressed()) {
                        // 如果有直接Return
                        return
                    }
                }
            }
        }
        super.onBackPressed()
    }

    override fun onStart() {
        super.onStart()
        mListener?.onStart()
        if (isRegisterEventBus()){
            EventBus.getDefault().register(this)
        }
    }

    override fun onRestart() {
        super.onRestart()
        mListener?.onRestart()

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

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected open fun isRegisterEventBus() :Boolean{
        return false
    }
    /**
     * 当点击界面导航返回时，Finish当前界面
     * @return
     */
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancleProgressDialog()
        mListener?.onDestroy()
        //移除view绑定
        //unBinder?.unbind()
        RxBus.getInstance().unSubscribe(this)
        ActivityController.removeActivity(this)
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
    fun showProgressDialog(msg: String) {
        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage(msg)
        progressDialog?.show()
    }

    fun cancleProgressDialog() {
        progressDialog?.dismiss()
    }

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }

        /**
         * @param packageName 目标应用的包名
         */
        fun goToMarket(context: Context, packageName: String) {
            val uri = Uri.parse("market://details?id=$packageName")
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            try {
                context.startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }

        }
    }
}
