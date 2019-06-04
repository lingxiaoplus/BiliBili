package com.bilibili.lingxiao.web

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import com.bilibili.lingxiao.GlobalProperties
import com.bilibili.lingxiao.R
import com.camera.lingxiao.common.app.BaseActivity
import com.github.zackratos.ultimatebar.UltimateBar
import kotlinx.android.synthetic.main.activity_web.*
import android.content.Intent
import android.graphics.Bitmap
import android.opengl.Visibility
import android.util.Log
import android.view.View
import com.camera.lingxiao.common.rxbus.SkinChangedEvent
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class WebActivity : BaseActivity() {
    val TAG = WebActivity::class.java.simpleName

    override val contentLayoutId: Int
        get() = R.layout.activity_web

    override fun initBefore() {
        super.initBefore()
        UltimateBar.newTransparentBuilder()
            .statusColor(getResources().getColor(R.color.trans))        // 状态栏颜色
            .statusAlpha(100)               // 状态栏透明度
            .applyNav(false)                // 是否应用到导航栏
            .build(this)
            .apply();
    }
    override fun isRegisterEventBus(): Boolean {
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onSkinChanged(event : SkinChangedEvent){
        UltimateBar.newColorBuilder()
            .statusColor(event.color)   // 状态栏颜色
            .applyNav(true)             // 是否应用到导航栏
            .navColor(event.color)         // 导航栏颜色
            .build(this)
            .apply()

    }
    override fun initWidget() {
        super.initWidget()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val uri = intent.getStringExtra("uri")
        if (intent.hasExtra("image")){
            val image = intent.getStringExtra("image")
            image_bar.setImageURI(Uri.parse(image))
        }
        if (intent.hasExtra("title")){
            val title = intent.getStringExtra("title")
            toolbar.setTitle(title)
        }
        initWebView()
        var extHeader = HashMap<String, String>()
        extHeader.put(
            "User-Agent",
            GlobalProperties.USER_AGENT
        )
        webview.loadUrl(uri)
        webview.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                //val hit = view.getHitTestResult()
                if ("https".equals(Uri.parse(url).scheme,true)) {
                    view.loadUrl(url)
                    Log.e(TAG,"请求地址：" + url)
                }else{
                    if ("bilibili".equals(Uri.parse(url).scheme,true)){
                        return true
                    }
                }
                //返回true代表在当前webview中打开，返回false表示打开浏览器
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun onPageStarted(p0: WebView?, p1: String?, p2: Bitmap?) {
                super.onPageStarted(p0, p1, p2)
                progressBar.visibility = View.VISIBLE
            }
            override fun onPageFinished(p0: WebView?, p1: String?) {
                super.onPageFinished(p0, p1)
                progressBar.visibility = View.GONE
            }
        }

        webview.webChromeClient = object :WebChromeClient(){
            override fun onProgressChanged(webview: WebView?, progress: Int) {
                super.onProgressChanged(webview, progress)
                progressBar.progress = progress
            }
        }
    }

    private fun initWebView() {
        //声明WebSettings子类
        val webSettings = webview.getSettings()
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true)
        //webSettings.setUserAgentString(GlobalProperties.USER_AGENT);
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        //支持插件
        //webSettings.setPluginsEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true) //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true) // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true) //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false) //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK) //关闭webview中缓存
        webSettings.setAllowFileAccess(true) //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true) //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true) //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8")//设置编码格式
    }
}
