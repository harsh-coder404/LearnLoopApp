package com.example.learnloop.ui.screens.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OtpUiState(
    val email: String = "",
    val digits: List<String> = List(6) { "" },
    val countdown: Int = 60,
    val canResend: Boolean = false,
    val isVerifyEnabled: Boolean = false
)

sealed class OtpEvent {
    object NavigateHome : OtpEvent()
}

class OtpViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(OtpUiState())
    val uiState: StateFlow<OtpUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<OtpEvent>()
    val events: SharedFlow<OtpEvent> = _events.asSharedFlow()

    init {
        startCountdown()
    }

    fun setEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onDigitChange(index: Int, value: String) {
        if (value.length > 1 || value.any { !it.isDigit() }) return
        _uiState.update { state ->
            val updated = state.digits.toMutableList().apply { this[index] = value }
            state.copy(digits = updated, isVerifyEnabled = updated.all { it.isNotEmpty() })
        }
    }

    fun onResend() {
        _uiState.update { it.copy(countdown = 60, canResend = false) }
        startCountdown()
    }

    fun onVerify() {
        viewModelScope.launch {
            _events.emit(OtpEvent.NavigateHome)
        }
    }

    private fun startCountdown() {
        viewModelScope.launch {
            while (_uiState.value.countdown > 0) {
                delay(1000)
                _uiState.update { it.copy(countdown = it.countdown - 1) }
            }
            _uiState.update { it.copy(canResend = true) }
        }
    }
}