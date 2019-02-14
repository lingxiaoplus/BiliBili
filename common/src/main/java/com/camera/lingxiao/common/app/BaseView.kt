package com.camera.lingxiao.common.app

interface BaseView {
    //显示进度框
    fun showDialog()

    //隐藏进度条
    fun diamissDialog()

    //打吐司
    fun showToast(text: String?)
}
