package com.lineup.app.ui.screens.stat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lineup.app.data.model.Stat

@Composable
fun StatItem(
    stat: Stat,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 8.dp)
    ) {
        // 리그 배지 (왼쪽에 더 붙임)
        LeagueBadge(
            league = stat.league,
            modifier = Modifier.padding(start = 8.dp)
        )

        Spacer(modifier = Modifier.height(0.dp))

        // 통계 그리드 (기존 패딩 유지)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            StatBox(
                label = "경기수",
                value = stat.matches.toString(),
                modifier = Modifier.weight(1f),
                isWide = false
            )
            StatBox(
                label = "득점",
                value = stat.goals.toString(),
                modifier = Modifier.weight(1f),
                isWide = false
            )
            StatBox(
                label = "도움",
                value = stat.assists.toString(),
                modifier = Modifier.weight(1f),
                isWide = false
            )
            StatBox(
                label = "파울",
                value = stat.fouls.toString(),
                modifier = Modifier.weight(1f),
                isWide = false
            )
            StatBox(
                label = "유효슈팅",
                value = stat.shotsOnTarget.toString(),
                modifier = Modifier.weight(1f),
                isWide = false
            )
            StatBox(
                label = "출전시간",
                value = "${stat.playTime}분",
                modifier = Modifier.weight(1.4f),
                isWide = true
            )
        }
    }
}

@Composable
private fun LeagueBadge(
    league: String,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (league) {
        "프리미어리그" -> Color(0xFF3D195B)
        "분데스리가" -> Color(0xFFD3010D)
        "리그 1" -> Color(0xFF091C3E)
        else -> Color(0xFF666666)
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor,
        modifier = modifier
    ) {
        Text(
            text = league,
            fontSize = 12.sp,
            lineHeight = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

@Composable
private fun StatBox(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    isWide: Boolean
) {
    Column(
        modifier = modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(
                vertical = 8.dp,
                horizontal = if (isWide) 6.dp else 2.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            color = Color(0xFF888888),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF222222),
            maxLines = 1
        )
    }
}
