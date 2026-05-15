package com.example.learnloop.ui.screens.editprofile

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

data class EditProfileUiState(
    val name: String = "",
    val bio: String = "",
    val institutionEmail: String = ""
)

sealed class EditProfileEvent {
    object NavigateBack : EditProfileEvent()
}

class EditProfileViewModel(
    private val repository: LearnLoopRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<EditProfileEvent>()
    val events: SharedFlow<EditProfileEvent> = _events.asSharedFlow()

    private var initialized = false

    init {
        viewModelScope.launch {
            repository.currentUser.collectLatest { user ->
                if (!initialized) {
                    _uiState.update {
                        it.copy(
                            name = user.name,
                            bio = user.bio.orEmpty(),
                            institutionEmail = user.institutionEmail
                        )
                    }
                    initialized = true
                }
            }
        }
    }

    fun onNameChange(value: String) {
        _uiState.update { it.copy(name = value) }
    }

    fun onBioChange(value: String) {
        _uiState.update { it.copy(bio = value) }
    }

    fun onInstitutionEmailChange(value: String) {
        _uiState.update { it.copy(institutionEmail = value) }
    }

    fun onSave() {
        val state = _uiState.value
        viewModelScope.launch {
            repository.updateProfile(state.name, state.bio, state.institutionEmail)
            _events.emit(EditProfileEvent.NavigateBack)
        }
    }
}


