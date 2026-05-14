package com.example.learnloop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.learnloop.data.models.DummyData
import com.example.learnloop.ui.components.ActiveSessionCardCompact
import com.example.learnloop.ui.components.RequestCard
import com.example.learnloop.ui.navigation.Screen
import com.example.learnloop.ui.theme.Accent
import com.example.learnloop.ui.theme.Background
import com.example.learnloop.ui.theme.Gold
import com.example.learnloop.ui.theme.Primary
import com.example.learnloop.ui.theme.Surface
import com.example.learnloop.ui.theme.TextPrimary
import com.example.learnloop.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val user = DummyData.currentUser
    val unreadCount = DummyData.notifications.count { !it.isRead }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Background),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.AutoStories, null, tint = Accent, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("LearnLoop", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)
                    }
                },
                actions = {
                    BadgedBox(
                        badge = {
                            if (unreadCount > 0) {
                                Badge(containerColor = Color(0xFFE53E3E)) {
                                    Text("$unreadCount", fontSize = 9.sp, color = Color.White)
                                }
                            }
                        }
                    ) {
                        IconButton(onClick = { navController.navigate(Screen.Notifications.route) }) {
                            Icon(Icons.Filled.Notifications, null, tint = Color.White)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary)
            )
        }

        item {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                if (user.teachingStreak > 0) {
                    StreakBanner(streak = user.teachingStreak)
                }

                CreditsCard(credits = user.knowledgeCredits, onViewHistory = { navController.navigate(Screen.CreditWallet.route) })

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ActionCard(
                        title = "Get Help",
                        emoji = "📚",
                        subtitle = "Post a request",
                        color = Primary,
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate(Screen.PostRequest.route) }
                    )
                    ActionCard(
                        title = "Teach & Earn",
                        emoji = "💡",
                        subtitle = "Browse requests",
                        color = Accent,
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate(Screen.BrowseRequests.route) }
                    )
                }
            }
        }

        item {
            SectionHeader(
                title = "Your Active Sessions",
                onSeeAll = { navController.navigate(Screen.MySessions.route) }
            )
        }

        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(DummyData.activeSessions) { session ->
                    ActiveSessionCardCompact(
                        session = session,
                        onJoinClick = { navController.navigate(Screen.SessionRoom.createRoute(session.id)) }
                    )
                }
                item {
                    if (DummyData.activeSessions.isEmpty()) {
                        EmptySessionsCard()
                    }
                }
            }
        }

        item {
            SectionHeader(
                title = "Open Requests",
                onSeeAll = { navController.navigate(Screen.BrowseRequests.route) }
            )
        }

        items(DummyData.helpRequests.take(5)) { request ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)) {
                RequestCard(
                    request = request,
                    showAcceptButton = true,
                    onAcceptClick = {}
                )
            }
        }
    }
}

@Composable
private fun StreakBanner(streak: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.horizontalGradient(listOf(Color(0xFFF6AD55), Color(0xFFED8936))))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("🔥", fontSize = 24.sp)
        Spacer(Modifier.width(8.dp))
        Column {
            Text("$streak Day Teaching Streak!", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text("Keep it up — teach today to extend your streak!", fontSize = 12.sp, color = Color.White.copy(alpha = 0.9f))
        }
    }
}

@Composable
private fun CreditsCard(credits: Int, onViewHistory: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, Gold.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Knowledge Credits", fontSize = 13.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.MonetizationOn, null, tint = Gold, modifier = Modifier.size(28.dp))
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "$credits",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Gold
                    )
                }
                Spacer(Modifier.height(2.dp))
                TextButton(onClick = onViewHistory, contentPadding = PaddingValues(0.dp)) {
                    Text("View History →", fontSize = 13.sp, color = Accent, fontWeight = FontWeight.Medium)
                }
            }
            Box(
                modifier = Modifier.size(60.dp).clip(CircleShape).background(Gold.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.MonetizationOn, null, tint = Gold, modifier = Modifier.size(32.dp))
            }
        }
    }
}

@Composable
private fun ActionCard(
    title: String,
    emoji: String,
    subtitle: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.height(110.dp).clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(emoji, fontSize = 28.sp)
            Spacer(Modifier.height(4.dp))
            Text(title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text(subtitle, fontSize = 12.sp, color = Color.White.copy(alpha = 0.85f))
        }
    }
}

@Composable
private fun SectionHeader(title: String, onSeeAll: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        TextButton(onClick = onSeeAll) {
            Text("See all", fontSize = 13.sp, color = Accent)
        }
    }
}

@Composable
private fun EmptySessionsCard() {
    Card(
        modifier = Modifier.width(200.dp).height(100.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("📭", fontSize = 24.sp)
            Spacer(Modifier.height(4.dp))
            Text("No active sessions", fontSize = 12.sp, color = TextSecondary, textAlign = TextAlign.Center)
        }
    }
}
