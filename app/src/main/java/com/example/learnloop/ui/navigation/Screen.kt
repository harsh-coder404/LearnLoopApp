package com.example.learnloop.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Otp : Screen("otp/{email}") {
        fun createRoute(email: String) = "otp/$email"
    }
    object Home : Screen("home")
    object BrowseRequests : Screen("browse_requests")
    object MySessions : Screen("my_sessions")
    object Leaderboard : Screen("leaderboard")
    object Profile : Screen("profile")
    object PostRequest : Screen("post_request")
    object SessionRoom : Screen("session_room/{sessionId}") {
        fun createRoute(sessionId: String) = "session_room/$sessionId"
    }
    object SessionSummary : Screen("session_summary/{sessionId}") {
        fun createRoute(sessionId: String) = "session_summary/$sessionId"
    }
    object CreditWallet : Screen("credit_wallet")
    object Notifications : Screen("notifications")
    object EditProfile : Screen("edit_profile")
}

val bottomNavRoutes = setOf(
    Screen.Home.route,
    Screen.BrowseRequests.route,
    Screen.MySessions.route,
    Screen.Leaderboard.route,
    Screen.Profile.route
)
