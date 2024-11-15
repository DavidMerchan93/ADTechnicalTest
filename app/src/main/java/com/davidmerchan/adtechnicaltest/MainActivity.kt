package com.davidmerchan.adtechnicaltest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.davidmerchan.presentation.screen.articles.ArticlesListScreen
import com.davidmerchan.presentation.screen.detail.ArticleDetailScreen
import com.davidmerchan.presentation.theme.ADTechnicalTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ADTechnicalTestTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screens.ArticleList
                ) {
                    composable<Screens.ArticleList> {
                        ArticlesListScreen(
                            onGoToDetail = { id, url ->
                                navController.navigate(Screens.ArticleDetail(id, url))
                            }
                        )
                    }
                    composable<Screens.ArticleDetail> {
                        val arg = it.toRoute<Screens.ArticleDetail>()
                        ArticleDetailScreen(
                            url = arg.url,
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
