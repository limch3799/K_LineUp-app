package com.lineup.app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lineup.app.data.model.Stat
import com.lineup.app.data.repository.StatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class StatUiState {
    object Loading : StatUiState()
    data class Success(val stats: List<Stat>) : StatUiState()
    data class Error(val message: String) : StatUiState()
}

class StatViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = StatRepository.getInstance(application.applicationContext)

    private val _uiState = MutableStateFlow<StatUiState>(StatUiState.Loading)
    val uiState: StateFlow<StatUiState> = _uiState.asStateFlow()

    private val _expandedPlayers = MutableStateFlow<Set<String>>(emptySet())
    val expandedPlayers: StateFlow<Set<String>> = _expandedPlayers.asStateFlow()

    init {
        loadStats()
    }

    private fun loadStats() {
        viewModelScope.launch {
            _uiState.value = StatUiState.Loading

            repository.loadStats()
                .onSuccess { stats ->
                    // 모든 선수를 펼친 상태로 초기화
                    _expandedPlayers.value = stats.map { it.name }.toSet()
                    _uiState.value = StatUiState.Success(stats)
                }
                .onFailure { error ->
                    _uiState.value = StatUiState.Error(
                        error.message ?: "통계를 불러오는데 실패했습니다."
                    )
                }
        }
    }

    fun togglePlayerExpanded(playerName: String) {
        _expandedPlayers.value = if (_expandedPlayers.value.contains(playerName)) {
            _expandedPlayers.value - playerName
        } else {
            _expandedPlayers.value + playerName
        }
    }

    fun isPlayerExpanded(playerName: String): Boolean {
        return _expandedPlayers.value.contains(playerName)
    }
}