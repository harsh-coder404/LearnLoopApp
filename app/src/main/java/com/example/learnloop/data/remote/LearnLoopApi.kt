package com.example.learnloop.data.remote

import com.example.learnloop.data.remote.dto.AuthTokensDto
import com.example.learnloop.data.remote.dto.CreditTransactionDto
import com.example.learnloop.data.remote.dto.HelpRequestDto
import com.example.learnloop.data.remote.dto.LeaderboardEntryDto
import com.example.learnloop.data.remote.dto.NotificationDto
import com.example.learnloop.data.remote.dto.OtpVerifyRequestDto
import com.example.learnloop.data.remote.dto.PostHelpRequestDto
import com.example.learnloop.data.remote.dto.RegisterRequestDto
import com.example.learnloop.data.remote.dto.SessionDto
import com.example.learnloop.data.remote.dto.SignInRequestDto
import com.example.learnloop.data.remote.dto.SubjectExpertiseDto
import com.example.learnloop.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LearnLoopApi {
    @POST("/auth/login")
    suspend fun signIn(@Body request: SignInRequestDto): AuthTokensDto

    @POST("/auth/register")
    suspend fun register(@Body request: RegisterRequestDto): AuthTokensDto

    @POST("/auth/verify-otp")
    suspend fun verifyOtp(@Body request: OtpVerifyRequestDto): AuthTokensDto

    @GET("/users/me")
    suspend fun getCurrentUser(): UserDto

    @GET("/users/me/expertise")
    suspend fun getMyExpertise(): List<SubjectExpertiseDto>

    @GET("/requests")
    suspend fun getHelpRequests(): List<HelpRequestDto>

    @POST("/requests")
    suspend fun postHelpRequest(@Body request: PostHelpRequestDto): HelpRequestDto

    @GET("/sessions")
    suspend fun getSessions(): List<SessionDto>

    @GET("/sessions/{id}")
    suspend fun getSessionById(@Path("id") sessionId: String): SessionDto

    @GET("/credits/transactions")
    suspend fun getCreditTransactions(): List<CreditTransactionDto>

    @GET("/notifications")
    suspend fun getNotifications(): List<NotificationDto>

    @POST("/notifications/mark-all-read")
    suspend fun markAllNotificationsRead()

    @GET("/leaderboard")
    suspend fun getLeaderboard(): List<LeaderboardEntryDto>
}
