package com.davidmerchan.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.presentation.theme.ADTechnicalTestTheme

@Composable
fun ArticlesScreen(
    modifier: Modifier = Modifier,
    articlesViewModel: ArticlesViewModel = hiltViewModel()
) {

    val uiState by articlesViewModel.articlesState.collectAsState()

    Scaffold(
        modifier = modifier
    ) { innerPadding ->

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

@Composable
fun ArticlesContent(modifier: Modifier = Modifier, articles: List<Article>) {
    LazyColumn {
        items(articles) { article ->
            Column(
                modifier = modifier
                    .padding(vertical = 16.dp)
                    .background(Color.Red)
            ) {
                Text(text = article.title)
                HorizontalDivider()
                Text(text = article.content)
                HorizontalDivider()
                Text(text = article.author)
            }
        }
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

@androidx.compose.ui.tooling.preview.Preview
@Composable
private fun ArticlesScreenPreview() {
    ADTechnicalTestTheme {
        ArticlesScreen()
    }
}
