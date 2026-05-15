package com.example.learnloop.ui.screens.sessionroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnloop.data.models.ChatMessage
import com.example.learnloop.data.repository.LearnLoopRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SessionRoomUiState(
	val sessionId: String = "",
	val selectedTab: Int = 0,
	val showEndDialog: Boolean = false,
	val elapsedSeconds: Int = 0,
	val currentUserId: String = "u1",
	val otherUserName: String = "Rahul Gupta",
	val messages: List<ChatMessage> = emptyList(),
	val inputText: String = "",
	val isCodeMode: Boolean = false,
	val whiteboardContent: String = "",
	val notes: String = ""
)

class SessionRoomViewModel(
	private val repository: LearnLoopRepository
) : ViewModel() {
	private val _uiState = MutableStateFlow(SessionRoomUiState())
	val uiState: StateFlow<SessionRoomUiState> = _uiState.asStateFlow()

	private var chatJob: Job? = null

	init {
		viewModelScope.launch {
			while (true) {
				delay(1000)
				_uiState.update { it.copy(elapsedSeconds = it.elapsedSeconds + 1) }
			}
		}
	}

	fun setSessionId(sessionId: String) {
		if (_uiState.value.sessionId == sessionId) return
		_uiState.update { it.copy(sessionId = sessionId) }
		chatJob?.cancel()
		chatJob = viewModelScope.launch {
			repository.chatMessages(sessionId).collectLatest { messages ->
				_uiState.update { it.copy(messages = messages) }
			}
		}
	}

	fun onTabSelected(index: Int) {
		_uiState.update { it.copy(selectedTab = index) }
	}

	fun onToggleEndDialog(show: Boolean) {
		_uiState.update { it.copy(showEndDialog = show) }
	}

	fun onInputChange(value: String) {
		_uiState.update { it.copy(inputText = value) }
	}

	fun onToggleCodeMode() {
		_uiState.update { it.copy(isCodeMode = !it.isCodeMode) }
	}

	fun onSendMessage() {
		val state = _uiState.value
		if (state.inputText.isBlank() || state.sessionId.isBlank()) return
		val message = ChatMessage(
			id = "m${state.messages.size + 1}",
			senderId = state.currentUserId,
			senderName = "Alex Johnson",
			content = state.inputText,
			type = if (state.isCodeMode) "CODE" else "TEXT",
			timestamp = "Now"
		)
		viewModelScope.launch {
			repository.sendChatMessage(state.sessionId, message)
		}
		_uiState.update { it.copy(inputText = "") }
	}

	fun onWhiteboardChange(value: String) {
		_uiState.update { it.copy(whiteboardContent = value) }
	}

	fun onNotesChange(value: String) {
		_uiState.update { it.copy(notes = value) }
	}
}