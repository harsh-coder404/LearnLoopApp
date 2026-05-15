package com.example.learnloop.ui.screens.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnloop.data.models.HelpRequest
import com.example.learnloop.data.repository.LearnLoopRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BrowseRequestsUiState(
    val searchQuery: String = "",
    val selectedSubject: String = "All",
    val selectedUrgency: String = "All",
    val isRefreshing: Boolean = false,
    val subjects: List<String> = emptyList(),
    val urgencies: List<String> = emptyList(),
    val filteredRequests: List<HelpRequest> = emptyList()
)

class BrowseRequestsViewModel(
    repository: LearnLoopRepository
) : ViewModel() {
    private val searchQuery = MutableStateFlow("")
    private val selectedSubject = MutableStateFlow("All")
    private val selectedUrgency = MutableStateFlow("All")
    private val isRefreshing = MutableStateFlow(false)
    private val urgencies = listOf("All", "URGENT", "HIGH", "MEDIUM", "LOW")

    val uiState: StateFlow<BrowseRequestsUiState> = combine(
        repository.helpRequests,
        repository.subjects,
        searchQuery,
        selectedSubject,
        selectedUrgency,
        isRefreshing
    ) { values ->
        val requests = values[0] as List<HelpRequest>
        val subjects = values[1] as List<String>
        val query = values[2] as String
        val subject = values[3] as String
        val urgency = values[4] as String
        val refreshing = values[5] as Boolean
        val subjectFilters = listOf("All") + subjects.take(6)
        val filtered = requests.filter { req ->
            val matchSearch = query.isEmpty() || req.topic.contains(query, ignoreCase = true) || req.subject.contains(query, ignoreCase = true)
            val matchSubject = subject == "All" || req.subject == subject
            val matchUrgency = urgency == "All" || req.urgencyLevel == urgency
            matchSearch && matchSubject && matchUrgency && req.status == "OPEN"
        }
        BrowseRequestsUiState(
            searchQuery = query,
            selectedSubject = subject,
            selectedUrgency = urgency,
            isRefreshing = refreshing,
            subjects = subjectFilters,
            urgencies = urgencies,
            filteredRequests = filtered
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BrowseRequestsUiState())

    fun onSearchChange(value: String) {
        searchQuery.value = value
    }

    fun onSubjectSelected(value: String) {
        selectedSubject.value = value
    }

    fun onUrgencySelected(value: String) {
        selectedUrgency.value = value
    }

    fun refresh() {
        viewModelScope.launch {
            isRefreshing.update { true }
            delay(1000)
            isRefreshing.update { false }
        }
    }
}