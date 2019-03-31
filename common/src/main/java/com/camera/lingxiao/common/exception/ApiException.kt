package com.camera.lingxiao.common.exception

class ApiException : Exception {
    var code: Int = 0//错误码
    var msg: String? = null//错误信息

    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(code: Int, msg: String) {
        this.code = code
        this.msg = msg
    }
}
