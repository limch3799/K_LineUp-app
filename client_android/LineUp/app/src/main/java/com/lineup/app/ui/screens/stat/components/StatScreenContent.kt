package com.lineup.app.ui.screens.stat.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lineup.app.data.model.Stat
import com.lineup.app.ui.theme.Background_list
import com.lineup.app.ui.viewmodel.StatViewModel

@Composable
fun StatScreenContent(
    stats: List<Stat>,
    viewModel: StatViewModel,
    modifier: Modifier = Modifier
) {
    if (stats.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "등록된 통계가 없습니다",
                fontSize = 16.sp,
                color = Background_list
            )
        }
    } else {
        StatScreenList(
            stats = stats,
            viewModel = viewModel
        )
    }
}