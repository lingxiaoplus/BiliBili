package com.camera.lingxiao.common.example

import com.camera.lingxiao.common.app.BaseView

interface HttpView: BaseView{
    fun onGetResult(result: Array<HttpModle>?)
}