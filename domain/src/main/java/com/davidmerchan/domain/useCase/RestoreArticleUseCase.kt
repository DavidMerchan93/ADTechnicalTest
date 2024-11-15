package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.ArticleId
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.repository.ArticleLocalManagerRepository
import javax.inject.Inject

class RestoreArticleUseCase @Inject constructor(
    private val articleManager: ArticleLocalManagerRepository
) {
    operator fun invoke(id: ArticleId) : Resource<Article> = when (val result = articleManager.restoreArticle(id)) {
        is Resource.Success -> result
        is Resource.Error -> result
    }
}