package com.example.learnloop.ui.screens.sessions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnloop.data.models.Session
import com.example.learnloop.data.repository.LearnLoopRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class MySessionsUiState(
    val selectedTab: Int = 1,
    val tabs: List<String> = listOf("Upcoming", "Active", "Completed"),
    val upcoming: List<Session> = emptyList(),
    val active: List<Session> = emptyList(),
    val completed: List<Session> = emptyList(),
    val sessionsToShow: List<Session> = emptyList()
)

class MySessionsViewModel(
    repository: LearnLoopRepository
) : ViewModel() {
    private val selectedTab = MutableStateFlow(1)

    val uiState: StateFlow<MySessionsUiState> = combine(
        repository.activeSessions,
        repository.completedSessions,
        selectedTab
    ) { activeSessions, completedSessions, tab ->
        val upcoming = activeSessions.filter { it.status == "SCHEDULED" }
        val active = activeSessions.filter { it.status == "ACTIVE" }
        val completed = completedSessions
        val sessionsToShow = when (tab) {
            0 -> upcoming
            1 -> active
            else -> completed
        }
        MySessionsUiState(
            selectedTab = tab,
            upcoming = upcoming,
            active = active,
            completed = completed,
            sessionsToShow = sessionsToShow
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MySessionsUiState())

    fun onTabSelected(index: Int) {
        selectedTab.value = index
    }
}