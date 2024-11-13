package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.repository.ArticleLocalManagerRepository
import javax.inject.Inject

class SaveArticlesUseCase @Inject constructor(
    private val articleManager: ArticleLocalManagerRepository
) {
    operator fun invoke(articles: List<Article>) {
        articleManager.saveArticles(articles)
    }
}
