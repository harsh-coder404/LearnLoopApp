package com.example.learnloop.ui.screens.creditwallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnloop.data.models.CreditTransaction
import com.example.learnloop.data.models.User
import com.example.learnloop.data.repository.LearnLoopRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class CreditWalletUiState(
    val user: User = User(),
    val transactions: List<CreditTransaction> = emptyList()
)

class CreditWalletViewModel(
    repository: LearnLoopRepository
) : ViewModel() {
    val uiState: StateFlow<CreditWalletUiState> = combine(
        repository.currentUser,
        repository.creditTransactions
    ) { user, transactions ->
        CreditWalletUiState(user = user, transactions = transactions)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CreditWalletUiState())
}


