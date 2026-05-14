package com.example.learnloop.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Leaderboard
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.VideoCall
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.learnloop.ui.screens.BrowseRequestsScreen
import com.example.learnloop.ui.screens.CreditWalletScreen
import com.example.learnloop.ui.screens.EditProfileScreen
import com.example.learnloop.ui.screens.HomeScreen
import com.example.learnloop.ui.screens.LeaderboardScreen
import com.example.learnloop.ui.screens.LoginScreen
import com.example.learnloop.ui.screens.MySessionsScreen
import com.example.learnloop.ui.screens.NotificationScreen
import com.example.learnloop.ui.screens.OtpVerificationScreen
import com.example.learnloop.ui.screens.PostRequestScreen
import com.example.learnloop.ui.screens.ProfileScreen
import com.example.learnloop.ui.screens.RegisterScreen
import com.example.learnloop.ui.screens.SessionRoomScreen
import com.example.learnloop.ui.screens.SessionSummaryScreen
import com.example.learnloop.ui.screens.SplashScreen
import com.example.learnloop.ui.theme.Accent
import com.example.learnloop.ui.theme.Background
import com.example.learnloop.ui.theme.Primary
import com.example.learnloop.ui.theme.Surface
import com.example.learnloop.ui.theme.TextSecondary
import com.example.learnloop.ui.theme.TextPrimary

data class BottomNavItem(
    val label: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem("Home", Screen.Home.route, Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavItem("Requests", Screen.BrowseRequests.route, Icons.Filled.Search, Icons.Outlined.Search),
    BottomNavItem("Sessions", Screen.MySessions.route, Icons.Filled.VideoCall, Icons.Outlined.VideoCall),
    BottomNavItem("Leaderboard", Screen.Leaderboard.route, Icons.Filled.Leaderboard, Icons.Outlined.Leaderboard),
    BottomNavItem("Profile", Screen.Profile.route, Icons.Filled.Person, Icons.Outlined.Person)
)

@Composable
fun LearnLoopNavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in bottomNavRoutes

    Scaffold(
        containerColor = Background,
        bottomBar = {
            if (showBottomBar) {
                LearnLoopBottomNav(navController = navController, currentRoute = currentRoute)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(navController)
            }
            composable(Screen.Login.route) {
                LoginScreen(navController)
            }
            composable(Screen.Register.route) {
                RegisterScreen(navController)
            }
            composable(
                route = Screen.Otp.route,
                arguments = listOf(navArgument("email") { type = NavType.StringType })
            ) { backStackEntry ->
                val email = backStackEntry.arguments?.getString("email") ?: ""
                OtpVerificationScreen(navController, email)
            }
            composable(Screen.Home.route) {
                HomeScreen(navController)
            }
            composable(Screen.BrowseRequests.route) {
                BrowseRequestsScreen(navController)
            }
            composable(Screen.MySessions.route) {
                MySessionsScreen(navController)
            }
            composable(Screen.Leaderboard.route) {
                LeaderboardScreen(navController)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController)
            }
            composable(Screen.PostRequest.route) {
                PostRequestScreen(navController)
            }
            composable(
                route = Screen.SessionRoom.route,
                arguments = listOf(navArgument("sessionId") { type = NavType.StringType })
            ) { backStackEntry ->
                val sessionId = backStackEntry.arguments?.getString("sessionId") ?: ""
                SessionRoomScreen(navController, sessionId)
            }
            composable(
                route = Screen.SessionSummary.route,
                arguments = listOf(navArgument("sessionId") { type = NavType.StringType })
            ) { backStackEntry ->
                val sessionId = backStackEntry.arguments?.getString("sessionId") ?: ""
                SessionSummaryScreen(navController, sessionId)
            }
            composable(Screen.CreditWallet.route) {
                CreditWalletScreen(navController)
            }
            composable(Screen.Notifications.route) {
                NotificationScreen(navController)
            }
            composable(Screen.EditProfile.route) {
                EditProfileScreen(navController)
            }
        }
    }
}

@Composable
fun LearnLoopBottomNav(navController: NavController, currentRoute: String?) {
    NavigationBar(
        containerColor = Surface,
        tonalElevation = 4.dp
    ) {
        bottomNavItems.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 11.sp,
                        fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Primary,
                    selectedTextColor = Primary,
                    indicatorColor = Color(0xFFE8F0F8),
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary
                )
            )
        }
    }
}
