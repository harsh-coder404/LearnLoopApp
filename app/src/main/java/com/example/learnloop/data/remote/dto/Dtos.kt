package com.example.learnloop.data.remote.dto

import com.squareup.moshi.Json

data class AuthTokensDto(
    @Json(name = "accessToken") val accessToken: String,
    @Json(name = "refreshToken") val refreshToken: String
)

data class SignInRequestDto(
    val email: String,
    val password: String
)

data class RegisterRequestDto(
    val name: String,
    val email: String,
    val institutionEmail: String,
    val password: String,
    val subjectExpertise: List<SubjectExpertiseDto>,
    val languagesSpoken: List<String>
)

data class OtpVerifyRequestDto(
    val email: String,
    val code: String
)

data class UserDto(
    val id: String,
    val name: String,
    val email: String,
    val institutionEmail: String,
    val isVerified: Boolean,
    val knowledgeCredits: Int,
    val reputationScore: Float,
    val teachingStreak: Int,
    val totalSessionsTaught: Int,
    val totalSessionsLearned: Int,
    val profilePicture: String?,
    val bio: String?,
    val languagesSpoken: List<String>
)

data class SubjectExpertiseDto(
    val id: String,
    val subject: String,
    val level: String,
    val userId: String
)

data class HelpRequestDto(
    val id: String,
    val subject: String,
    val topic: String,
    val description: String,
    val preferredLanguage: String,
    val urgencyLevel: String,
    val sessionDuration: Int,
    val creditCost: Int,
    val status: String,
    val createdAt: String,
    val postedBy: UserDto,
    val assignedTutorId: String?
)

data class PostHelpRequestDto(
    val subject: String,
    val topic: String,
    val description: String,
    val preferredLanguage: String,
    val urgencyLevel: String,
    val sessionDuration: Int
)

data class SessionDto(
    val id: String,
    val startTime: String?,
    val endTime: String?,
    val status: String,
    val sessionNotes: String?,
    val sharedResources: List<String>,
    val creditsTransferred: Int,
    val helpRequest: HelpRequestDto,
    val tutor: UserDto,
    val learner: UserDto
)

data class CreditTransactionDto(
    val id: String,
    val type: String,
    val amount: Int,
    val reason: String,
    val timestamp: String,
    val relatedSessionId: String?
)

data class NotificationDto(
    val id: String,
    val message: String,
    val type: String,
    val isRead: Boolean,
    val createdAt: String
)

data class BadgeDto(
    val id: String,
    val name: String,
    val description: String,
    val icon: String,
    val isEarned: Boolean,
    val progress: String
)

data class ChatMessageDto(
    val id: String,
    val senderId: String,
    val senderName: String,
    val content: String,
    val type: String,
    val timestamp: String
)

data class LeaderboardEntryDto(
    val rank: Int,
    val user: UserDto,
    val creditsEarned: Int,
    val sessionsCount: Int
)