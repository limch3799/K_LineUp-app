//package com.lineup.app.data.repository
//
//import android.content.Context
//import com.google.firebase.firestore.FirebaseFirestore
//import com.lineup.app.data.model.Match
//import com.lineup.app.data.preference.LineupPreferenceManager
//import kotlinx.coroutines.tasks.await
//
//class MyPickRepository(context: Context) {
//    private val firestore = FirebaseFirestore.getInstance()
//    private val matchesCollection = firestore.collection("matches")
//    private val preferenceManager = LineupPreferenceManager(context)
//
//    /**
//     * 내가 Pick한 매치들 가져오기
//     */
//    suspend fun getMyPickMatches(): Result<List<Match>> {
//        return try {
//            val pickedDocumentIds = preferenceManager.getLineupNotifications()
//
//            if (pickedDocumentIds.isEmpty()) {
//                return Result.success(emptyList())
//            }
//
//            val matches = mutableListOf<Match>()
//
//            // 각 documentId로 매치 가져오기
//            pickedDocumentIds.forEach { documentId ->
//                try {
//                    val document = matchesCollection.document(documentId).get().await()
//                    document.toObject(Match::class.java)?.let { match ->
//                        match.documentId = document.id
//                        match.isLineupNotificationOn = true // MyPick은 모두 ON
//                        matches.add(match)
//                    }
//                } catch (e: Exception) {
//                    // 개별 매치 로드 실패는 무시하고 계속 진행
//                }
//            }
//
//            // matchTime으로 정렬
//            val sortedMatches = matches.sortedBy { it.matchTime?.toDate()?.time ?: Long.MAX_VALUE }
//            Result.success(sortedMatches)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//
//    /**
//     * 라인업 알림 제거 (MyPick에서 제거)
//     */
//    fun removeLineupNotification(documentId: String) {
//        preferenceManager.removeLineupNotification(documentId)
//        // TODO: Firebase에 저장
//    }
//}