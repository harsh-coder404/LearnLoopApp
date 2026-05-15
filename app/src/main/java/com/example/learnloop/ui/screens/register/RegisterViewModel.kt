package com.example.learnloop.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnloop.data.repository.LearnLoopRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SubjectSelection(
    val subject: String,
    val level: String
)

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val institutionEmail: String = "",
    val passwordVisible: Boolean = false,
    val selectedSubjects: List<SubjectSelection> = emptyList(),
    val selectedLanguages: List<String> = emptyList(),
    val subjects: List<String> = emptyList(),
    val languages: List<String> = emptyList()
)

sealed class RegisterEvent {
    data class NavigateToOtp(val email: String) : RegisterEvent()
}

class RegisterViewModel(
    private val repository: LearnLoopRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<RegisterEvent>()
    val events: SharedFlow<RegisterEvent> = _events.asSharedFlow()

    init {
        viewModelScope.launch {
            repository.subjects.collectLatest { list ->
                _uiState.update { it.copy(subjects = list) }
            }
        }
        viewModelScope.launch {
            repository.languages.collectLatest { list ->
                _uiState.update { it.copy(languages = list) }
            }
        }
    }

    fun onNameChange(value: String) {
        _uiState.update { it.copy(name = value) }
    }

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value) }
    }

    fun onInstitutionEmailChange(value: String) {
        _uiState.update { it.copy(institutionEmail = value) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value) }
    }

    fun onTogglePasswordVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    fun addSubject(subject: String, level: String) {
        _uiState.update { state ->
            if (subject.isBlank() || state.selectedSubjects.any { it.subject == subject }) {
                state
            } else {
                state.copy(selectedSubjects = state.selectedSubjects + SubjectSelection(subject, level))
            }
        }
    }

    fun removeSubject(subject: String, level: String) {
        _uiState.update { state ->
            state.copy(selectedSubjects = state.selectedSubjects.filterNot { it.subject == subject && it.level == level })
        }
    }

    fun toggleLanguage(language: String) {
        _uiState.update { state ->
            val updated = if (state.selectedLanguages.contains(language)) {
                state.selectedLanguages - language
            } else {
                state.selectedLanguages + language
            }
            state.copy(selectedLanguages = updated)
        }
    }

    fun onCreateAccount() {
        viewModelScope.launch {
            val email = _uiState.value.email.ifBlank { "user@example.com" }
            _events.emit(RegisterEvent.NavigateToOtp(email))
        }
    }
}
