package com.davidmerchan.presentation.screen.articles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.davidmerchan.core.network.NetworkConnectionState
import com.davidmerchan.core.network.rememberConnectivityState
import com.davidmerchan.core.ui.ConnectionMessage
import com.davidmerchan.core.ui.ErrorScreen
import com.davidmerchan.core.ui.LoadingScreen
import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.presentation.R
import com.davidmerchan.presentation.screen.articles.events.ArticlesUiEvent
import com.davidmerchan.presentation.theme.ADTechnicalTestTheme
import com.davidmerchan.presentation.viewModel.ArticlesViewModel

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
                    Text(stringResource(R.string.articles_title_screen))
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->

        PullToRefreshBox(
            modifier = Modifier.padding(innerPadding),
            isRefreshing = uiState.isRefreshing,
            onRefresh = {
                articlesViewModel.handleArticleEvent(ArticlesUiEvent.LoadArticles)
            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isConnected.not()) {
                    ConnectionMessage()
                }

                when {
                    uiState.isLoading ->
                        LoadingScreen(message = stringResource(R.string.title_loading_articles))

                    uiState.articles.isNotEmpty() -> {
                        ArticlesContent(articles = uiState.articles)
                    }

                    uiState.error != null -> ErrorScreen()

                    else -> ErrorScreen(error = stringResource(R.string.empty_articles_error))
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
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = article.title,
                softWrap = true,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = article.author,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 16.sp
                )
                Text(
                    text = article.createdDate,
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 14.sp
                )
            }
        }
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
