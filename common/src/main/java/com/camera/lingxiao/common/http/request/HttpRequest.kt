package com.camera.lingxiao.common.http.request

import com.camera.lingxiao.common.api.MainApi
import com.camera.lingxiao.common.http.response.HttpResponse
import com.camera.lingxiao.common.observable.HttpRxObservable
import com.camera.lingxiao.common.observer.HttpRxCallback
import com.camera.lingxiao.common.utills.LogUtils
import com.camera.lingxiao.common.utills.RetrofitUtil
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent

import java.util.TreeMap

import io.reactivex.Observable

/**
 * http请求类
 */
class HttpRequest {

    private val appKey = "1889b37351288"
    private val k_key = "key"
    /**
     * 获取基础request参数
     * @return
     */
    private//map.put(k_key,appKey);
    val baseRequest: TreeMap<String, Any>
        get() = TreeMap()

    enum class Method {
        GET,
        POST
    }

    /**
     * 发送请求 不管理生命周期
     * @param method
     * @param prams
     * @param callback
     */
    fun request(method: Method, prams: TreeMap<String, Any>, callback: HttpRxCallback<Any>) {
        val apiObservable = handleRequest(method, prams)

        HttpRxObservable.getObservable(apiObservable, callback).subscribe(callback)
    }

    /**
     * 发送请求
     * 备注:自动管理生命周期
     *
     * @param method    请求方式
     * @param lifecycle 实现RxActivity/RxFragment 参数为空不管理生命周期
     * @param prams     参数集合
     * @param callback  回调
     */
    fun request(
        method: Method,
        prams: TreeMap<String, Any>,
        lifecycle: LifecycleProvider<Any>,
        callback: HttpRxCallback<Any>
    ) {
        val apiObservable = handleRequest(method, prams)

        HttpRxObservable.getObservable(apiObservable, lifecycle, callback).subscribe(callback)
    }

    /**
     * 全路径请求
     * 备注:自动管理生命周期
     * @param lifecycle 实现RxActivity/RxFragment 参数为空不管理生命周期
     * @param callback  回调
     */
    fun requestFullPath(
        method: Method,
        url: String, prams: TreeMap<String, Any>, lifecycle: LifecycleProvider<*>?, callback: HttpRxCallback<Any>) {
        val apiObservable: Observable<HttpResponse>
        when(method){
            Method.GET ->{
                apiObservable = RetrofitUtil
                    .get()
                    .retrofit()
                    .create(MainApi::class.java)
                    .fullPathGet(url,prams)
            }
            else->{
                apiObservable = RetrofitUtil
                    .get()
                    .retrofit()
                    .create(MainApi::class.java)
                    .fullPathPost(url,prams)
            }
        }

        HttpRxObservable.getObservable(apiObservable, lifecycle, callback).subscribe(callback)
        //HttpRxObservable.getOtherObservable(apiObservable,lifecycle,callback).subscribe(callback)
    }

    /**
     * 全路径请求
     * 备注:自动管理生命周期
     * @param lifecycle 实现RxActivity/RxFragment 参数为空不管理生命周期
     * @param callback  回调
     */
    fun requestFullPathWithoutCheck(
        method: Method,
        url: String, prams: TreeMap<String, Any>, lifecycle: LifecycleProvider<*>?, callback: HttpRxCallback<Any>) {
        val apiObservable: Observable<Any>
        when(method){
            Method.GET ->{
                apiObservable = RetrofitUtil
                    .get()
                    .retrofit()
                    .create(MainApi::class.java)
                    .fullPathGetWithoutCheck(url,prams)
            }
            else->{
                apiObservable = RetrofitUtil
                    .get()
                    .retrofit()
                    .create(MainApi::class.java)
                    .fullPathPostWithoutCheck(url,prams)
            }
        }
        HttpRxObservable.getOtherObservable(apiObservable,lifecycle,callback).subscribe(callback)
    }

    /**
     * 发送请求
     * 备注:手动指定生命周期-Activity
     *
     * @param method    请求方式
     * @param lifecycle 实现RxActivity
     * @param event     指定生命周期
     * @param prams     参数集合
     * @param callback  回调
     */
    fun request(
        method: Method,
        prams: TreeMap<String, Any>,
        lifecycle: LifecycleProvider<ActivityEvent>,
        event: ActivityEvent,
        callback: HttpRxCallback<Any>
    ) {
        val apiObservable = handleRequest(method, prams)

        HttpRxObservable.getObservable(apiObservable, lifecycle, event, callback).subscribe(callback)
    }

    /**
     * 发送请求
     * 备注:手动指定生命周期-Fragment
     *
     * @param method    请求方式
     * @param lifecycle 实现RxFragment
     * @param event     指定生命周期
     * @param prams     参数集合
     * @param callback  回调
     */
    fun request(
        method: Method,
        prams: TreeMap<String, Any>,
        lifecycle: LifecycleProvider<FragmentEvent>,
        event: FragmentEvent,
        callback: HttpRxCallback<Any>
    ) {
        val apiObservable = handleRequest(method, prams)

        HttpRxObservable.getObservable(apiObservable, lifecycle, event, callback).subscribe(callback)
    }

    /**
     * 发送请求 安卓壁纸
     * 备注:手动指定生命周期-Fragment
     *
     * @param lifecycle 实现RxFragment
     * @param prams     参数集合
     * @param callback  回调
     * @param strings 5 个参数
     */
    fun request(
        prams: TreeMap<String, Any>,
        lifecycle: LifecycleProvider<Any>,
        callback: HttpRxCallback<Any>,
        vararg strings: String
    ) {
        val apiObservable: Observable<HttpResponse>
        val map = baseRequest
        //添加业务参数
        map.putAll(prams)
        //var apiUrl = ""
        if (map.containsKey(API_URL)) {
            //apiUrl = map[API_URL].toString()
            //移除apiurl参数  此参数不纳入业务参数
            map.remove(API_URL)
        }
        apiObservable = RetrofitUtil
            .get()
            .retrofit()
            .create(MainApi::class.java)
            .desk(strings[0], strings[1], strings[2], strings[3], strings[4], map)

        HttpRxObservable.getObservable(apiObservable, lifecycle, callback).subscribe(callback)
    }

    /**
     * 预处理请求
     * @param method 请求方法
     * @param prams 参数集合
     * @return
     */
    private fun handleRequest(method: Method, prams: TreeMap<String, Any>): Observable<HttpResponse> {
        val map = baseRequest
        //添加业务参数
        map.putAll(prams)
        var apiUrl = ""
        if (map.containsKey(API_URL)) {
            apiUrl = map[API_URL].toString()
            //移除apiurl参数  此参数不纳入业务参数
            map.remove(API_URL)
        }
        val apiObservable: Observable<HttpResponse>
        when (method) {
            Method.GET -> apiObservable =
                    RetrofitUtil.get().retrofit().create(MainApi::class.java).get(apiUrl, map)
            Method.POST -> apiObservable =
                    RetrofitUtil.get().retrofit().create(MainApi::class.java).post(apiUrl, map)
            //else -> apiObservable = RetrofitUtil.get().retrofit().create(MainApi::class.java).post(apiUrl, map)
        }
        return apiObservable
    }

    companion object {
        val API_URL = "API_URL"
    }

}

