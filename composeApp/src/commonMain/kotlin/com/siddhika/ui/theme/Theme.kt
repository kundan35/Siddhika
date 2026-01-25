package com.siddhika.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = SaffronPrimary,
    onPrimary = TextOnPrimary,
    primaryContainer = SaffronLight,
    onPrimaryContainer = DeepMaroon,

    secondary = SageGreen,
    onSecondary = TextOnPrimary,
    secondaryContainer = SageGreenLight,
    onSecondaryContainer = SageGreenDark,

    tertiary = GoldAccent,
    onTertiary = DeepMaroon,
    tertiaryContainer = GoldLight,
    onTertiaryContainer = GoldDark,

    background = CreamBackground,
    onBackground = TextPrimary,

    surface = SurfaceLight,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = TextSecondary,

    error = ErrorRed,
    onError = TextOnPrimary,

    outline = TextTertiary,
    outlineVariant = CreamDark
)

private val DarkColorScheme = darkColorScheme(
    primary = SaffronLight,
    onPrimary = DeepMaroon,
    primaryContainer = SaffronDark,
    onPrimaryContainer = CreamLight,

    secondary = SageGreenLight,
    onSecondary = SageGreenDark,
    secondaryContainer = SageGreen,
    onSecondaryContainer = CreamLight,

    tertiary = GoldLight,
    onTertiary = DeepMaroon,
    tertiaryContainer = GoldDark,
    onTertiaryContainer = GoldLight,

    background = SurfaceDark,
    onBackground = TextOnDark,

    surface = MidnightBlueDark,
    onSurface = TextOnDark,
    surfaceVariant = MidnightBlue,
    onSurfaceVariant = TextTertiary,

    error = ErrorRed,
    onError = TextOnPrimary,

    outline = TextTertiary,
    outlineVariant = MidnightBlueLight
)

@Composable
fun SiddhikaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SiddhikaTypography,
        content = content
    )
}
