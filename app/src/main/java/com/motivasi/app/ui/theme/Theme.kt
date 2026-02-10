package com.motivasi.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Premium warm color palette
val PrimaryGold = Color(0xFFE8A838)
val PrimaryGoldDark = Color(0xFFD4922A)
val SecondaryAmber = Color(0xFFF5C16C)
val TertiaryWarm = Color(0xFFFF8F6B)

val SurfaceDark = Color(0xFF1A1520)
val SurfaceVariantDark = Color(0xFF2A2230)
val BackgroundDark = Color(0xFF12101A)
val CardDark = Color(0xFF221E2A)

val TextPrimary = Color(0xFFF5F0EA)
val TextSecondary = Color(0xFFB8A8C8)
val TextGold = Color(0xFFE8A838)

val GradientStart = Color(0xFF2D1B4E)
val GradientMiddle = Color(0xFF1A1035)
val GradientEnd = Color(0xFF0D0A18)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryGold,
    onPrimary = Color.Black,
    primaryContainer = PrimaryGoldDark,
    onPrimaryContainer = Color.White,
    secondary = SecondaryAmber,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF3A2A15),
    onSecondaryContainer = SecondaryAmber,
    tertiary = TertiaryWarm,
    onTertiary = Color.Black,
    background = BackgroundDark,
    onBackground = TextPrimary,
    surface = SurfaceDark,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondary,
    outline = Color(0xFF4A3A5A),
    outlineVariant = Color(0xFF3A2A4A),
)

@Composable
fun MotivasiTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = false
                isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
