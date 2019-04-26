package com.camera.lingxiao.common.exception.crash

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.camera.lingxiao.common.R
import com.camera.lingxiao.common.app.ActivityController
import com.camera.lingxiao.common.app.BaseActivity
import kotlinx.android.synthetic.main.activity_crash.*

class CrashActivity : BaseActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_crash

    override fun initWidget() {
        super.initWidget()
        val intent = intent
        val errorMsg = intent.getStringExtra("msg")
        tv_errormsg.setText(errorMsg)
        setToolbarBack(toolbar_title)
        toolbar_title.setTitle("程序坏了")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            ActivityController.finishAll()
            finish()
            System.exit(0)
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ActivityController.finishAll()
        finish()
        System.exit(0)
    }
}
