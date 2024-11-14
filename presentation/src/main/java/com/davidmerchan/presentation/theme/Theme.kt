package com.davidmerchan.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF84D6C2),
    onPrimary = Color(0xFF00382F),
    secondary = Color(0xFFB1CCC4),
    onSecondary = Color(0xFF1D352F),
    tertiary = Color(0xFFABCAE4),
    onTertiary = Color(0xFF113348),
    background = Color(0xFF0E1513),
    onBackground = Color(0xFFDEE4E0),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005)
)

private val LightColorScheme = darkColorScheme(
    primary = Color(0xFF056B5B),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF4A635D),
    onSecondary = Color(0xFFFFFFFF),
    tertiary = Color(0xFF436278),
    onTertiary = Color(0xFFFFFFFF),
    background = Color(0xFFF5FBF7),
    onBackground = Color(0xFF171D1B),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF)
)

@Composable
fun ADTechnicalTestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
