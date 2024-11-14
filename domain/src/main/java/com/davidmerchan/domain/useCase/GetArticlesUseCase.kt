package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.repository.ArticleDatasourceRepository
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticleDatasourceRepository,
) {
    suspend operator fun invoke(): Resource<List<Article>> {
        return when (val result = articlesRepository.getArticles()) {
            is Resource.Success -> {
                Resource.Success(orderArticlesByDate(result.data))
            }

            is Resource.Error -> result
        }
    }

    private fun orderArticlesByDate(articles: List<Article>): List<Article> {
        return articles.filter { it.isDeleted.not() }
            .sortedByDescending { it.createdDate }
            .distinctBy { it.id }
    }
}
