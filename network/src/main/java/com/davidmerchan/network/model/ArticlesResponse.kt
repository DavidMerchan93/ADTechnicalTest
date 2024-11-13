package com.davidmerchan.network.model

import com.google.gson.annotations.SerializedName

data class ArticlesResponse(
    @SerializedName("hits")
    val hits: List<Hits>
)
