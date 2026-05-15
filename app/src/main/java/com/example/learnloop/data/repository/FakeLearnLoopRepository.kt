package com.example.learnloop.data.repository

import com.example.learnloop.data.models.ChatMessage
import com.example.learnloop.data.models.Session
import com.example.learnloop.data.source.FakeLearnLoopDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeLearnLoopRepository(
    private val dataSource: FakeLearnLoopDataSource
) : LearnLoopRepository {
    private val _currentUser = MutableStateFlow(dataSource.currentUser())
    private val _helpRequests = MutableStateFlow(dataSource.helpRequests())
    private val _activeSessions = MutableStateFlow(dataSource.activeSessions())
    private val _completedSessions = MutableStateFlow(dataSource.completedSessions())
    private val _creditTransactions = MutableStateFlow(dataSource.creditTransactions())
    private val _notifications = MutableStateFlow(dataSource.notifications())
    private val _badges = MutableStateFlow(dataSource.badges())
    private val _leaderboard = MutableStateFlow(dataSource.leaderboard())
    private val _subjects = MutableStateFlow(dataSource.subjects())
    private val _languages = MutableStateFlow(dataSource.languages())
    private val appliedCreditSessions = mutableSetOf<String>()

    private val sessionFlows = mutableMapOf<String, MutableStateFlow<Session?>>()
    private val chatFlows = mutableMapOf<String, MutableStateFlow<List<ChatMessage>>>()

    override val currentUser: StateFlow<com.example.learnloop.data.models.User> = _currentUser.asStateFlow()
    override val helpRequests: StateFlow<List<com.example.learnloop.data.models.HelpRequest>> = _helpRequests.asStateFlow()
    override val activeSessions: StateFlow<List<Session>> = _activeSessions.asStateFlow()
    override val completedSessions: StateFlow<List<Session>> = _completedSessions.asStateFlow()
    override val creditTransactions: StateFlow<List<com.example.learnloop.data.models.CreditTransaction>> = _creditTransactions.asStateFlow()
    override val notifications: StateFlow<List<com.example.learnloop.data.models.Notification>> = _notifications.asStateFlow()
    override val badges: StateFlow<List<com.example.learnloop.data.models.Badge>> = _badges.asStateFlow()
    override val leaderboard: StateFlow<List<com.example.learnloop.data.models.LeaderboardEntry>> = _leaderboard.asStateFlow()
    override val subjects: StateFlow<List<String>> = _subjects.asStateFlow()
    override val languages: StateFlow<List<String>> = _languages.asStateFlow()

    override fun sessionById(sessionId: String): StateFlow<Session?> {
        return sessionFlows.getOrPut(sessionId) {
            MutableStateFlow(findSession(sessionId))
        }
    }

    override fun chatMessages(sessionId: String): StateFlow<List<ChatMessage>> {
        return chatFlows.getOrPut(sessionId) {
            MutableStateFlow(dataSource.chatMessages(sessionId))
        }
    }

    override suspend fun markAllNotificationsRead() {
        _notifications.update { list -> list.map { it.copy(isRead = true) } }
    }

    override suspend fun sendChatMessage(sessionId: String, message: ChatMessage) {
        val flow = chatFlows.getOrPut(sessionId) { MutableStateFlow(emptyList()) }
        flow.update { it + message }
    }

    override suspend fun updateProfile(name: String, bio: String, institutionEmail: String) {
        _currentUser.update { user ->
            user.copy(name = name, bio = bio, institutionEmail = institutionEmail)
        }
    }

    override suspend fun estimateCreditCost(durationMinutes: Int, urgency: String): Int {
        return dataSource.estimateCreditCost(durationMinutes, urgency)
    }

    override suspend fun matchedTutors(): List<com.example.learnloop.data.models.User> {
        return dataSource.users().drop(1).take(3)
    }

    override suspend fun applySessionCredits(sessionId: String, isTutor: Boolean, amount: Int, reason: String) {
        if (!appliedCreditSessions.add(sessionId)) return

        val delta = if (isTutor) amount else -amount
        _currentUser.update { user ->
            user.copy(knowledgeCredits = (user.knowledgeCredits + delta).coerceAtLeast(0))
        }
        _creditTransactions.update { list ->
            val type = if (isTutor) "EARNED" else "SPENT"
            val transaction = com.example.learnloop.data.models.CreditTransaction(
                id = "tx_$sessionId",
                type = type,
                amount = amount,
                reason = reason,
                timestamp = "Just now",
                relatedSessionId = sessionId
            )
            listOf(transaction) + list
        }
    }

    private fun findSession(sessionId: String): Session? {
        return (_activeSessions.value + _completedSessions.value).firstOrNull { it.id == sessionId }
    }
}
