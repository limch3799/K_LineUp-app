package com.lineup.app.data.model

import com.google.firebase.Timestamp

data class Stat(
    val name: String = "",
    val currentTeam: String = "",
    val league: String = "",
    val matches: Int = 0,
    val goals: Int = 0,
    val assists: Int = 0,
    val fouls: Int = 0,
    val shotsOnTarget: Int = 0,
    val playTime: Int = 0,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null,
    val documentId: String = ""
)