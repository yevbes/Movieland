package com.yevbes.movieland.service.remote.interceptor

import com.yevbes.movieland.service.remote.error.MovielandException
import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (!response.isSuccessful) {
            throw MovielandException(
                response.code(),
                response.message()
            )
        }
        return response
    }
}