package com.knowroaming.esim.app.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

object BrandColor {
    val Black = Color(0xFF000000)
    val White = Color(0xFFFFFFFF)

    val GrayMuted = Color(0xCC383838)
    val PrimaryStart = Color(0xFFAD30AD)
    val PrimaryEnd = Color(0xFF8952B2)
    val NavigationStart = Color(0xFF5B7DB8)
    val NavigationEnd = Color(0xFF379FBE)
    val BackgroundEnd = Color(0xFF22464D)
    val BackgroundStart = Color(0xFF4A2048)


    val Gray25 = Color(0xFFEEEEEE)
    val Gray50 = Color(0xFFEBEBEB)
    val Gray100 = Color(0xFFC1C1C1)
    val Gray200 = Color(0xFFA3A3A3)
    val Gray300 = Color(0xFF7A7A7A)
    val Gray400 = Color(0xFF606060)
    val Gray500 = Color(0xFF383838)
    val Gray600 = Color(0xFF333333)
    val Gray700 = Color(0xFF282828)
    val Gray800 = Color(0xFF1F1F1F)
    val Gray900 = Color(0xFF181818)

    val Blue50 = Color(0xFFEAF6F9)
    val Blue100 = Color(0xFFBFE4EB)
    val Blue200 = Color(0xFFA0D7E2)
    val Blue300 = Color(0xFF74C4D4)
    val Blue400 = Color(0xFF59B9CC)
    val Blue500 = Color(0xFF30A7BF)
    val Blue600 = Color(0xFF2C98AE)
    val Blue700 = Color(0xFF227788)
    val Blue800 = Color(0xFF1A5C69)
    val Blue900 = Color(0xFF144650)

    val Violet50 = Color(0xFFF8EAF7)
    val Violet100 = Color(0xFFE8BDE5)
    val Violet200 = Color(0xFFDD9DD9)
    val Violet300 = Color(0xFFCD70C7)
    val Violet400 = Color(0xFFC454BD)
    val Violet500 = Color(0xFFB529AC)
    val Violet600 = Color(0xFFA5259D)
    val Violet700 = Color(0xFF811D7A)
    val Violet800 = Color(0xFF64175F)
    val Violet900 = Color(0xFF4C1148)
}


object BrandGradientOffset {
    val Primary = Offset(
        x = cos(89f * (PI / 180f)).toFloat(), y = sin(89f * (PI / 180f)).toFloat()
    )
}

object BrandGradient {
    val Primary = Brush.linearGradient(
        colors = listOf(
            BrandColor.PrimaryStart,
            BrandColor.PrimaryEnd,
        ), start = BrandGradientOffset.Primary, end = Offset.Infinite
    )

    val Navigation = Brush.linearGradient(
        colors = listOf(
            BrandColor.NavigationStart,
            BrandColor.NavigationEnd,
        ), start = BrandGradientOffset.Primary, end = Offset.Infinite
    )

    val Background = Brush.linearGradient(
        colors = listOf(
            BrandColor.BackgroundStart,
            BrandColor.BackgroundEnd,
        ), start = BrandGradientOffset.Primary, end = Offset.Infinite
    )
}

val LightColorScheme = lightColorScheme(
    primary = BrandColor.Blue400,
    secondary = BrandColor.Violet400,
    tertiary = BrandColor.Gray25,
    surface = BrandColor.Gray400,
    error = BrandColor.Violet600,
    background = BrandColor.Gray800,
    primaryContainer = BrandColor.Blue100,
    secondaryContainer = BrandColor.Violet100,
    tertiaryContainer = BrandColor.Violet50,
    onPrimary = BrandColor.White,
    onSecondary = BrandColor.White,
    onTertiary = BrandColor.White,
    onSurface = BrandColor.Gray50,
    onBackground = BrandColor.Gray50,
    onPrimaryContainer = BrandColor.Black,
    onSecondaryContainer = BrandColor.Black,
    onTertiaryContainer = BrandColor.Black,
)

val DarkColorScheme = darkColorScheme(
    primary = BrandColor.Blue500,
    secondary = BrandColor.Violet500,
    tertiary = BrandColor.Gray25,
    surface = BrandColor.Gray500,
    background = BrandColor.Gray900,
    error = BrandColor.Violet700,
    primaryContainer = BrandColor.Blue100,
    secondaryContainer = BrandColor.Violet100,
    tertiaryContainer = BrandColor.Violet50,
    onPrimary = BrandColor.White,
    onSecondary = BrandColor.White,
    onTertiary = BrandColor.White,
    onSurface = BrandColor.Gray50,
    onBackground = BrandColor.Gray50,
    onPrimaryContainer = BrandColor.Black,
    onSecondaryContainer = BrandColor.Black,
    onTertiaryContainer = BrandColor.Black,
)