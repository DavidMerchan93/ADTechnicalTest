package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.repository.ArticleLocalManagerRepository
import javax.inject.Inject

class RestoreAllArticlesUseCase @Inject constructor(
    private val articleManager: ArticleLocalManagerRepository
) {
    operator fun invoke(): Resource<List<Article>> =
        when (val result = articleManager.restoreAllArticles()) {
            is Resource.Success -> result
            is Resource.Error -> result
        }
}