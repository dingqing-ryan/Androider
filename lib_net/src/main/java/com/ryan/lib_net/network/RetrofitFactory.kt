package com.ryan.lib_net.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    private const val TIME_OUT: Long = 10000

    val instance: NetService
        get() = Builder()
            .setBaseUrl("https://www.wanandroid.com/")
            .setLogInterceptor(true)
            .builder()

    class Builder {

        private var mBaseUrl: String? = null
        private var mLogInterceptor: Boolean = false

        fun setBaseUrl(baseUrl: String): Builder {
            this.mBaseUrl = baseUrl
            return this
        }

        fun setLogInterceptor(logInterceptor: Boolean): Builder {
            this.mLogInterceptor = logInterceptor
            return this
        }

        fun builder():NetService {
            val okHttpClient = OkHttpClient.Builder()
            okHttpClient.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            okHttpClient.readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            okHttpClient.writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)

            okHttpClient.addInterceptor { chain ->
                val request = chain.request()
                val builder = request.newBuilder()
                    .addHeader("client", "android")
                    .header("Content-Type", "application/json")
                    .method(request.method(), request.body())
                    .build()
                chain.proceed(builder)
            }

            if (mLogInterceptor) {
                //okHttpClient.addInterceptor(new LoggerInterceptor("technology", true));//日志插值器
                okHttpClient.addInterceptor(ResponseInterceptor())//日志插值器
            }

            return Retrofit.Builder()
                .baseUrl(mBaseUrl!!)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient.build())
                .build()
                .create(NetService::class.java)
        }
    }
}
