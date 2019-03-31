package com.camera.lingxiao.common.http

import android.util.ArrayMap

import io.reactivex.disposables.Disposable

/**
 * @author lingxiao
 */
class RxActionManagerImpl<Any> : RxActionManager<Any> {
    private val mMaps: ArrayMap<Any, Disposable> //处理请求列表

    init {
        mMaps = ArrayMap()
    }

    override fun add(tag: Any, disposable: Disposable) {
        mMaps[tag] = disposable
    }

    override fun remove(tag: Any) {
        if (!mMaps.isEmpty()) {
            mMaps.remove(tag)
        }
    }

    /**
     * 取消订阅事件
     * @param tag
     */
    override fun cancel(tag: Any) {
        if (mMaps.isEmpty()) {
            return
        }
        if (mMaps[tag] == null) {
            return
        }
        //如果不是处于dispose的状态
        //切断所有订阅事件，防止内存泄漏
        if (!mMaps[tag]!!.isDisposed) {
            mMaps[tag]!!.dispose()
        }
    }

    /**
     * 判断是否取消了请求
     * @param tag
     * @return
     */
    fun isDisposed(tag: Any): Boolean? {
        return if (mMaps[tag] == null || mMaps.isEmpty()) {
            true
        } else mMaps[tag]?.isDisposed
    }

    companion object {

        @Volatile
        private var mInstance: RxActionManagerImpl<Any>? = null

        val instance: RxActionManagerImpl<Any>?
            get() {
                if (mInstance == null) {
                    synchronized(RxActionManagerImpl::class.java) {
                        if (mInstance == null) {
                            mInstance =
                                    RxActionManagerImpl()
                        }
                    }
                }
                return mInstance
            }
    }
}
