package com.lineup.app.ui.screens.mypick

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
import com.lineup.app.ui.screens.mypick.components.MyPickScreenContent
import com.lineup.app.ui.viewmodel.MyPickUiState
import com.lineup.app.ui.viewmodel.MyPickViewModel

@Composable
fun MyPickScreen(
    navController: NavController,
    viewModel: MyPickViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(bottom = 90.dp)
    ) {
        when (val state = uiState) {
            is MyPickUiState.Loading -> {
                LoadingView()
            }

            is MyPickUiState.Success -> {
                MyPickScreenContent(
                    matches = state.matches,
                    onRefresh = { viewModel.refresh() },
                    onLineupToggle = { documentId, isEnabled ->
                        if (!isEnabled) {
                            viewModel.removeLineupNotification(documentId)
                        }
                    }
                )
            }

            is MyPickUiState.Error -> {
                ErrorView(
                    message = state.message,
                    onRetry = { viewModel.refresh() }
                )
            }
        }
    }
}