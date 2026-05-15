package com.example.learnloop.ui.screens.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnloop.data.models.Notification
import com.example.learnloop.data.repository.LearnLoopRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class NotificationsUiState(
    val notifications: List<Notification> = emptyList(),
    val unreadCount: Int = 0
)

class NotificationsViewModel(
    private val repository: LearnLoopRepository
) : ViewModel() {
    val uiState: StateFlow<NotificationsUiState> = repository.notifications
        .map { notifications ->
            NotificationsUiState(
                notifications = notifications,
                unreadCount = notifications.count { !it.isRead }
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NotificationsUiState())

    fun markAllRead() {
        viewModelScope.launch {
            repository.markAllNotificationsRead()
        }
    }
}



