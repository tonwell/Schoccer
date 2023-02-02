package io.well.schoccer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = darkColors(
    primary = Green500,
    onPrimary = Yellow500,
    surface = LightBlue200,
    onSurface = DarkBlue800,
    background = Yellow100,
    onBackground = DarkBlue900
)

private val DarkColorPalette = lightColors(
    primary = DarkGreen900,
    onPrimary = Yellow700,
    surface = DarkerBlue900,
    onSurface = LightYellow100,
    background = Blue900,
    onBackground = Yellow600
)

@Composable
fun SchoccerTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}