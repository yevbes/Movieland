package com.yevbes.movieland.repository.remote

import com.yevbes.movieland.App
import com.yevbes.movieland.repository.interceptor.HeaderInterceptor
import com.yevbes.movieland.utils.AppConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator {
    companion object {
        // Builder for client
        private val httpClient = OkHttpClient.Builder()

        // Caching response
        private const val cacheSize = 10 * 1024 * 1024 // 10 MB
        private val cache = Cache(App.getApplication().cacheDir, cacheSize.toLong())

        // Builder for Rest Service
        private val sBuilder = Retrofit.Builder()
            .baseUrl(AppConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        // Create Rest Service
        @JvmStatic
        fun <S> createService(serviceClass: Class<S>): S {
            // HttpLoggingInterceptor
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            httpClient.addInterceptor(ErrorInterceptor())
            httpClient.addInterceptor(HeaderInterceptor())
            httpClient.addInterceptor(loggingInterceptor)
            httpClient.cache(cache)

            val retrofit = sBuilder
                .client(httpClient.build())
                .build()
            return retrofit.create(serviceClass)
        }
    }
}