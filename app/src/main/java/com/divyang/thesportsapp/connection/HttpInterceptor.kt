package com.divyang.thesportsapp.connection

import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.io.IOException


class HttpInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (request.body() != null && request.body()?.contentLength() ?: 0 > 0) {
            try {
                val copy = request.newBuilder().build()
                val buffer = Buffer()
                copy.body()?.writeTo(buffer)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val response = chain.proceed(request)
        return run {
            val responseBody = response.body()!!
            val source = responseBody.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()
            response
        }
    }
}