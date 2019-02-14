package com.camera.lingxiao.common.exception

/**
 * @author lingxiao
 * 自定义的错误类型，一般我们开发中都会跟服务器约定一种接口请求返回的数据:
 * int code：表示接口请求状态，200表示成功，-101表示密码错误等等
 * String msg：表示接口请求返回的描述。success，”token过期”等等
 * Object result：成功是返回的数据
 */
class ServerException(val code: Int, val msg: String) : RuntimeException()
