package com.example.learnloop.data.models

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val institutionEmail: String = "",
    val isVerified: Boolean = false,
    val knowledgeCredits: Int = 20,
    val reputationScore: Float = 0f,
    val teachingStreak: Int = 0,
    val totalSessionsTaught: Int = 0,
    val totalSessionsLearned: Int = 0,
    val profilePicture: String? = null,
    val bio: String? = null,
    val languagesSpoken: List<String> = emptyList(),
    val subjectExpertise: List<SubjectExpertise> = emptyList()
)

data class SubjectExpertise(
    val id: String = "",
    val subject: String = "",
    val level: String = "",
    val userId: String = ""
)

data class HelpRequest(
    val id: String = "",
    val subject: String = "",
    val topic: String = "",
    val description: String = "",
    val preferredLanguage: String = "English",
    val urgencyLevel: String = "LOW",
    val sessionDuration: Int = 30,
    val creditCost: Int = 1,
    val status: String = "OPEN",
    val createdAt: String = "",
    val postedBy: User = User(),
    val assignedTutorId: String? = null
)

data class Session(
    val id: String = "",
    val startTime: String? = null,
    val endTime: String? = null,
    val status: String = "SCHEDULED",
    val sessionNotes: String? = null,
    val sharedResources: List<String> = emptyList(),
    val creditsTransferred: Int = 0,
    val helpRequest: HelpRequest = HelpRequest(),
    val tutor: User = User(),
    val learner: User = User()
)

data class CreditTransaction(
    val id: String = "",
    val type: String = "",
    val amount: Int = 0,
    val reason: String = "",
    val timestamp: String = "",
    val relatedSessionId: String? = null
)

data class Notification(
    val id: String = "",
    val message: String = "",
    val type: String = "",
    val isRead: Boolean = false,
    val createdAt: String = ""
)

data class Badge(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val icon: String = "",
    val isEarned: Boolean = false,
    val progress: String = ""
)

data class ChatMessage(
    val id: String = "",
    val senderId: String = "",
    val senderName: String = "",
    val content: String = "",
    val type: String = "TEXT",
    val timestamp: String = ""
)

data class LeaderboardEntry(
    val rank: Int = 0,
    val user: User = User(),
    val creditsEarned: Int = 0,
    val sessionsCount: Int = 0
)
