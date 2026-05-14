package com.example.learnloop.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LearnLoopColorScheme = lightColorScheme(
    primary = Primary,
    secondary = Accent,
    tertiary = Gold,
    background = Background,
    surface = Surface,
    error = ErrorColor,
    onPrimary = Surface,
    onSecondary = Surface,
    onTertiary = TextPrimary,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onError = Surface,
)

@Composable
fun LearnLoopTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LearnLoopColorScheme,
        typography = Typography,
        content = content
    )
}