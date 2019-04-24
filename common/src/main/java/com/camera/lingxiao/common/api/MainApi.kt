package com.camera.lingxiao.common.api

import com.camera.lingxiao.common.body.CosplayBody
import com.camera.lingxiao.common.http.response.HttpResponse
import java.util.TreeMap

import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface MainApi {
    /**
     * GET请求
     *
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @GET
    operator fun get(@Url url: String, @QueryMap request: TreeMap<String, Any>): Observable<HttpResponse>

    /**
     * POST请求
     *
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @FormUrlEncoded
    @POST
    fun post(@Url url: String, @FieldMap request: TreeMap<String, Any>): Observable<HttpResponse>

    /**
     * 全路径Get调用
     * @param url 全路径
     * @return
     */
    @GET
    fun fullPathGet(@Url url: String, @QueryMap request: TreeMap<String, Any>): Observable<HttpResponse>
    /**
     * 全路径Post调用
     * @param url 全路径
     * @return
     */
    @FormUrlEncoded
    @POST
    fun fullPathPost(@Url url: String, @FieldMap request: TreeMap<String, Any>): Observable<HttpResponse>


    /**
     * 全路径Get调用
     * @param url 全路径
     * @return
     */
    @GET
    fun fullPathGetWithoutCheck(@Url url: String,@QueryMap request: TreeMap<String, Any>): Observable<Any>
    /**
     * 全路径Post调用
     * @param url 全路径
     * @return
     */
    @FormUrlEncoded
    @POST
    fun fullPathPostWithoutCheck(@Url url: String,@QueryMap request: TreeMap<String, Any>): Observable<Any>

    /**
     * 首页轮播图
     * /v1/wallpaper/album/{id}/wallpaper/limit/skip/adult/order
     */
    @GET("/{version}/{wallpaper}/{category}/{id}/{wallpapertype}")
    fun desk(
        @Path("version") version: String,
        @Path("wallpaper") wallpaper: String,
        @Path("category") category: String,
        @Path("id") id: String,
        @Path("wallpapertype") wallpapertype: String,
        @QueryMap request: TreeMap<String, Any>
    ): Observable<HttpResponse>

    /**
     * 其他api调用
     * @param url 全路径
     * @return
     */
    @FormUrlEncoded
    @POST
    fun otherHeader(
        @Url url: String, @HeaderMap headers: Map<String, String>,
        @FieldMap values: Map<String, Any>
    ): Observable<Any>


    /**
     * 短信验证码
     * @param url 全路径
     * @return
     */
    @POST
    fun sendCode(
        @Url url: String, @HeaderMap headers: Map<String, String>,
        @Body body: RequestBody
    ): Observable<Any>
}
