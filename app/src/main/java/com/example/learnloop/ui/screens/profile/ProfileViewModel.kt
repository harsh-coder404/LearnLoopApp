package com.example.learnloop.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnloop.data.models.Badge
import com.example.learnloop.data.models.User
import com.example.learnloop.data.repository.LearnLoopRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class ProfileUiState(
    val user: User = User(),
    val badges: List<Badge> = emptyList()
)

class ProfileViewModel(
    repository: LearnLoopRepository
) : ViewModel() {
    val uiState: StateFlow<ProfileUiState> = combine(
        repository.currentUser,
        repository.badges
    ) { user, badges ->
        ProfileUiState(user = user, badges = badges)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProfileUiState())
}


