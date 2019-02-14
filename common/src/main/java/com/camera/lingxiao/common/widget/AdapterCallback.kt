package com.camera.lingxiao.common.widget

interface AdapterCallback<Data> {
    fun update(data: Data, holder: BaseHolder<Data>)
}
