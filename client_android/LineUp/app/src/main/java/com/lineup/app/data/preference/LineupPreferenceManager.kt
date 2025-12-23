package com.lineup.app.data.preference

import android.content.Context
import android.content.SharedPreferences

class LineupPreferenceManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "lineup_notifications",
        Context.MODE_PRIVATE
    )

    companion object {
        private const val KEY_LINEUP_NOTIFICATIONS = "lineup_notification_ids"
    }

    /**
     * 라인업 알림 추가
     */
    fun addLineupNotification(documentId: String) {
        val current = getLineupNotifications().toMutableSet()
        current.add(documentId)
        saveLineupNotifications(current)
    }

    /**
     * 라인업 알림 제거
     */
    fun removeLineupNotification(documentId: String) {
        val current = getLineupNotifications().toMutableSet()
        current.remove(documentId)
        saveLineupNotifications(current)
    }

    /**
     * 라인업 알림 상태 확인
     */
    fun isLineupNotificationEnabled(documentId: String): Boolean {
        return getLineupNotifications().contains(documentId)
    }

    /**
     * 모든 라인업 알림 ID 가져오기
     */
    fun getLineupNotifications(): Set<String> {
        val idsString = prefs.getString(KEY_LINEUP_NOTIFICATIONS, "") ?: ""
        return if (idsString.isEmpty()) {
            emptySet()
        } else {
            idsString.split(",").toSet()
        }
    }

    /**
     * 라인업 알림 저장
     */
    private fun saveLineupNotifications(ids: Set<String>) {
        prefs.edit()
            .putString(KEY_LINEUP_NOTIFICATIONS, ids.joinToString(","))
            .apply()
    }

    /**
     * 모든 라인업 알림 삭제
     */
    fun clearAllLineupNotifications() {
        prefs.edit()
            .remove(KEY_LINEUP_NOTIFICATIONS)
            .apply()
    }
}