package com.example.learnloop.ui.screens.sessionsummary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SummarySection(
	val title: String,
	val items: List<String>,
	val numbered: Boolean = false
)

data class SessionSummaryUiState(
	val rating: Int = 0,
	val comment: String = "",
	val isTutor: Boolean = true,
	val creditAmount: Int = 0,
	val sessionSubtitle: String = "45 minutes  ·  BST Deletion",
	val summarySections: List<SummarySection> = emptyList()
)

class SessionSummaryViewModel(
	private val repository: com.example.learnloop.data.repository.LearnLoopRepository
) : ViewModel() {
	private val _uiState = MutableStateFlow(
		SessionSummaryUiState(
			summarySections = listOf(
				SummarySection(
					title = "Key Concepts Covered",
					items = listOf(
						"Binary Search Tree deletion algorithm",
						"In-order successor concept",
						"Three cases: no child, one child, two children",
						"Time complexity: O(h) where h = height"
					)
				),
				SummarySection(
					title = "Code / Notes Shared",
					items = listOf(
						"BST delete() pseudocode walkthrough",
						"findMin() helper function",
						"Recursive deletion implementation"
					)
				),
				SummarySection(
					title = "3 Takeaways",
					items = listOf(
						"Always find the in-order successor (smallest in right subtree) for two-child deletion.",
						"The in-order successor has at most one right child, simplifying its own deletion.",
						"For balanced BSTs: O(log n), but O(n) worst-case for skewed trees."
					),
					numbered = true
				)
			)
		)
	)
	val uiState: StateFlow<SessionSummaryUiState> = _uiState.asStateFlow()

	fun onRatingChange(value: Int) {
		_uiState.update { it.copy(rating = value) }
	}

	fun onCommentChange(value: String) {
		_uiState.update { it.copy(comment = value) }
	}

	fun onScreenShown(sessionId: String) {
		viewModelScope.launch {
			val session = repository.sessionById(sessionId).value ?: return@launch
			val currentUser = repository.currentUser.value
			val isTutor = session.tutor.id == currentUser.id
			val amount = session.creditsTransferred.coerceAtLeast(1)
			val subtitle = "${session.helpRequest.sessionDuration} minutes  ·  ${session.helpRequest.topic}"

			_uiState.update {
				it.copy(isTutor = isTutor, creditAmount = amount, sessionSubtitle = subtitle)
			}

			repository.applySessionCredits(
				sessionId = sessionId,
				isTutor = isTutor,
				amount = amount,
				reason = "Session: ${session.helpRequest.topic}"
			)
		}
	}
}

