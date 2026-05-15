package com.example.learnloop.ui.screens.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnloop.data.models.LeaderboardEntry
import com.example.learnloop.data.repository.LearnLoopRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LeaderboardUiState(
	val selectedTab: Int = 0,
	val selectedSubject: String = "All",
	val isRefreshing: Boolean = false,
	val subjects: List<String> = emptyList(),
	val entries: List<LeaderboardEntry> = emptyList(),
	val currentUserEntry: LeaderboardEntry? = null
)

class LeaderboardViewModel(
	repository: LearnLoopRepository
) : ViewModel() {
	private val selectedTab = MutableStateFlow(0)
	private val selectedSubject = MutableStateFlow("All")
	private val isRefreshing = MutableStateFlow(false)
	private val subjects = listOf("All", "CS", "Math", "Physics", "Biology", "Economics")

	val uiState: StateFlow<LeaderboardUiState> = combine(
		repository.leaderboard,
		repository.currentUser,
		selectedTab,
		selectedSubject,
		isRefreshing
	) { entries, user, tab, subject, refreshing ->
		val current = entries.find { it.user.id == user.id }
			?: LeaderboardEntry(0, user, user.knowledgeCredits, user.totalSessionsTaught)
		LeaderboardUiState(
			selectedTab = tab,
			selectedSubject = subject,
			isRefreshing = refreshing,
			subjects = subjects,
			entries = entries,
			currentUserEntry = current
		)
	}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LeaderboardUiState())

	fun onTabSelected(index: Int) {
		selectedTab.value = index
	}

	fun onSubjectSelected(value: String) {
		selectedSubject.value = value
	}

	fun refresh() {
		viewModelScope.launch {
			isRefreshing.update { true }
			delay(800)
			isRefreshing.update { false }
		}
	}
}
