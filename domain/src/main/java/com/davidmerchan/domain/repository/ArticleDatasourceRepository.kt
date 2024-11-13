package com.davidmerchan.domain.repository

import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.Resource

interface ArticleDatasourceRepository {
    suspend fun getArticles(): Resource<List<Article>>
}
