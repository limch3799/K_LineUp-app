package com.lineup.app.ui.screens.stat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.lineup.app.ui.screens.match.components.ErrorView
import com.lineup.app.ui.screens.match.components.LoadingView
import com.lineup.app.ui.screens.stat.components.StatHeader
import com.lineup.app.ui.screens.stat.components.StatScreenContent
import com.lineup.app.ui.viewmodel.StatUiState
import com.lineup.app.ui.viewmodel.StatViewModel

@Composable
fun StatScreen(
    navController: NavController,
    viewModel: StatViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            StatHeader()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 90.dp)
            ) {
                when (val state = uiState) {
                    is StatUiState.Loading -> {
                        LoadingView()
                    }

                    is StatUiState.Success -> {
                        StatScreenContent(
                            stats = state.stats,
                            viewModel = viewModel
                        )
                    }

                    is StatUiState.Error -> {
                        ErrorView(
                            message = state.message,
                            onRetry = {}
                        )
                    }
                }
            }
        }
    }
}