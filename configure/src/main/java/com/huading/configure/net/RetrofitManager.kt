package com.huading.configure.net

import com.huading.configure.AppConfig
import com.huading.configure.BuildConfig
import com.huading.configure.base.BaseApplication
import com.huading.configure.net.http.CustomGsonConverterFactory
import com.huading.configure.utils.LogUtil
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetrofitManager {

    @Volatile
    private var mRetrofit: Retrofit? = null

    init {
        initRetrofit()
    }

    /**
     * 设置头
     * 可添加请求头参数
     * .header("Content-Type", "application/json; charset=utf-8")
     * .method(originalRequest.method(), originalRequest.body())
     */
    private fun addHeaderInterceptor(): Interceptor {

        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    fun initRetrofit() {
        LogUtil.i("RetrofitManager init")
        val builder = OkHttpClient.Builder()
        val loggingInterceptor =
            HttpLoggingInterceptor(
                HttpLoggingInterceptor.Logger { msg -> LogUtil.e(msg) })//不重写,部分手机平板需要打开日志

        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        //设置 请求的缓存的大小跟位置
        val cacheFile = File(BaseApplication().sInstance.cacheDir, "cache")
        val cache = Cache(cacheFile, 1024 * 1024 * 50) //50Mb 缓存的大小

        builder.addInterceptor(loggingInterceptor) //日誌
            .addInterceptor(addHeaderInterceptor()) // 請求頭
            .cache(cache)  //添加缓存
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)

        mRetrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(CustomGsonConverterFactory.create())
            .baseUrl(AppConfig.instance.HOST)
            .client(builder.build())
            .build()
    }

    fun <T> getApiServier(reqServer: Class<T>): T {
        return mRetrofit!!.create(reqServer)
    }
}