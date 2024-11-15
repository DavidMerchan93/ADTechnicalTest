package com.davidmerchan.data.repository

import com.davidmerchan.core.network.NetworkValidator
import com.davidmerchan.data.datasource.ArticlesLocalDatasource
import com.davidmerchan.data.datasource.ArticlesRemoteDataSource
import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.repository.ArticleDatasourceRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

@Suppress("MaxLineLength")
class ArticleDatasourceTest {

    private val localDatasource: ArticlesLocalDatasource = mockk()
    private val remoteDataSource: ArticlesRemoteDataSource = mockk()
    private val networkValidator: NetworkValidator = mockk()
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var articleDatasource: ArticleDatasourceRepository
    
    @Before
    fun setUp() {
        articleDatasource = ArticleDatasource(
            localDatasource,
            remoteDataSource,
            networkValidator,
            testDispatcher
        )
    }

    @After
    fun tearDown() {
        confirmVerified(
            localDatasource,
            remoteDataSource,
            networkValidator
        )
    }
    
    @Test
    fun `getArticles() with a specific filter should return filtered articles when isConnected is true`() = runBlocking {
        // Given
        val article1: Article = mockk()
        val article2: Article = mockk()
        val article3: Article = mockk()
        val article4: Article = mockk()

        val articles1 = listOf(article1, article2)
        val articles2 = listOf(article3, article4)

        coEvery { networkValidator.isConnected() } returns true
        coEvery { remoteDataSource.getArticles() } returns articles1
        coEvery { localDatasource.saveArticles(articles1) } just Runs
        coEvery { localDatasource.getArticles() } returns articles2

        // When
        val result = articleDatasource.getArticles()

        // Then
        coVerify {
            networkValidator.isConnected()
            remoteDataSource.getArticles()
            localDatasource.saveArticles(articles1)
            localDatasource.getArticles()
        }
        assertEquals(result, Resource.Success(articles2))
    }

    @Test
    fun `getArticles() with a specific filter should return filtered articles when isConnected is false`() = runBlocking {
        // Given
        val article1: Article = mockk()
        val article2: Article = mockk()

        val articles1 = listOf(article1, article2)

        coEvery { networkValidator.isConnected() } returns false
        coEvery { localDatasource.getArticles() } returns articles1

        // When
        val result = articleDatasource.getArticles()

        // Then
        coVerify {
            networkValidator.isConnected()
            localDatasource.getArticles()
        }
        assertEquals(result, Resource.Success(articles1))
    }
    
    @Test
    fun `getArticles() should handle network error and return an error resource`() = runBlocking {
        // Given
        val networkException = IOException("Network error")

        coEvery { networkValidator.isConnected() } returns true
        coEvery { remoteDataSource.getArticles() } throws networkException

        // When
        val result = articleDatasource.getArticles()

        // Then
        coVerify {
            networkValidator.isConnected()
            remoteDataSource.getArticles()
        }
        assertEquals(result, Resource.Error(networkException))
    }
    
    @Test
    fun `getArticles() should handle local data source error and return an error resource`() = runBlocking {
        // Given
        val localException = IOException("Local data source error")

        coEvery { networkValidator.isConnected() } returns true
        coEvery { remoteDataSource.getArticles() } returns emptyList()
        coEvery { localDatasource.saveArticles(any()) } just Runs
        coEvery { localDatasource.getArticles() } throws localException

        // When
        val result = articleDatasource.getArticles()

        // Then
        coVerify {
            networkValidator.isConnected()
            remoteDataSource.getArticles()
            localDatasource.saveArticles(any())
            localDatasource.getArticles()
        }
        assertEquals(result, Resource.Error(localException))
    }
}
