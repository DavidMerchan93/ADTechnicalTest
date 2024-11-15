package com.davidmerchan.presentation.viewModel

import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.useCase.DeleteArticleUseCase
import com.davidmerchan.domain.useCase.GetArticlesUseCase
import com.davidmerchan.domain.useCase.RestoreAllArticlesUseCase
import com.davidmerchan.domain.useCase.RestoreArticleUseCase
import com.davidmerchan.presentation.screen.articles.events.ArticlesUiEvent
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class ArticlesViewModelTest {

    private val getArticlesUseCase: GetArticlesUseCase = mockk()
    private val deleteArticlesUseCase: DeleteArticleUseCase = mockk()
    private val restoreArticleUseCase: RestoreArticleUseCase = mockk()
    private val restoreAllArticlesUseCase: RestoreAllArticlesUseCase = mockk()

    private lateinit var articlesViewModel: ArticlesViewModel

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        articlesViewModel = ArticlesViewModel(
            getArticlesUseCase,
            deleteArticlesUseCase,
            restoreArticleUseCase,
            restoreAllArticlesUseCase
        )
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        confirmVerified(
            getArticlesUseCase,
            deleteArticlesUseCase,
            restoreArticleUseCase,
            restoreAllArticlesUseCase
        )
        Dispatchers.resetMain()
    }

    @Test
    fun `ArticlesViewModel handles LoadArticles event correctly`() = runBlocking {
        // Given
        val article: Article = mockk()
        val expectedArticles = listOf(article)

        coEvery { getArticlesUseCase() } returns Resource.Success(expectedArticles)

        // When
        articlesViewModel.handleArticleEvent(ArticlesUiEvent.LoadArticles)

        // Then
        coVerify { getArticlesUseCase() }

        assert(articlesViewModel.articlesState.value.articles == expectedArticles)
    }

    @Test
    fun `ArticlesViewModel handles error scenarios gracefully`() = runBlocking {
        // Given
        val expectedErrorMessage = "Error fetching articles"

        coEvery { getArticlesUseCase() } returns Resource.Error(Exception(expectedErrorMessage))

        // When
        articlesViewModel.handleArticleEvent(ArticlesUiEvent.LoadArticles)

        // Then
        coVerify { getArticlesUseCase() }
        assert(articlesViewModel.articlesState.value.error == expectedErrorMessage)
    }

    @Test
    fun `ArticlesViewModel handles RestoreAllArticles event correctly`() = runBlocking {
        // Given
        val article: Article = mockk()
        val expectedArticles = listOf(article)

        coEvery { restoreAllArticlesUseCase() } returns Resource.Success(expectedArticles)

        // When
        articlesViewModel.handleArticleEvent(ArticlesUiEvent.RestoreAllArticles)

        // Then
        coVerify {
            getArticlesUseCase()
            restoreAllArticlesUseCase()
        }
        confirmVerified(article)

        assert(articlesViewModel.articlesState.value.articles == expectedArticles)
    }

    @Test
    fun `ArticlesViewModel handles DeleteArticle event correctly`() = runBlocking {
        // Given
        val articleId = 10L
        val article1: Article = mockk {
            every { id } returns articleId
            every { isDeleted } returns false
        }
        val article2: Article = mockk {
            every { id } returns 20L
            every { isDeleted } returns false
        }
        val expectedArticles = listOf(article1, article2)

        coEvery { deleteArticlesUseCase(articleId) } returns Resource.Success(articleId)
        coEvery { getArticlesUseCase() } returns Resource.Success(expectedArticles)

        // When
        articlesViewModel.handleArticleEvent(ArticlesUiEvent.DeleteArticle(articleId))

        // Then
        coVerify {
            deleteArticlesUseCase(articleId)
            getArticlesUseCase()
        }
        confirmVerified(article1, article2)

        val stateValue = articlesViewModel.articlesState.value

        assert(stateValue.successDeletedArticle == articleId)
    }
}