package com.bilibili.lingxiao.user

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bilibili.lingxiao.R
import com.camera.lingxiao.common.app.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_login

    override fun initWidget() {
        super.initWidget()
        setToolbarBack(login_toolbar)
        login_toolbar.title = "登录"
        login_et_password.setOnFocusChangeListener(object : View.OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus){
                    login_img_22.setImageResource(R.drawable.ic_22_hide)
                    login_img_33.setImageResource(R.drawable.ic_33_hide)
                    login_line_between_password.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    login_line_between_username.setBackgroundColor(resources.getColor(R.color.black_alpha_16))
                }else{
                    login_img_22.setImageResource(R.drawable.ic_22)
                    login_img_33.setImageResource(R.drawable.ic_33)
                    login_line_between_username.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    login_line_between_password.setBackgroundColor(resources.getColor(R.color.black_alpha_16))
                }
            }
        })
    }

}
