package com.bilibili.lingxiao.user

import com.camera.lingxiao.common.app.BasePresenter
import com.hiczp.bilibili.api.BilibiliClient
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
                        val response = login(userName, password)
                        if (response.code == -105){
                            //验证码错误
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