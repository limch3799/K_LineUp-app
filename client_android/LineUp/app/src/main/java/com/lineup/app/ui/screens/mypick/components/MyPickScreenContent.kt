package com.lineup.app.ui.screens.mypick.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lineup.app.data.model.Match
import com.lineup.app.ui.screens.match.components.MatchScreenList
import com.lineup.app.ui.theme.Background_list
import java.util.Calendar

@Composable
fun MyPickScreenContent(
    matches: List<Match>,
    onRefresh: () -> Unit,
    onLineupToggle: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedMonth by remember { mutableStateOf(Calendar.getInstance().get(Calendar.MONTH) + 1) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MyPickHeader(
            selectedMonth = selectedMonth,
            onMonthSelected = { selectedMonth = it }
        )

        val filteredMatches = filterMyPickMatches(matches, selectedMonth)

        if (filteredMatches.isEmpty()) {
            EmptyMyPickView()
        } else {
            MatchScreenList(
                matches = filteredMatches,
                onLineupToggle = onLineupToggle
            )
        }
    }
}

@Composable
private fun EmptyMyPickView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "픽한 경기가 없습니다",
            fontSize = 16.sp,
            color = Background_list
        )
    }
}

private fun filterMyPickMatches(
    matches: List<Match>,
    selectedMonth: Int
): List<Match> {
    return matches.filter { match ->
        match.matchTime?.let { timestamp ->
            val calendar = Calendar.getInstance()
            calendar.time = timestamp.toDate()
            calendar.get(Calendar.MONTH) + 1 == selectedMonth
        } ?: false
    }
}