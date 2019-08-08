package com.bilibili.lingxiao.user

import android.support.design.widget.Snackbar
import android.view.View
import com.bilibili.lingxiao.GlobalProperties
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.utils.ToastUtil
import com.camera.lingxiao.common.app.BaseActivity
import com.camera.lingxiao.common.utills.LogUtils
import com.camera.lingxiao.common.utills.SpUtils
import com.google.gson.Gson
import com.hiczp.bilibili.api.app.model.MyInfo
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus

class LoginActivity : BaseActivity() ,LoginView{
    private val mPresenter by lazy {
        LoginPresenter(this,this)
    }
    override val contentLayoutId: Int
        get() = R.layout.activity_login

    override fun initWidget() {
        super.initWidget()
        setToolbarBack(login_toolbar)
        login_toolbar.title = resources.getString(R.string.login_title)
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

        login.setOnClickListener {
            val username = login_et_username.text.toString()
            val password = login_et_password.text.toString()
            if (username.isEmpty()){
                Snackbar.make(it,resources.getString(R.string.input_username),Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.isEmpty()){
                Snackbar.make(it,resources.getString(R.string.input_password),Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showProgressDialog("登录中...")
            mPresenter.login(username,password)
        }
    }

    override fun showDialog() {

    }

    override fun onLogin(success: Boolean, error: String?, user: MyInfo?) {
        if (success){
            ToastUtil.show("登录成功 ${user?.data?.name}")
            LogUtils.d("登录结果：$user")
            SpUtils.putString(applicationContext, GlobalProperties.USER_INFO, Gson().toJson(user))
            EventBus.getDefault().post(user)
            finish()
        }else{
            ToastUtil.show("登录失败 ${error}")
        }
    }

    override fun onLogout() {

    }

    override fun diamissDialog() {
        cancleProgressDialog()
    }

    override fun showToast(text: String?) {
        ToastUtil.show(text)
    }
}
