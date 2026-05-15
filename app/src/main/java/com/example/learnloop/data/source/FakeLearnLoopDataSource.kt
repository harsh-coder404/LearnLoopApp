package com.example.learnloop.data.source

import com.example.learnloop.data.models.DummyData

class FakeLearnLoopDataSource {
    fun currentUser() = DummyData.currentUser
    fun users() = DummyData.users
    fun helpRequests() = DummyData.helpRequests
    fun activeSessions() = DummyData.activeSessions
    fun completedSessions() = DummyData.completedSessions
    fun creditTransactions() = DummyData.creditTransactions
    fun notifications() = DummyData.notifications
    fun badges() = DummyData.badges
    fun leaderboard() = DummyData.leaderboard
    fun chatMessages(sessionId: String) = DummyData.chatMessages
    fun subjects() = DummyData.subjects
    fun languages() = DummyData.languages

    fun estimateCreditCost(durationMinutes: Int, urgency: String): Int {
        val multiplier = when (urgency) {
            "URGENT" -> 3f
            "HIGH" -> 2f
            "MEDIUM" -> 1.5f
            else -> 1f
        }
        return ((durationMinutes / 30f) * multiplier).toInt().coerceAtLeast(1)
    }
}
