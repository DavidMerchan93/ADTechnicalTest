package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.repository.ArticleDatasourceRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetArticlesUseCaseTest {

    private val articlesRepository: ArticleDatasourceRepository = mockk()
    private lateinit var getArticlesUseCase: GetArticlesUseCase

    @Before
    fun setUp() {
        getArticlesUseCase = GetArticlesUseCase(articlesRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(articlesRepository)
    }

    @Test
    fun `should return a success resource when the articles repository returns a success`(): Unit =
        runBlocking {
            // Given
            val article1: Article = mockk {
                every { id } returns 1L
                every { createdDate } returns "2024-11-12T21:40:00"
                every { isDeleted } returns false
            }
            val article2: Article = mockk {
                every { id } returns 2L
                every { createdDate } returns "2024-11-12T21:30:00"
                every { isDeleted } returns false
            }
            val article3: Article = mockk {
                every { id } returns 3L
                every { createdDate } returns "2024-11-12T21:20:00"
                every { isDeleted } returns false
            }

            val expectedArticles = listOf(article1, article2, article3)
            coEvery {
                articlesRepository.getArticles()
            } returns Resource.Success(expectedArticles)

            // When
            val result = getArticlesUseCase()

            // Then
            coVerify {
                articlesRepository.getArticles()
            }
            verify {
                article1.id
                article2.id
                article3.id

                article1.createdDate
                article2.createdDate
                article3.createdDate

                article1.isDeleted
                article2.isDeleted
                article3.isDeleted
            }
            confirmVerified(article1, article2, article3)

            assertEquals(result, Resource.Success(expectedArticles))
        }

    @Test
    fun `should return a success resource when the articles repository returns a success and deleted articles`(): Unit =
        runBlocking {
            // Given
            val article1: Article = mockk {
                every { id } returns 1L
                every { createdDate } returns "2024-11-12T21:40:00"
                every { isDeleted } returns false
            }
            val article2: Article = mockk {
                every { id } returns 2L
                every { createdDate } returns "2024-11-12T21:30:00"
                every { isDeleted } returns true
            }
            val article3: Article = mockk {
                every { id } returns 3L
                every { createdDate } returns "2024-11-12T21:20:00"
                every { isDeleted } returns false
            }

            val expectedArticles = listOf(article1, article3)
            coEvery {
                articlesRepository.getArticles()
            } returns Resource.Success(listOf(article1, article2, article3))

            // When
            val result = getArticlesUseCase()

            // Then
            coVerify {
                articlesRepository.getArticles()
            }
            verify {
                article1.id
                article3.id

                article1.createdDate
                article3.createdDate

                article1.isDeleted
                article2.isDeleted
                article3.isDeleted
            }
            confirmVerified(article1, article2, article3)

            assertEquals(result, Resource.Success(expectedArticles))
        }

    @Test
    fun `should handle network errors gracefully`() = runBlocking {
        // Given
        val expectedError = Resource.Error(Exception("Network error"))
        coEvery {
            articlesRepository.getArticles()
        } returns expectedError

        // When
        val result = getArticlesUseCase()

        // Then
        coVerify {
            articlesRepository.getArticles()
        }
        assertEquals(result, expectedError)
    }

    @Test
    fun `should return an error when the articles repository returns an error`() = runBlocking {
        val exception = Exception("Network error")

        // Given
        coEvery {
            articlesRepository.getArticles()
        } returns Resource.Error(exception)

        // When
        val result = getArticlesUseCase()

        // Then
        coVerify {
            articlesRepository.getArticles()
        }
        assertEquals(result, Resource.Error(exception))
    }
}