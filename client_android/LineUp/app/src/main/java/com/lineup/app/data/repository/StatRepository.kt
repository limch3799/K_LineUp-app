package com.lineup.app.data.repository

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.lineup.app.data.model.Stat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class StatRepository private constructor(context: Context) {
    private val firestore = FirebaseFirestore.getInstance()
    private val statsCollection = firestore.collection("stats")

    private val _cachedStats = MutableStateFlow<List<Stat>>(emptyList())
    val cachedStats: StateFlow<List<Stat>> = _cachedStats

    private var isInitialized = false

    companion object {
        @Volatile
        private var INSTANCE: StatRepository? = null

        fun getInstance(context: Context): StatRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: StatRepository(context).also { INSTANCE = it }
            }
        }
    }

    suspend fun loadStats(): Result<List<Stat>> {
        if (isInitialized && _cachedStats.value.isNotEmpty()) {
            return Result.success(_cachedStats.value)
        }

        return try {
            val snapshot = statsCollection.get().await()

            val stats = snapshot.documents.mapNotNull { document ->
                document.toObject(Stat::class.java)?.copy(
                    documentId = document.id
                )
            }

            _cachedStats.value = stats
            isInitialized = true
            Result.success(stats)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getCachedStats(): List<Stat> {
        return _cachedStats.value
    }
}