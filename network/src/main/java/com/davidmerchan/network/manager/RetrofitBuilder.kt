package com.davidmerchan.network.manager

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private const val CONNECTION_TIMEOUT = 15L

    private val interceptLogger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(interceptLogger)
        connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
    }.build()

    fun create(): Retrofit = Retrofit.Builder()
        .baseUrl("https://hn.algolia.com/api/v1/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
