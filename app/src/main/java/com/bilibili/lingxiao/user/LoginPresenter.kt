package com.bilibili.lingxiao.user

import android.os.Handler
import com.bilibili.lingxiao.GlobalProperties
import com.camera.lingxiao.common.app.BasePresenter
import com.camera.lingxiao.common.utills.SpUtils
import com.google.gson.Gson
import com.hiczp.bilibili.api.BilibiliClient
import com.hiczp.bilibili.api.passport.model.LoginResponse
import com.hiczp.bilibili.api.retrofit.CommonResponse
import com.hiczp.bilibili.api.retrofit.exception.BilibiliApiException
import kotlinx.coroutines.runBlocking

class LoginPresenter(view: LoginView, activity: LoginActivity) :
    BasePresenter<LoginView, LoginActivity>(view, activity) {
    lateinit var commonResponse: CommonResponse

    fun login(userName:String,password:String){
        runBlocking{
                val bilibiliClient = BilibiliClient().apply {
                    try {
                        var loginCache = SpUtils.getString(mActivity,GlobalProperties.LOGIN_RESPONSE,"")
                        if (loginCache.isNotEmpty()){
                            this.loginResponse = Gson().fromJson(loginCache,LoginResponse::class.java)
                        }else{
                            val response = login(userName, password)
                            if (response.code == -105){
                                //验证码错误
                            }
                            SpUtils.putString(mActivity,GlobalProperties.LOGIN_RESPONSE,Gson().toJson(response))
                        }
                        val myInfo = appAPI.myInfo().await()
                        mView?.onLogin(true,null,myInfo)
                    }catch (ex:BilibiliApiException){
                        ex.printStackTrace()
                        mView?.onLogin(false, ex.message)
                    }finally {
                        mView?.diamissDialog()
                    }
                }
        }
    }
}