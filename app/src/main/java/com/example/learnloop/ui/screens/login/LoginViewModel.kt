package com.example.learnloop.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnloop.data.repository.LearnLoopRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val isSignInEnabled: Boolean = false
)

sealed class LoginEvent {
    object NavigateHome : LoginEvent()
}

class LoginViewModel(
    private val repository: LearnLoopRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<LoginEvent>()
    val events: SharedFlow<LoginEvent> = _events.asSharedFlow()

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value, isSignInEnabled = canSignIn(value, it.password)) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, isSignInEnabled = canSignIn(it.email, value)) }
    }

    fun onTogglePasswordVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    fun onSignIn() {
        viewModelScope.launch {
            _events.emit(LoginEvent.NavigateHome)
        }
    }

    private fun canSignIn(email: String, password: String): Boolean {
        return email.isNotBlank() && password.isNotBlank()
    }
}