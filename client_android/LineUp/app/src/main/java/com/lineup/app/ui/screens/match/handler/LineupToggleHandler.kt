package com.lineup.app.ui.screens.match.handler

import com.lineup.app.data.model.Match
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 라인업 알림 토글 핸들러
 * TODO: Firebase에 데이터 저장 구현 필요
 */
object LineupToggleHandler {

    /**
     * 라인업 알림 상태 변경
     * @param match 매치 정보
     * @param isEnabled 라인업 알림 활성화 여부
     */
    fun toggleLineupNotification(match: Match, isEnabled: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // TODO: Firebase Firestore에 라인업 알림 상태 저장
                // 예시:
                // val updates = hashMapOf<String, Any>(
                //     "lineupNotificationEnabled" to isEnabled,
                //     "lineupNotificationUpdatedAt" to FieldValue.serverTimestamp()
                // )
                // FirebaseFirestore.getInstance()
                //     .collection("matches")
                //     .document(match.documentId)
                //     .update(updates)
                //     .await()

                println("라인업 알림 ${if (isEnabled) "활성화" else "비활성화"}: ${match.homeTeam} vs ${match.awayTeam}")
            } catch (e: Exception) {
                println("라인업 알림 상태 변경 실패: ${e.message}")
            }
        }
    }
}