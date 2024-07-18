package com.example.final_finalapp.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

data class ChessBoardColors(
    val sqDark: Color,
    val sqLight: Color,
)

@Composable
fun provideChessBoardColors(): ChessBoardColors {
    val isDarkTheme = isSystemInDarkTheme()
    return if (isDarkTheme) {
        ChessBoardColors(sqDark = DarkColors.tertiaryContainer, sqLight = DarkColors.tertiary)
    } else {
        ChessBoardColors(sqDark = LightColors.tertiaryContainer, sqLight = LightColors.tertiary)
    }
}
