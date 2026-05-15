package com.example.learnloop.ui.screens.leaderboard

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.learnloop.data.models.LeaderboardEntry
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learnloop.ui.components.AvatarInitialsSmall
import com.example.learnloop.ui.theme.Accent
import com.example.learnloop.ui.theme.Background
import com.example.learnloop.ui.theme.Bronze3rd
import com.example.learnloop.ui.theme.Gold
import com.example.learnloop.ui.theme.Gold1st
import com.example.learnloop.ui.theme.Primary
import com.example.learnloop.ui.theme.Silver2nd
import com.example.learnloop.ui.theme.Surface
import com.example.learnloop.ui.theme.TextPrimary
import com.example.learnloop.ui.theme.TextSecondary
import com.example.learnloop.ui.viewmodel.LearnLoopViewModelFactory
import com.example.learnloop.ui.screens.leaderboard.LeaderboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    navController: NavController,
    viewModel: LeaderboardViewModel = viewModel(factory = LearnLoopViewModelFactory())
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentUserEntry = uiState.currentUserEntry
    val entries = uiState.entries

    Column(modifier = Modifier.fillMaxSize().background(Background)) {
        TopAppBar(
            title = { Text("Leaderboard", fontWeight = FontWeight.Bold, color = Color.White) },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary)
        )

        TabRow(
            selectedTabIndex = uiState.selectedTab,
            containerColor = Primary,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[uiState.selectedTab]),
                    color = Accent
                )
            }
        ) {
            listOf("This Week", "All Time").forEachIndexed { index, title ->
                Tab(
                    selected = uiState.selectedTab == index,
                    onClick = { viewModel.onTabSelected(index) },
                    text = { Text(title, fontWeight = if (uiState.selectedTab == index) FontWeight.SemiBold else FontWeight.Normal) },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(alpha = 0.6f)
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            PullToRefreshBox(
                isRefreshing = uiState.isRefreshing,
                onRefresh = viewModel::refresh
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    item {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(uiState.subjects) { subj ->
                                val selected = uiState.selectedSubject == subj
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(if (selected) Primary else Surface)
                                        .border(1.dp, if (selected) Primary else Color(0xFFCBD5E0), RoundedCornerShape(20.dp))
                                        .clickable { viewModel.onSubjectSelected(subj) }
                                        .padding(horizontal = 14.dp, vertical = 7.dp)
                                ) {
                                    Text(subj, fontSize = 13.sp, color = if (selected) Color.White else TextSecondary,
                                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal)
                                }
                            }
                        }
                    }

                    item {
                        PodiumRow(entries = entries.take(3))
                    }

                    item {
                        Spacer(Modifier.height(8.dp))
                        Text("Rankings", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary, modifier = Modifier.padding(horizontal = 16.dp))
                        Spacer(Modifier.height(8.dp))
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    }

                    itemsIndexed(entries.drop(3)) { index, entry ->
                        LeaderboardRow(entry = entry, isCurrentUser = currentUserEntry?.user?.id == entry.user.id)
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF0F0F0))
                    }
                }
            }

            if (currentUserEntry != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .align(Alignment.BottomCenter),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Primary)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier.size(28.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("#${currentUserEntry.rank}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                            Spacer(Modifier.width(10.dp))
                            AvatarInitialsSmall(currentUserEntry.user.name, 32)
                            Spacer(Modifier.width(8.dp))
                            Column {
                                Text("You", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text("Your current rank", fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
                            }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.MonetizationOn, null, tint = Gold, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(3.dp))
                            Text("${currentUserEntry.creditsEarned}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Gold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PodiumRow(entries: List<LeaderboardEntry>) {
    val order = listOf(1, 0, 2)
    val heights = listOf(80.dp, 110.dp, 70.dp)
    val colors = listOf(Silver2nd, Gold1st, Bronze3rd)
    val medals = listOf("", "", "")

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        order.forEachIndexed { displayIndex, dataIndex ->
            if (dataIndex < entries.size) {
                val entry = entries[dataIndex]
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(medals[displayIndex], fontSize = 24.sp)
                    Spacer(Modifier.height(4.dp))
                    AvatarInitialsSmall(entry.user.name, 40)
                    Spacer(Modifier.height(4.dp))
                    Text(entry.user.name.split(" ").first(), fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.MonetizationOn, null, tint = Gold, modifier = Modifier.size(12.dp))
                        Text(" ${entry.creditsEarned}", fontSize = 12.sp, color = Gold, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(heights[displayIndex])
                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                            .background(
                                Brush.verticalGradient(
                                    listOf(colors[displayIndex].copy(alpha = 0.8f), colors[displayIndex])
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("#${entry.rank}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
private fun LeaderboardRow(entry: LeaderboardEntry, isCurrentUser: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isCurrentUser) Primary.copy(alpha = 0.06f) else Color.Transparent)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "#${entry.rank}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (isCurrentUser) Primary else TextSecondary,
            modifier = Modifier.width(34.dp)
        )
        AvatarInitialsSmall(entry.user.name, 36)
        Spacer(Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                entry.user.name + if (isCurrentUser) " (You)" else "",
                fontSize = 14.sp, fontWeight = FontWeight.SemiBold,
                color = if (isCurrentUser) Primary else TextPrimary
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Star, null, tint = Gold, modifier = Modifier.size(11.dp))
                Text(" ${"%.1f".format(entry.user.reputationScore)}  ·  ${entry.sessionsCount} sessions", fontSize = 11.sp, color = TextSecondary)
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.MonetizationOn, null, tint = Gold, modifier = Modifier.size(14.dp))
            Spacer(Modifier.width(2.dp))
            Text("${entry.creditsEarned}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Gold)
        }
    }
}


