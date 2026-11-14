package com.edusutra.app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = PrimaryBlueSoft,
    onPrimaryContainer = PrimaryBlueDark,

    secondary = Navy700,
    onSecondary = Color.White,
    secondaryContainer = SlateBorderSubtle,
    onSecondaryContainer = Navy900,

    tertiary = SuccessGreen,
    onTertiary = Color.White,
    tertiaryContainer = SuccessGreenSoft,
    onTertiaryContainer = SuccessGreen,

    background = SlateBackground,
    onBackground = Navy900,

    surface = SlateSurface,
    onSurface = Navy900,
    surfaceVariant = SlateBackground,
    onSurfaceVariant = SlateMuted,

    outline = SlateBorder,
    outlineVariant = SlateBorderSubtle,

    error = ErrorRed,
    onError = Color.White,
    errorContainer = ErrorRedSoft,
    onErrorContainer = ErrorRed
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    secondary = PurpleGrey80,
    tertiary = SuccessGreen,
    background = Navy900,
    surface = Navy800,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun EduSutraTheme(
    darkTheme: Boolean = false, // EduSutra wireframe design uses crisp light palette
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
