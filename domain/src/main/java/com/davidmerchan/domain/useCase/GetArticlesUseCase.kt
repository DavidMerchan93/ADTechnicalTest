package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.repository.ArticleDatasourceRepository
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticleDatasourceRepository
) {
    suspend operator fun invoke() {
        val data = articlesRepository.getArticles()
        data.forEach {
            println(it.title)
        }
    }
}
