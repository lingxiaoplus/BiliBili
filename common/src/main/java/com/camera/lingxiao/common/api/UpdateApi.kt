package com.camera.lingxiao.common.api

import com.camera.lingxiao.common.http.response.HttpResponse

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

interface UpdateApi {
    @GET
    fun getVersion(@Url update_url: String): Observable<HttpResponse>
}
