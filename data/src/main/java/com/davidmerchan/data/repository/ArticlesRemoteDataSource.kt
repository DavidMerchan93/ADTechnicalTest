package com.davidmerchan.data.repository

import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.repository.ArticleRepository

class ArticlesRemoteDataSource: ArticleRepository {
    override fun getArticles(): List<Article> {
        return emptyList()
    }
}