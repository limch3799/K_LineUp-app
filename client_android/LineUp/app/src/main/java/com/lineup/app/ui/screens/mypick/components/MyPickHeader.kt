package com.lineup.app.ui.screens.mypick.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun MyPickHeader(
    selectedMonth: Int,
    onMonthSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .zIndex(1f)
            .shadow(
                elevation = 4.dp,
                clip = false
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
                    text = "마이 픽",
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF222222),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // 대회 필터 (MyPick 고정)
            MyPickCompetitionFilter()

            Spacer(modifier = Modifier.height(12.dp))

            // 월 필터
            MyPickMonthFilter(
                selectedMonth = selectedMonth,
                onMonthSelected = onMonthSelected
            )

            Spacer(modifier = Modifier.height(1.dp))
        }
    }
}