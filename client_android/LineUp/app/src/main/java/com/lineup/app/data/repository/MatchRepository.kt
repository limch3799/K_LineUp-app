package com.lineup.app.data.repository

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.lineup.app.data.model.Match
import com.lineup.app.data.preference.LineupPreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class MatchRepository private constructor(context: Context) {
    private val firestore = FirebaseFirestore.getInstance()
    private val matchesCollection = firestore.collection("matches")
    private val preferenceManager = LineupPreferenceManager(context.applicationContext)

    private val _cachedMatches = MutableStateFlow<List<Match>>(emptyList())
    val cachedMatches: StateFlow<List<Match>> = _cachedMatches

    private var matchesListener: ListenerRegistration? = null
    private var isInitialized = false

    companion object {
        @Volatile
        private var INSTANCE: MatchRepository? = null

        fun getInstance(context: Context): MatchRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: MatchRepository(context).also { INSTANCE = it }
            }
        }
    }

    fun startMatchesListener() {
        if (isInitialized) return

        matchesListener = matchesCollection
            .orderBy("matchTime", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val matches = snapshot.documents.mapNotNull { document ->
                        document.toObject(Match::class.java)?.copy(
                            documentId = document.id,
                            isLineupNotificationOn = preferenceManager.isLineupNotificationEnabled(document.id)
                        )
                    }
                    _cachedMatches.value = matches
                }
            }

        isInitialized = true
    }

    fun getCachedMatches(): List<Match> {
        return _cachedMatches.value
    }

    suspend fun refreshMatches(): Result<List<Match>> {
        return try {
            val snapshot = matchesCollection
                .orderBy("matchTime", Query.Direction.ASCENDING)
                .get()
                .await()

            val matches = snapshot.documents.mapNotNull { document ->
                document.toObject(Match::class.java)?.copy(
                    documentId = document.id,
                    isLineupNotificationOn = preferenceManager.isLineupNotificationEnabled(document.id)
                )
            }
            _cachedMatches.value = matches
            Result.success(matches)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun toggleLineupNotification(documentId: String, isEnabled: Boolean) {
        if (isEnabled) {
            preferenceManager.addLineupNotification(documentId)
        } else {
            preferenceManager.removeLineupNotification(documentId)
        }

        val updatedMatches = _cachedMatches.value.map { match ->
            if (match.documentId == documentId) {
                match.copy(isLineupNotificationOn = isEnabled)
            } else {
                match
            }
        }

        _cachedMatches.value = updatedMatches
    }

    fun isLineupNotificationEnabled(documentId: String): Boolean {
        return preferenceManager.isLineupNotificationEnabled(documentId)
    }

    fun getMyPickMatches(): List<Match> {
        val pickedDocumentIds = preferenceManager.getLineupNotifications()
        return _cachedMatches.value.filter { match ->
            pickedDocumentIds.contains(match.documentId)
        }
    }

    fun stopMatchesListener() {
        matchesListener?.remove()
        matchesListener = null
        isInitialized = false
    }
}