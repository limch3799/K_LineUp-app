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

sealed class MyPickUiState {
    object Loading : MyPickUiState()
    data class Success(val matches: List<Match>) : MyPickUiState()
    data class Error(val message: String) : MyPickUiState()
}

class MyPickViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MatchRepository.getInstance(application.applicationContext)

    private val _uiState = MutableStateFlow<MyPickUiState>(MyPickUiState.Loading)
    val uiState: StateFlow<MyPickUiState> = _uiState.asStateFlow()

    private val _expandedPlayers = MutableStateFlow<Set<String>>(emptySet())
    val expandedPlayers: StateFlow<Set<String>> = _expandedPlayers.asStateFlow()

    init {
        // 캐시 데이터 구독해서 MyPick만 필터링
        viewModelScope.launch {
            repository.cachedMatches.collect { allMatches ->
                val myPickMatches = repository.getMyPickMatches()
                if (myPickMatches.isNotEmpty()) {
                    _uiState.value = MyPickUiState.Success(myPickMatches)
                } else {
                    _uiState.value = MyPickUiState.Success(emptyList())
                }
            }
        }
    }

    fun removeLineupNotification(documentId: String) {
        repository.toggleLineupNotification(documentId, false)
    }

    fun refresh() {
        viewModelScope.launch {
            repository.refreshMatches()
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