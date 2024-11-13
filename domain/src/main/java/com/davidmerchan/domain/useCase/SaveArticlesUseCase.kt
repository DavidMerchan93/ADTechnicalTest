package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.repository.ArticleLocalManagerRepository
import javax.inject.Inject

class SaveArticlesUseCase @Inject constructor(
    private val articleManager: ArticleLocalManagerRepository
) {
    operator fun invoke(articles: List<Article>) =
        when (val result = articleManager.saveArticles(articles)) {
            is Resource.Success -> result.data
            is Resource.Error -> throw result.exception
        }
}
