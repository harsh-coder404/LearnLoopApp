package com.example.learnloop.ui.screens

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.learnloop.data.models.DummyData
import com.example.learnloop.ui.components.SessionCard
import com.example.learnloop.ui.navigation.Screen
import com.example.learnloop.ui.theme.Accent
import com.example.learnloop.ui.theme.Background
import com.example.learnloop.ui.theme.Primary
import com.example.learnloop.ui.theme.TextPrimary
import com.example.learnloop.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySessionsScreen(navController: NavController) {
    var selectedTab by remember { mutableIntStateOf(1) }
    val tabs = listOf("Upcoming", "Active", "Completed")

    val upcoming = DummyData.allSessions.filter { it.status == "SCHEDULED" }
    val active = DummyData.allSessions.filter { it.status == "ACTIVE" }
    val completed = DummyData.allSessions.filter { it.status == "COMPLETED" }

    val sessionsToShow = when (selectedTab) {
        0 -> upcoming
        1 -> active
        else -> completed
    }

    Column(modifier = Modifier.fillMaxSize().background(Background)) {
        TopAppBar(
            title = { Text("My Sessions", fontWeight = FontWeight.Bold, color = Color.White) },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary)
        )

        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = Primary,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = Accent
                )
            },
            edgePadding = 0.dp
        ) {
            tabs.forEachIndexed { index, title ->
                val count = when (index) { 0 -> upcoming.size; 1 -> active.size; else -> completed.size }
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = "$title${if (count > 0) " ($count)" else ""}",
                            fontWeight = if (selectedTab == index) FontWeight.SemiBold else FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(alpha = 0.6f)
                )
            }
        }

        if (sessionsToShow.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = when (selectedTab) { 0 -> "📅"; 1 -> "🎯"; else -> "📚" },
                        fontSize = 52.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = when (selectedTab) {
                            0 -> "No upcoming sessions"
                            1 -> "No active sessions right now"
                            else -> "No completed sessions yet"
                        },
                        fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = when (selectedTab) {
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
                items(sessionsToShow) { session ->
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
