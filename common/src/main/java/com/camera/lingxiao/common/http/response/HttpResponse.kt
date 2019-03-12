package com.camera.lingxiao.common.http.response

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

/**
 * @author lingxiao
 * http响应参数实体类
 * 通过Gson解析属性名称需要与服务器返回字段对应,或者使用注解@SerializedName
 * 备注:这里与服务器约定返回格式
 */
class HttpResponse {
    /**
     * 描述信息
     */
    @SerializedName("message")
    var msg: String? = null

    /**
     * 状态码
     */
    @SerializedName("code")
    var code: Int = 0

    /**
     * 数据对象[成功返回对象,失败返回错误说明]
     */
    @SerializedName("data")
    var results: Any? = null

    /**
     * 是否成功(这里约定200或者0)
     *
     * @return
     */
    val isSuccess: Boolean
        get() = if (code == 200 || code == 0) {
            true
        } else false

    /*val isSuccess: Boolean
        get() = if (!error) {
            true
        } else false*/

    override fun toString(): String {
        return "HttpResponse(msg=$msg, code=$code, results=$results)"
    }

}
