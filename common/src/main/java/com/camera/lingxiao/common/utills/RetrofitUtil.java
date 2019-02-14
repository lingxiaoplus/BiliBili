package com.camera.lingxiao.common.utills;

import com.camera.lingxiao.common.app.ContentValue;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    public static final int CONNECT_TIME_OUT = 30;//连接超时时长x秒
    public static final int READ_TIME_OUT = 30;//读数据超时时长x秒
    public static final int WRITE_TIME_OUT = 30;//写数据接超时时长x秒
    private static RetrofitUtil mInstance = null;

    private RetrofitUtil(){}

    public static RetrofitUtil get(){
        if (mInstance == null){
            synchronized (RetrofitUtil.class){
                if (mInstance == null){
                    mInstance = new RetrofitUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * 设置okhttp参数
     * @return
     */
    private static OkHttpClient okHttpClient(){
        //开启log
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT,TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT,TimeUnit.SECONDS)
                .build();
        return client;
    }

    /**
     * 获取retrofit
     * @return
     */
    public Retrofit retrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient())
                .baseUrl(ContentValue.INSTANCE.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create())  //指定Gson作为解析Json数据的Converter
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //指定使用RxJava 作为CallAdapter
                .build();
        return retrofit;
    }

}
