package com.camera.lingxiao.common.http

import com.google.gson.JsonElement

/**
 * @author lingxiao
 * 数据解析
 */
interface ParseHelper {
    fun parse(element: JsonElement): Any?
}
