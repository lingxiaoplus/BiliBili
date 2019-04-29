package com.camera.lingxiao.common.utills

import android.util.Log
import com.camera.lingxiao.common.app.ContentValue

import java.io.IOException
import java.util.concurrent.TimeUnit

import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.NoSuchAlgorithmException

class RetrofitUtil private constructor() {

    /**
     * 获取retrofit
     * @return
     */
    fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient())
            .baseUrl(ContentValue.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  //指定Gson作为解析Json数据的Converter
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //指定使用RxJava 作为CallAdapter
            .build()
    }

    companion object {
        val CONNECT_TIME_OUT = 30//连接超时时长x秒
        val READ_TIME_OUT = 30//读数据超时时长x秒
        val WRITE_TIME_OUT = 30//写数据接超时时长x秒
        private var mInstance: RetrofitUtil? = null
        var TAG = RetrofitUtil::class.java.simpleName
        fun get(): RetrofitUtil {
            if (mInstance == null) {
                synchronized(RetrofitUtil::class.java) {
                    if (mInstance == null) {
                        mInstance = RetrofitUtil()
                    }
                }
            }
            return mInstance!!
        }

        /**
         * 设置okhttp参数
         * @return
         */
        private fun okHttpClient(): OkHttpClient {
            //开启log
            return OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
                /*.addInterceptor(object :Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        var oldRequest = chain.request();
                        var sign = getSign(oldRequest.url());
                        //添加sign参数
                        var newBuilder = oldRequest.url()
                            .newBuilder()
                            .scheme(oldRequest.url().scheme())
                            .host(oldRequest.url().host())
                            .addQueryParameter(ApiHelper.PARAM_SIGN, sign)
                        var newRequest = oldRequest.newBuilder()
                            .method(oldRequest.method(), oldRequest.body())
                            .url(newBuilder.build())
                            .build()
                        return chain.proceed(newRequest);
                    }

                })*/
                .build()
        }
    }


}
