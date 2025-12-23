package com.lineup.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.lineup.app.R

// 기존 폰트 패밀리들
val font_paperlogy_1 = FontFamily(Font(R.font.font_paperlogy_1))
val font_paperlogy_2 = FontFamily(Font(R.font.font_paperlogy_2))
val font_paperlogy_3 = FontFamily(Font(R.font.font_paperlogy_3))
val font_paperlogy_4 = FontFamily(Font(R.font.font_paperlogy_4))
val font_paperlogy_5 = FontFamily(Font(R.font.font_paperlogy_5))
val font_paperlogy_6 = FontFamily(Font(R.font.font_paperlogy_6))
val font_paperlogy_7 = FontFamily(Font(R.font.font_paperlogy_7))
val font_paperlogy_8 = FontFamily(Font(R.font.font_paperlogy_8))
val font_paperlogy_9 = FontFamily(Font(R.font.font_paperlogy_9))

val font_gothic_1 = FontFamily(Font(R.font.font_gothic_1))
val font_gothic_2 = FontFamily(Font(R.font.font_gothic_2))
val font_gothic_3 = FontFamily(Font(R.font.font_gothic_3))
val font_gothic_4 = FontFamily(Font(R.font.font_gothic_4))
val font_gothic_5 = FontFamily(Font(R.font.font_gothic_5))

// 앱 전체 Typography - 페이퍼로지4를 기본 폰트로 설정
val AppTypography = Typography(
    // Display
    displayLarge = TextStyle(
        fontFamily = font_gothic_3,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = font_gothic_3,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = font_gothic_3,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),

    // Headline
    headlineLarge = TextStyle(
        fontFamily = font_gothic_5,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = font_gothic_3,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = font_gothic_3,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),

    // Title
    titleLarge = TextStyle(
        fontFamily = font_gothic_3,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = font_gothic_3,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = font_gothic_3,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),

    // Body
    bodyLarge = TextStyle(
        fontFamily = font_gothic_3,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = font_gothic_3,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = font_gothic_3,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),

    // Label
    labelLarge = TextStyle(
        fontFamily = font_gothic_3,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = font_gothic_3,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = font_gothic_3,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

// 기존 Typography는 남겨두거나 삭제해도 됨
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)