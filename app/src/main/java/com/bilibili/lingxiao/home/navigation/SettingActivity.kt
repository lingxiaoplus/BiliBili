package com.bilibili.lingxiao.home.navigation

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bilibili.lingxiao.GlobalProperties
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.web.WebActivity
import com.camera.lingxiao.common.app.BaseActivity
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.title_bar.*

class SettingActivity : BaseActivity(), View.OnClickListener{
    override val contentLayoutId: Int
        get() = R.layout.activity_setting

    override fun initWidget() {
        super.initWidget()
        setToolbarBack(title_bar)
        title_bar.title = "设置"
        initOnclickListener()
    }

    private fun initOnclickListener() {
        my_service.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.my_service -> {
                var intent = Intent(this@SettingActivity, WebActivity::class.java)
                intent.putExtra("uri",GlobalProperties.MY_SERVICE_HELP)
                intent.putExtra("title","联系客客服")
                startActivity(intent)
            }
        }
    }
}
