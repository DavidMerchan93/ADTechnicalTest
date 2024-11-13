package com.davidmerchan.domain.repository

import com.davidmerchan.domain.entitie.Article

interface ArticleDatasourceRepository {
    suspend fun getArticles(): List<Article>
}
