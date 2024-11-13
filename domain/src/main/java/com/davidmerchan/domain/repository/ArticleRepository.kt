package com.davidmerchan.domain.repository

import com.davidmerchan.domain.entitie.Article

interface ArticleRepository {
    fun getArticles(): List<Article>
}
