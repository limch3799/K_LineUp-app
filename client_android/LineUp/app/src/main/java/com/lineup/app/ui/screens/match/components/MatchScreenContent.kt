package com.lineup.app.ui.screens.match.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lineup.app.data.model.Match
import com.lineup.app.ui.theme.Background_list
import java.util.Calendar

@Composable
fun MatchScreenContent(
    matches: List<Match>,
    onRefresh: () -> Unit,
    onLineupToggle: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCompetition by remember { mutableStateOf("전체") }
    var selectedMonth by remember { mutableStateOf(Calendar.getInstance().get(Calendar.MONTH) + 1) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MatchHeader(
            selectedCompetition = selectedCompetition,
            selectedMonth = selectedMonth,
            onCompetitionSelected = { selectedCompetition = it },
            onMonthSelected = { selectedMonth = it }
        )

        val filteredMatches = filterMatches(matches, selectedCompetition, selectedMonth)

        if (filteredMatches.isEmpty()) {
            EmptyMatchView()
        } else {
            MatchScreenList(
                matches = filteredMatches,
                onLineupToggle = onLineupToggle
            )
        }
    }
}

@Composable
private fun EmptyMatchView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "등록된 매치가 없습니다",
            fontSize = 16.sp,
            color = Background_list
        )
    }
}

private fun filterMatches(
    matches: List<Match>,
    selectedCompetition: String,
    selectedMonth: Int
): List<Match> {
    var filtered = matches

    if (selectedCompetition != "전체") {
        filtered = filtered.filter { it.competition == selectedCompetition }
    }

    filtered = filtered.filter { match ->
        match.matchTime?.let { timestamp ->
            val calendar = Calendar.getInstance()
            calendar.time = timestamp.toDate()
            calendar.get(Calendar.MONTH) + 1 == selectedMonth
        } ?: false
    }

    return filtered
}