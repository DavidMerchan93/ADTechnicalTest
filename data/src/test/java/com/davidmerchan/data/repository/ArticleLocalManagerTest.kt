package com.davidmerchan.data.repository

import com.davidmerchan.data.datasource.ArticlesLocalDatasource
import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.repository.ArticleLocalManagerRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ArticleLocalManagerTest {

    private val articlesLocalDatasource: ArticlesLocalDatasource = mockk()
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var articleLocalManager: ArticleLocalManagerRepository

    @Before
    fun setUp() {
        articleLocalManager = ArticleLocalManager(articlesLocalDatasource, testDispatcher)
    }

    @After
    fun tearDown() {
        confirmVerified(articlesLocalDatasource)
    }

    @Test
    fun `saveArticles() should save articles correctly`() = runBlocking {
        // Given
        val largeNumberOfArticles = List(10) {
            mockk<Article>()
        }
        every {
            articlesLocalDatasource.saveArticles(largeNumberOfArticles)
        } just runs

        // When
        val result = articleLocalManager.saveArticles(largeNumberOfArticles)

        // Then
        verify {
            articlesLocalDatasource.saveArticles(largeNumberOfArticles)
        }
        assertEquals(result, Resource.Success(Unit))
    }

    @Test
    fun `saveArticles() should not save articles with error`() = runBlocking {
        // Given
        val largeNumberOfArticles = List(10) {
            mockk<Article>()
        }
        val exception = Exception("General Error")
        every {
            articlesLocalDatasource.saveArticles(largeNumberOfArticles)
        } throws exception

        // When
        val result = articleLocalManager.saveArticles(largeNumberOfArticles)

        // Then
        verify {
            articlesLocalDatasource.saveArticles(largeNumberOfArticles)
        }
        assertEquals(result, Resource.Error(exception))
    }

    @Test
    fun `deleteArticle() should delete one article correctly`() = runBlocking {
        // Given
        val articleId = 101L
        every {
            articlesLocalDatasource.deleteArticle(articleId)
        } just runs

        // When
        val result = articleLocalManager.deleteArticle(articleId)

        // Then
        verify {
            articlesLocalDatasource.deleteArticle(articleId)
        }
        assertEquals(result, Resource.Success(Unit))
    }

    @Test
    fun `deleteArticle() should not delete one article with error`() = runBlocking {
        // Given
        val articleId = 101L
        val exception = Exception("General Error")
        every {
            articlesLocalDatasource.deleteArticle(articleId)
        } throws exception

        // When
        val result = articleLocalManager.deleteArticle(articleId)

        // Then
        verify {
            articlesLocalDatasource.deleteArticle(articleId)
        }
        assertEquals(result, Resource.Error(exception))
    }

    @Test
    fun `restoreArticle() should restore one article correctly`() = runBlocking {
        // Given
        val articleId = 101L
        val article: Article = mockk()

        every {
            articlesLocalDatasource.restoreArticle(articleId)
        } returns article

        // When
        val result = articleLocalManager.restoreArticle(articleId)

        // Then
        verify {
            articlesLocalDatasource.restoreArticle(articleId)
        }
        confirmVerified(article)
        assertEquals(result, Resource.Success(article))
    }

    @Test
    fun `restoreArticle() should not restore one article with error`() = runBlocking {
        // Given
        val articleId = 101L
        val exception = Exception("General Error")
        every {
            articlesLocalDatasource.restoreArticle(articleId)
        } throws exception

        // When
        val result = articleLocalManager.restoreArticle(articleId)

        // Then
        verify {
            articlesLocalDatasource.restoreArticle(articleId)
        }
        assertEquals(result, Resource.Error(exception))
    }

    @Test
    fun `restoreAllArticles() should restore all articles correctly`() = runBlocking {
        // Given
        val articleId = 101L
        val largeNumberOfArticles = List(10) {
            mockk<Article>()
        }

        every {
            articlesLocalDatasource.restoreAllArticles()
        } returns largeNumberOfArticles

        // When
        val result = articleLocalManager.restoreAllArticles()

        // Then
        verify {
            articlesLocalDatasource.restoreAllArticles()
        }
        assertEquals(result, Resource.Success(largeNumberOfArticles))
    }

    @Test
    fun `restoreArticle() should not restore any article with error`() = runBlocking {
        // Given
        val articleId = 101L
        val exception = Exception("General Error")
        every {
            articlesLocalDatasource.restoreAllArticles()
        } throws exception

        // When
        val result = articleLocalManager.restoreAllArticles()

        // Then
        verify {
            articlesLocalDatasource.restoreAllArticles()
        }
        assertEquals(result, Resource.Error(exception))
    }
}
