package com.bilibili.lingxiao.user

import com.camera.lingxiao.common.app.BaseView
import com.hiczp.bilibili.api.app.model.MyInfo

interface LoginView :BaseView{
    fun onLogin(success :Boolean,error :String? = null,user:MyInfo?=null)
    fun onLogout()
}