package com.bilibili.lingxiao

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.camera.lingxiao.common.app.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_main;

    override fun initWidget() {
        super.initWidget()
        sample_text.text = stringFromJNI()
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
