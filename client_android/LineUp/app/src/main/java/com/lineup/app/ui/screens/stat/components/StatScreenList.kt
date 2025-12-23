package com.lineup.app.ui.screens.stat.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lineup.app.data.model.Stat
import com.lineup.app.ui.viewmodel.StatViewModel

@Composable
fun StatScreenList(
    stats: List<Stat>,
    viewModel: StatViewModel,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    // 리그별로 그룹화 및 정렬
    val groupedStats = remember(stats) {
        groupStatsByLeague(stats)
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // 리그 순서: 프리미어리그 -> 분데스리가 -> 리그 1
        val leagueOrder = listOf("프리미어리그", "분데스리가", "리그 1")

        leagueOrder.forEach { league ->
            groupedStats[league]?.let { leagueStats ->
                items(
                    leagueStats,
                    key = { it.documentId }
                ) { stat ->
                    PlayerStatGroup(
                        stat = stat,
                        isExpanded = viewModel.isPlayerExpanded(stat.name),
                        onToggleExpanded = {
                            viewModel.togglePlayerExpanded(stat.name)
                        }
                    )
                }
            }
        }
    }
}

private fun groupStatsByLeague(stats: List<Stat>): Map<String, List<Stat>> {
    return stats
        .groupBy { it.league }
        .mapValues { (_, leagueStats) ->
            // 같은 리그 내에서는 documentId로 정렬
            leagueStats.sortedBy { it.documentId }
        }
}