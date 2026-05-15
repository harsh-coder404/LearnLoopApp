package com.example.learnloop.ui.screens.sessions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learnloop.ui.components.SessionCard
import com.example.learnloop.ui.navigation.Screen
import com.example.learnloop.ui.theme.Accent
import com.example.learnloop.ui.theme.Background
import com.example.learnloop.ui.theme.Primary
import com.example.learnloop.ui.theme.TextPrimary
import com.example.learnloop.ui.theme.TextSecondary
import com.example.learnloop.ui.viewmodel.LearnLoopViewModelFactory
import com.example.learnloop.ui.screens.sessions.MySessionsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySessionsScreen(
    navController: NavController,
    viewModel: MySessionsViewModel = viewModel(factory = LearnLoopViewModelFactory())
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().background(Background)) {
        TopAppBar(
            title = { Text("My Sessions", fontWeight = FontWeight.Bold, color = Color.White) },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary)
        )

        ScrollableTabRow(
            selectedTabIndex = uiState.selectedTab,
            containerColor = Primary,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[uiState.selectedTab]),
                    color = Accent
                )
            },
            edgePadding = 0.dp
        ) {
            uiState.tabs.forEachIndexed { index, title ->
                val count = when (index) { 0 -> uiState.upcoming.size; 1 -> uiState.active.size; else -> uiState.completed.size }
                Tab(
                    selected = uiState.selectedTab == index,
                    onClick = { viewModel.onTabSelected(index) },
                    text = {
                        Text(
                            text = "$title${if (count > 0) " ($count)" else ""}",
                            fontWeight = if (uiState.selectedTab == index) FontWeight.SemiBold else FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(alpha = 0.6f)
                )
            }
        }

        if (uiState.sessionsToShow.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = when (uiState.selectedTab) { 0 -> ""; 1 -> ""; else -> "" },
                        fontSize = 52.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = when (uiState.selectedTab) {
                            0 -> "No upcoming sessions"
                            1 -> "No active sessions right now"
                            else -> "No completed sessions yet"
                        },
                        fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = when (uiState.selectedTab) {
                            0 -> "Post a request to get matched!"
                            1 -> "Join or start a session to see it here."
                            else -> "Complete sessions to see them here."
                        },
                        fontSize = 14.sp, color = TextSecondary, textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 40.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.sessionsToShow) { session ->
                    SessionCard(
                        session = session,
                        onViewClick = {
                            if (session.status == "ACTIVE" || session.status == "SCHEDULED") {
                                navController.navigate(Screen.SessionRoom.createRoute(session.id))
                            } else {
                                navController.navigate(Screen.SessionSummary.createRoute(session.id))
                            }
                        }
                    )
                }
            }
        }
    }
}


