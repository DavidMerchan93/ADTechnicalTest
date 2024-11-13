package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.repository.ArticleDatasourceRepository

class GetArticlesUseCase(
    private val articlesRepository: ArticleDatasourceRepository
) {
    suspend operator fun invoke() {
        val data = articlesRepository.getArticles()
        data.forEach {
            println(it.title)
        }
    }
}
