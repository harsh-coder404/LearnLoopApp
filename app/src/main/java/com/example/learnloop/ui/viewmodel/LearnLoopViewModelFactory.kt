package com.example.learnloop.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.learnloop.data.repository.LearnLoopRepository
import com.example.learnloop.di.LearnLoopAppContainer
import com.example.learnloop.ui.screens.login.LoginViewModel
import com.example.learnloop.ui.screens.splash.SplashViewModel
import com.example.learnloop.ui.screens.register.RegisterViewModel
import com.example.learnloop.ui.screens.otp.OtpViewModel
import com.example.learnloop.ui.screens.home.HomeViewModel
import com.example.learnloop.ui.screens.browse.BrowseRequestsViewModel
import com.example.learnloop.ui.screens.postrequest.PostRequestViewModel
import com.example.learnloop.ui.screens.sessions.MySessionsViewModel
import com.example.learnloop.ui.screens.leaderboard.LeaderboardViewModel
import com.example.learnloop.ui.screens.profile.ProfileViewModel
import com.example.learnloop.ui.screens.creditwallet.CreditWalletViewModel
import com.example.learnloop.ui.screens.notifications.NotificationsViewModel
import com.example.learnloop.ui.screens.sessionroom.SessionRoomViewModel
import com.example.learnloop.ui.screens.sessionsummary.SessionSummaryViewModel
import com.example.learnloop.ui.screens.editprofile.EditProfileViewModel

class LearnLoopViewModelFactory(
    private val repository: LearnLoopRepository = LearnLoopAppContainer.repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel() as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(repository) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(repository) as T
            modelClass.isAssignableFrom(OtpViewModel::class.java) -> OtpViewModel() as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository) as T
            modelClass.isAssignableFrom(BrowseRequestsViewModel::class.java) -> BrowseRequestsViewModel(repository) as T
            modelClass.isAssignableFrom(MySessionsViewModel::class.java) -> MySessionsViewModel(repository) as T
            modelClass.isAssignableFrom(LeaderboardViewModel::class.java) -> LeaderboardViewModel(repository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(repository) as T
            modelClass.isAssignableFrom(CreditWalletViewModel::class.java) -> CreditWalletViewModel(repository) as T
            modelClass.isAssignableFrom(NotificationsViewModel::class.java) -> NotificationsViewModel(repository) as T
            modelClass.isAssignableFrom(PostRequestViewModel::class.java) -> PostRequestViewModel(repository) as T
            modelClass.isAssignableFrom(SessionRoomViewModel::class.java) -> SessionRoomViewModel(repository) as T
            modelClass.isAssignableFrom(SessionSummaryViewModel::class.java) -> SessionSummaryViewModel(repository) as T
            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> EditProfileViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}





