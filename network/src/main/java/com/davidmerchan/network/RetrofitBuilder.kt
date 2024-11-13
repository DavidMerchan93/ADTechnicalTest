package com.davidmerchan.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    fun create(): Retrofit = Retrofit.Builder()
        .baseUrl("https://hn.algolia.com/api/v1")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
