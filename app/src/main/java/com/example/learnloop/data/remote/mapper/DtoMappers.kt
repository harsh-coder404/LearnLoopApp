package com.example.learnloop.data.remote.mapper

import com.example.learnloop.data.models.Badge
import com.example.learnloop.data.models.ChatMessage
import com.example.learnloop.data.models.CreditTransaction
import com.example.learnloop.data.models.HelpRequest
import com.example.learnloop.data.models.LeaderboardEntry
import com.example.learnloop.data.models.Notification
import com.example.learnloop.data.models.Session
import com.example.learnloop.data.models.SubjectExpertise
import com.example.learnloop.data.models.User
import com.example.learnloop.data.remote.dto.BadgeDto
import com.example.learnloop.data.remote.dto.ChatMessageDto
import com.example.learnloop.data.remote.dto.CreditTransactionDto
import com.example.learnloop.data.remote.dto.HelpRequestDto
import com.example.learnloop.data.remote.dto.LeaderboardEntryDto
import com.example.learnloop.data.remote.dto.NotificationDto
import com.example.learnloop.data.remote.dto.SessionDto
import com.example.learnloop.data.remote.dto.SubjectExpertiseDto
import com.example.learnloop.data.remote.dto.UserDto

fun UserDto.toModel(subjectExpertise: List<SubjectExpertise> = emptyList()): User {
    return User(
        id = id,
        name = name,
        email = email,
        institutionEmail = institutionEmail,
        isVerified = isVerified,
        knowledgeCredits = knowledgeCredits,
        reputationScore = reputationScore,
        teachingStreak = teachingStreak,
        totalSessionsTaught = totalSessionsTaught,
        totalSessionsLearned = totalSessionsLearned,
        profilePicture = profilePicture,
        bio = bio,
        languagesSpoken = languagesSpoken,
        subjectExpertise = subjectExpertise
    )
}

fun SubjectExpertiseDto.toModel(): SubjectExpertise {
    return SubjectExpertise(
        id = id,
        subject = subject,
        level = level,
        userId = userId
    )
}

fun HelpRequestDto.toModel(postedByUser: User = this.postedBy.toModel()): HelpRequest {
    return HelpRequest(
        id = id,
        subject = subject,
        topic = topic,
        description = description,
        preferredLanguage = preferredLanguage,
        urgencyLevel = urgencyLevel,
        sessionDuration = sessionDuration,
        creditCost = creditCost,
        status = status,
        createdAt = createdAt,
        postedBy = postedByUser,
        assignedTutorId = assignedTutorId
    )
}

fun SessionDto.toModel(): Session {
    return Session(
        id = id,
        startTime = startTime,
        endTime = endTime,
        status = status,
        sessionNotes = sessionNotes,
        sharedResources = sharedResources,
        creditsTransferred = creditsTransferred,
        helpRequest = helpRequest.toModel(postedByUser = helpRequest.postedBy.toModel()),
        tutor = tutor.toModel(),
        learner = learner.toModel()
    )
}

fun CreditTransactionDto.toModel(): CreditTransaction {
    return CreditTransaction(
        id = id,
        type = type,
        amount = amount,
        reason = reason,
        timestamp = timestamp,
        relatedSessionId = relatedSessionId
    )
}

fun NotificationDto.toModel(): Notification {
    return Notification(
        id = id,
        message = message,
        type = type,
        isRead = isRead,
        createdAt = createdAt
    )
}

fun BadgeDto.toModel(): Badge {
    return Badge(
        id = id,
        name = name,
        description = description,
        icon = icon,
        isEarned = isEarned,
        progress = progress
    )
}

fun ChatMessageDto.toModel(): ChatMessage {
    return ChatMessage(
        id = id,
        senderId = senderId,
        senderName = senderName,
        content = content,
        type = type,
        timestamp = timestamp
    )
}

fun LeaderboardEntryDto.toModel(): LeaderboardEntry {
    return LeaderboardEntry(
        rank = rank,
        user = user.toModel(),
        creditsEarned = creditsEarned,
        sessionsCount = sessionsCount
    )
}

fun SubjectExpertise.toDto(): SubjectExpertiseDto {
    return SubjectExpertiseDto(
        id = id,
        subject = subject,
        level = level,
        userId = userId
    )
}


