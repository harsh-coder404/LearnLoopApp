package com.example.learnloop.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnloop.data.models.HelpRequest
import com.example.learnloop.data.models.Session
import com.example.learnloop.data.models.User
import com.example.learnloop.data.repository.LearnLoopRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class HomeUiState(
    val user: User = User(),
    val unreadCount: Int = 0,
    val activeSessions: List<Session> = emptyList(),
    val openRequests: List<HelpRequest> = emptyList()
)

class HomeViewModel(
    repository: LearnLoopRepository
) : ViewModel() {
    val uiState: StateFlow<HomeUiState> = combine(
        repository.currentUser,
        repository.notifications,
        repository.activeSessions,
        repository.helpRequests
    ) { user, notifications, activeSessions, requests ->
        HomeUiState(
            user = user,
            unreadCount = notifications.count { !it.isRead },
            activeSessions = activeSessions,
            openRequests = requests.filter { it.status == "OPEN" }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())
}