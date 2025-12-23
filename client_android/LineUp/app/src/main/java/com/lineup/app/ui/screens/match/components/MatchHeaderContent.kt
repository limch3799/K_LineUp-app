package com.lineup.app.ui.screens.match.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MatchHeaderContent(
    matchCount: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = 36.dp,
                end = 16.dp,
                bottom = 16.dp
            )
    ) {
        // 가운데 텍스트
        Text(
            text = "경기 일정",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF222222),
            modifier = Modifier.align(Alignment.Center)
        )

        // 오른쪽 텍스트
//        Text(
//            text = "${matchCount}경기",
//            fontSize = 16.sp,
//            color = Color(0xFF666666),
//            modifier = Modifier.align(Alignment.CenterEnd)
//        )
    }
}
