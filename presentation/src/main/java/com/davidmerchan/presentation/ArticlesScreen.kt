package com.davidmerchan.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.davidmerchan.core.network.NetworkConnectionState
import com.davidmerchan.core.network.rememberConnectivityState
import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.presentation.theme.ADTechnicalTestTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesScreen(
    modifier: Modifier = Modifier,
    articlesViewModel: ArticlesViewModel = hiltViewModel()
) {
    val uiState by articlesViewModel.articlesState.collectAsState()
    val connectionState by rememberConnectivityState()

    val isConnected by remember(connectionState) {
        derivedStateOf { connectionState === NetworkConnectionState.Available }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Articulos")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isConnected.not()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "No tiene conexion a la red"
                )
            } else {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "Conectado a la red"
                )
            }

            when {
                uiState.isLoading -> LoadingScreen()
                uiState.articles.isNotEmpty() -> {
                    ArticlesContent(articles = uiState.articles)
                }

                uiState.error != null -> {
                    ErrorScreen()
                }

                else -> {
                    ErrorScreen(error = stringResource(R.string.empty_articles_error))
                }
            }
        }
    }
}

@Composable
fun ArticlesContent(modifier: Modifier = Modifier, articles: List<Article>) {
    LazyColumn(
        modifier = modifier
    ) {
        items(articles) { article ->
            ArticleItem(
                article = article,
                onArticleClick = {},
                onDeleteArticle = {}
            )
        }
    }
}

@Composable
fun ArticleItem(
    modifier: Modifier = Modifier,
    article: Article,
    onArticleClick: (Article) -> Unit,
    onDeleteArticle: (id: Long) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = article.title, softWrap = true, maxLines = 2)
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(text = article.author)
            Text("-")
            Text(text = article.createdDate)
        }
        HorizontalDivider()
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.title_loading_articles))
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, error: String? = null) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(error ?: stringResource(R.string.general_error))
    }
}

@Preview
@Composable
internal fun ArticlesScreenPreview() {
    ADTechnicalTestTheme {
        ArticleItem(
            article = Article(
                1,
                "Title",
                "Content",
                "Author",
                "2024-10-12",
                "http://google.com"
            ),
            onArticleClick = {},
            onDeleteArticle = {}
        )
    }
}
