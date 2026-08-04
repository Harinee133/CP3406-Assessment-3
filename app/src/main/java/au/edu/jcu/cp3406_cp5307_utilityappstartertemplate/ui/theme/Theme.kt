package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.theme

import android.app.Activity
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

private val DarkColorScheme = darkColorScheme(
    primary = SkyBlue,
    secondary = GrassGreen,
    tertiary = SunnyYellow,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = SkyBlue,
    onPrimary = Color.White,
    primaryContainer = SkyBlue.copy(alpha = 0.2f),
    secondary = GrassGreen,
    onSecondary = Color.White,
    secondaryContainer = GrassGreen.copy(alpha = 0.2f),
    tertiary = BrightOrange,
    onTertiary = Color.White,
    tertiaryContainer = SunnyYellow.copy(alpha = 0.4f),
    background = Color(0xFFF0F9FF), // Very soft blue background
    surface = Color.White
)

@Composable
fun CP3406_CP5603UtilityAppStarterTemplateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Set to false by default for consistent educational branding
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
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
        content = content
    )
}