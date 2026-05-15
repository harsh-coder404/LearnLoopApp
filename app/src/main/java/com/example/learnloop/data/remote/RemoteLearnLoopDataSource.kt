package com.example.learnloop.data.remote

import com.example.learnloop.data.models.CreditTransaction
import com.example.learnloop.data.models.HelpRequest
import com.example.learnloop.data.models.LeaderboardEntry
import com.example.learnloop.data.models.Notification
import com.example.learnloop.data.models.Session
import com.example.learnloop.data.models.SubjectExpertise
import com.example.learnloop.data.models.User
import com.example.learnloop.data.remote.dto.OtpVerifyRequestDto
import com.example.learnloop.data.remote.dto.PostHelpRequestDto
import com.example.learnloop.data.remote.dto.RegisterRequestDto
import com.example.learnloop.data.remote.dto.SignInRequestDto
import com.example.learnloop.data.remote.mapper.toModel
import com.example.learnloop.data.remote.mapper.toDto

class RemoteLearnLoopDataSource(
    private val api: LearnLoopApi
) {
    suspend fun signIn(email: String, password: String) = api.signIn(SignInRequestDto(email, password))

    suspend fun register(
        name: String,
        email: String,
        institutionEmail: String,
        password: String,
        subjectExpertise: List<SubjectExpertise>,
        languages: List<String>
    ) = api.register(
        RegisterRequestDto(
            name = name,
            email = email,
            institutionEmail = institutionEmail,
            password = password,
            subjectExpertise = subjectExpertise.map { it.toDto() },
            languagesSpoken = languages
        )
    )

    suspend fun verifyOtp(email: String, code: String) = api.verifyOtp(OtpVerifyRequestDto(email, code))

    suspend fun getCurrentUser(): User = api.getCurrentUser().toModel()

    suspend fun getHelpRequests(): List<HelpRequest> = api.getHelpRequests().map { it.toModel() }

    suspend fun postHelpRequest(request: PostHelpRequestDto): HelpRequest = api.postHelpRequest(request).toModel()

    suspend fun getSessions(): List<Session> = api.getSessions().map { it.toModel() }

    suspend fun getSession(sessionId: String): Session = api.getSessionById(sessionId).toModel()

    suspend fun getCreditTransactions(): List<CreditTransaction> = api.getCreditTransactions().map { it.toModel() }

    suspend fun getNotifications(): List<Notification> = api.getNotifications().map { it.toModel() }

    suspend fun markAllNotificationsRead() = api.markAllNotificationsRead()

    suspend fun getLeaderboard(): List<LeaderboardEntry> = api.getLeaderboard().map { it.toModel() }
}
