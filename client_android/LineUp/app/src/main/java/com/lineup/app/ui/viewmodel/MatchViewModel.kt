package com.lineup.app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lineup.app.data.model.Match
import com.lineup.app.data.repository.MatchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class MatchUiState {
    object Loading : MatchUiState()
    data class Success(val matches: List<Match>) : MatchUiState()
    data class Error(val message: String) : MatchUiState()
}

class MatchViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MatchRepository.getInstance(application.applicationContext)

    private val _uiState = MutableStateFlow<MatchUiState>(MatchUiState.Loading)
    val uiState: StateFlow<MatchUiState> = _uiState.asStateFlow()

    private val _expandedPlayers = MutableStateFlow<Set<String>>(emptySet())
    val expandedPlayers: StateFlow<Set<String>> = _expandedPlayers.asStateFlow()

    init {
        // Firebase 리스너 시작 (한 번만)
        repository.startMatchesListener()

        // 캐시 데이터 구독
        viewModelScope.launch {
            repository.cachedMatches.collect { matches ->
                if (matches.isNotEmpty()) {
                    _uiState.value = MatchUiState.Success(matches)
                } else {
                    _uiState.value = MatchUiState.Loading
                }
            }
        }
    }

    fun toggleLineupNotification(documentId: String, isEnabled: Boolean) {
        repository.toggleLineupNotification(documentId, isEnabled)
    }

    fun refresh() {
        viewModelScope.launch {
            repository.refreshMatches()
                .onFailure { error ->
                    _uiState.value = MatchUiState.Error(
                        error.message ?: "매치 목록을 불러오는데 실패했습니다."
                    )
                }
        }
    }

    fun togglePlayerExpanded(playerId: String) {
        _expandedPlayers.value = if (_expandedPlayers.value.contains(playerId)) {
            _expandedPlayers.value - playerId
        } else {
            _expandedPlayers.value + playerId
        }
    }

    fun expandAllPlayers(playerIds: List<String>) {
        _expandedPlayers.value = playerIds.toSet()
    }

    fun isPlayerExpanded(playerId: String): Boolean {
        return _expandedPlayers.value.contains(playerId)
    }
}