package com.lineup.app.data.model

import com.google.firebase.Timestamp

data class Match(
    val homeTeam: String = "",
    val awayTeam: String = "",
    val competition: String = "",
    val matchTime: Timestamp? = null,
    val stadium: String = "",
    val status: String = "",
    val homeScore: Int = 0,
    val awayScore: Int = 0,
    val koreanPlayerIds: List<String> = emptyList(),
    val isActive: Boolean = true,
    val subscriberCount: Int = 0,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null,
    val documentId: String = "",
    val isLineupNotificationOn: Boolean = false
)