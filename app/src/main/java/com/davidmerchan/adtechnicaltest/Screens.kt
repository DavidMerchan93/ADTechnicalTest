package com.davidmerchan.adtechnicaltest

import kotlinx.serialization.Serializable

object Screens  {

    @Serializable
    object ArticleList

    @Serializable
    data class ArticleDetail(val id: Long, val url: String)
}
