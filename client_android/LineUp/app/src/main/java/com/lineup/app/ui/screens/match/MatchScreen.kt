package com.lineup.app.ui.screens.match

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.lineup.app.ui.screens.match.components.*
import com.lineup.app.ui.viewmodel.MatchUiState
import com.lineup.app.ui.viewmodel.MatchViewModel

@Composable
fun MatchScreen(
    navController: NavController,
    viewModel: MatchViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(bottom = 90.dp)
    ) {
        when (val state = uiState) {
            is MatchUiState.Loading -> {
                LoadingView()
            }

            is MatchUiState.Success -> {
                MatchScreenContent(
                    matches = state.matches,
                    onRefresh = { viewModel.refresh() },
                    onLineupToggle = { documentId, isEnabled ->
                        viewModel.toggleLineupNotification(documentId, isEnabled)
                    }
                )
            }

            is MatchUiState.Error -> {
                ErrorView(
                    message = state.message,
                    onRetry = { viewModel.refresh() }
                )
            }
        }
    }
}