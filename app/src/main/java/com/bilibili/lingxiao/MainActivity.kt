package com.bilibili.lingxiao

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.camera.lingxiao.common.app.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    var tabArray = arrayOf("直播","推荐","追番","分区","动态","发现")
    override val contentLayoutId: Int
        get() = R.layout.activity_main;

    override fun initWidget() {
        super.initWidget()
        for (name in tabArray){
            main_tabLayout.addTab(main_tabLayout.newTab().setText(name))
        }

    }
}
