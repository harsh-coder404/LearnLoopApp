package com.example.learnloop.ui.screens.postrequest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnloop.data.models.User
import com.example.learnloop.data.repository.LearnLoopRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class PostRequestUiState(
    val step: Int = 0,
    val selectedSubject: String = "",
    val topic: String = "",
    val description: String = "",
    val urgency: String = "MEDIUM",
    val duration: Int = 30,
    val language: String = "English",
    val isLanguageMenuExpanded: Boolean = false,
    val subjects: List<String> = emptyList(),
    val languages: List<String> = emptyList(),
    val creditCost: Int = 1,
    val userBalance: Int = 0,
    val matchedTutors: List<User> = emptyList(),
    val isNextEnabled: Boolean = true
)

class PostRequestViewModel(
    private val repository: LearnLoopRepository
) : ViewModel() {
    private val step = MutableStateFlow(0)
    private val selectedSubject = MutableStateFlow("")
    private val topic = MutableStateFlow("")
    private val description = MutableStateFlow("")
    private val urgency = MutableStateFlow("MEDIUM")
    private val duration = MutableStateFlow(30)
    private val language = MutableStateFlow("English")
    private val isLanguageMenuExpanded = MutableStateFlow(false)
    private val creditCost = MutableStateFlow(1)
    private val matchedTutors = MutableStateFlow<List<User>>(emptyList())

    init {
        viewModelScope.launch {
            combine(duration, urgency) { mins, level ->
                repository.estimateCreditCost(mins, level)
            }.collect { cost ->
                creditCost.value = cost
            }
        }
        viewModelScope.launch {
            matchedTutors.value = repository.matchedTutors()
        }
    }

    val uiState: StateFlow<PostRequestUiState> = combine(
        step,
        selectedSubject,
        topic,
        description,
        urgency,
        duration,
        language,
        isLanguageMenuExpanded,
        repository.subjects,
        repository.languages,
        creditCost,
        repository.currentUser,
        matchedTutors
    ) { values ->
        val stepValue = values[0] as Int
        val subjectValue = values[1] as String
        val topicValue = values[2] as String
        val descriptionValue = values[3] as String
        val urgencyValue = values[4] as String
        val durationValue = values[5] as Int
        val languageValue = values[6] as String
        val menuExpandedValue = values[7] as Boolean
        val subjectsValue = values[8] as List<String>
        val languagesValue = values[9] as List<String>
        val costValue = values[10] as Int
        val userValue = values[11] as com.example.learnloop.data.models.User
        val tutorsValue = values[12] as List<User>
        val isNextEnabled = !(stepValue == 2 && userValue.knowledgeCredits < costValue)
        PostRequestUiState(
            step = stepValue,
            selectedSubject = subjectValue,
            topic = topicValue,
            description = descriptionValue,
            urgency = urgencyValue,
            duration = durationValue,
            language = languageValue,
            isLanguageMenuExpanded = menuExpandedValue,
            subjects = subjectsValue,
            languages = languagesValue,
            creditCost = costValue,
            userBalance = userValue.knowledgeCredits,
            matchedTutors = tutorsValue,
            isNextEnabled = isNextEnabled
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PostRequestUiState())

    fun onStepChange(value: Int) {
        step.value = value
    }

    fun onSubjectSelected(value: String) {
        selectedSubject.value = value
    }

    fun onTopicChange(value: String) {
        topic.value = value
    }

    fun onDescriptionChange(value: String) {
        description.value = value
    }

    fun onUrgencyChange(value: String) {
        urgency.value = value
    }

    fun onDurationChange(value: Int) {
        duration.value = value
    }

    fun onLanguageChange(value: String) {
        language.value = value
    }

    fun onLanguageMenuChange(expanded: Boolean) {
        isLanguageMenuExpanded.value = expanded
    }
}