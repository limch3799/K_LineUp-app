package com.lineup.app.ui.screens.match.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lineup.app.data.model.Match

@Composable
fun MatchScreenList(
    matches: List<Match>,
    onLineupToggle: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    val matchesByPlayerGroup = remember(matches) {
        groupMatchesByPlayerGroup(matches)
    }

    val localExpandedGroups = remember(matchesByPlayerGroup) {
        mutableStateOf(matchesByPlayerGroup.keys.toSet())
    }

    // LaunchedEffect 제거 - 스크롤 위치 유지

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            matchesByPlayerGroup.keys.toList().sortedBy { groupKey ->
                groupKey.split(",").firstOrNull()?.toIntOrNull() ?: Int.MAX_VALUE
            },
            key = { it }
        ) { groupKey ->
            val groupMatches = matchesByPlayerGroup[groupKey] ?: emptyList()
            val playerIds = groupKey.split(",")

            if (groupMatches.isNotEmpty()) {
                PlayerMatchGroup(
                    playerIds = playerIds,
                    matches = groupMatches,
                    isExpanded = localExpandedGroups.value.contains(groupKey),
                    onToggleExpanded = {
                        localExpandedGroups.value = if (localExpandedGroups.value.contains(groupKey)) {
                            localExpandedGroups.value - groupKey
                        } else {
                            localExpandedGroups.value + groupKey
                        }
                    },
                    onLineupToggle = onLineupToggle
                )
            }
        }
    }
}

private fun groupMatchesByPlayerGroup(matches: List<Match>): Map<String, List<Match>> {
    val grouped = mutableMapOf<String, MutableList<Match>>()

    matches.forEach { match ->
        if (match.koreanPlayerIds.isNotEmpty()) {
            val groupKey = match.koreanPlayerIds.sorted().joinToString(",")

            if (!grouped.containsKey(groupKey)) {
                grouped[groupKey] = mutableListOf()
            }
            grouped[groupKey]?.add(match)
        }
    }

    return grouped.mapValues { entry ->
        entry.value.sortedBy { it.matchTime?.toDate()?.time ?: Long.MAX_VALUE }
    }
}