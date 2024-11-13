package com.davidmerchan.domain.repository

import com.davidmerchan.domain.entitie.Article

interface ArticleDatasourceRepository {
    fun getArticles(): List<Article>
}
