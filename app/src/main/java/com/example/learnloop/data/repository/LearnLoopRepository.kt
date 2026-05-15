package com.example.learnloop.data.repository

import com.example.learnloop.data.models.Badge
import com.example.learnloop.data.models.ChatMessage
import com.example.learnloop.data.models.CreditTransaction
import com.example.learnloop.data.models.HelpRequest
import com.example.learnloop.data.models.LeaderboardEntry
import com.example.learnloop.data.models.Notification
import com.example.learnloop.data.models.Session
import com.example.learnloop.data.models.User
import kotlinx.coroutines.flow.StateFlow

interface LearnLoopRepository {
    val currentUser: StateFlow<User>
    val helpRequests: StateFlow<List<HelpRequest>>
    val activeSessions: StateFlow<List<Session>>
    val completedSessions: StateFlow<List<Session>>
    val creditTransactions: StateFlow<List<CreditTransaction>>
    val notifications: StateFlow<List<Notification>>
    val badges: StateFlow<List<Badge>>
    val leaderboard: StateFlow<List<LeaderboardEntry>>
    val subjects: StateFlow<List<String>>
    val languages: StateFlow<List<String>>

    fun sessionById(sessionId: String): StateFlow<Session?>
    fun chatMessages(sessionId: String): StateFlow<List<ChatMessage>>

    suspend fun markAllNotificationsRead()
    suspend fun sendChatMessage(sessionId: String, message: ChatMessage)
    suspend fun updateProfile(name: String, bio: String, institutionEmail: String)

    suspend fun estimateCreditCost(durationMinutes: Int, urgency: String): Int
    suspend fun matchedTutors(): List<User>

    suspend fun applySessionCredits(sessionId: String, isTutor: Boolean, amount: Int, reason: String)
}
