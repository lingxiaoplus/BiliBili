package com.camera.lingxiao.common.http


import com.camera.lingxiao.common.utills.LogUtils
import com.google.gson.Gson

import io.reactivex.functions.Function

class OtherServerFunction<T> : Function<T, Any> {
    override fun apply(t: T): Any {
        //其他api..未规范
        return Gson().toJson(t)
    }
}
