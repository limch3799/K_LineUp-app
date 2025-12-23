package com.lineup.app.ui.screens.match.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun MatchHeader(
    selectedCompetition: String,
    selectedMonth: Int,
    onCompetitionSelected: (String) -> Unit,
    onMonthSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .zIndex(1f) // 리스트보다 위에 그려지도록 강제 설정
            .shadow(
                elevation = 4.dp, // 그림자 높이
                clip = false      // 그림자가 잘리지 않게
            ),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 제목
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        top = 36.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
            ) {
                Text(
                    text = "경기 일정",
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF222222),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // 대회 필터
            CompetitionFilterContent(
                selectedCompetition = selectedCompetition,
                onCompetitionSelected = onCompetitionSelected
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 월 필터
            MonthFilterContent(
                selectedMonth = selectedMonth,
                onMonthSelected = onMonthSelected
            )

            Spacer(modifier = Modifier.height(1.dp))
        }
    }
}