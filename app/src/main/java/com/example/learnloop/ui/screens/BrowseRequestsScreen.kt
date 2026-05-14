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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.learnloop.data.models.DummyData
import com.example.learnloop.ui.components.RequestCard
import com.example.learnloop.ui.navigation.Screen
import com.example.learnloop.ui.theme.Accent
import com.example.learnloop.ui.theme.Background
import com.example.learnloop.ui.theme.Primary
import com.example.learnloop.ui.theme.Surface
import com.example.learnloop.ui.theme.TextPrimary
import com.example.learnloop.ui.theme.TextSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseRequestsScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedSubject by remember { mutableStateOf("All") }
    var selectedUrgency by remember { mutableStateOf("All") }
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val subjects = listOf("All") + DummyData.subjects.take(6)
    val urgencies = listOf("All", "URGENT", "HIGH", "MEDIUM", "LOW")

    val filtered = DummyData.helpRequests.filter { req ->
        val matchSearch = searchQuery.isEmpty() || req.topic.contains(searchQuery, ignoreCase = true) || req.subject.contains(searchQuery, ignoreCase = true)
        val matchSubject = selectedSubject == "All" || req.subject == selectedSubject
        val matchUrgency = selectedUrgency == "All" || req.urgencyLevel == selectedUrgency
        matchSearch && matchSubject && matchUrgency && req.status == "OPEN"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Browse Requests", fontWeight = FontWeight.Bold, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.PostRequest.route) },
                containerColor = Accent,
                contentColor = Color.White
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Filled.Add, null, modifier = Modifier.size(18.dp))
                    Text("Post Request", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        },
        containerColor = Background
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                scope.launch {
                    isRefreshing = true
                    delay(1000)
                    isRefreshing = false
                }
            },
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search topics, subjects...", color = TextSecondary) },
                            leadingIcon = { Icon(Icons.Filled.Search, null, tint = TextSecondary) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Primary,
                                unfocusedBorderColor = Color(0xFFCBD5E0),
                                unfocusedContainerColor = Surface,
                                focusedContainerColor = Surface
                            )
                        )

                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(subjects) { subj ->
                                FilterChip(subj, selectedSubject == subj) { selectedSubject = subj }
                            }
                        }

                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(urgencies) { urg ->
                                val color = when (urg) {
                                    "URGENT" -> Color(0xFFE53E3E)
                                    "HIGH" -> Color(0xFFDD6B20)
                                    "MEDIUM" -> Color(0xFF3182CE)
                                    "LOW" -> Color(0xFF718096)
                                    else -> Primary
                                }
                                FilterChip(urg, selectedUrgency == urg, activeColor = color) { selectedUrgency = urg }
                            }
                        }
                    }
                }

                if (filtered.isEmpty()) {
                    item { EmptyRequestsState() }
                } else {
                    items(filtered) { request ->
                        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                            RequestCard(
                                request = request,
                                showAcceptButton = true,
                                onAcceptClick = {}
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterChip(
    label: String,
    selected: Boolean,
    activeColor: Color = Primary,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (selected) activeColor else Surface)
            .border(1.dp, if (selected) activeColor else Color(0xFFCBD5E0), RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 7.dp)
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = if (selected) Color.White else TextSecondary,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Composable
private fun EmptyRequestsState() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("📭", fontSize = 56.sp)
        Spacer(Modifier.height(16.dp))
        Text("No open requests right now", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary, textAlign = TextAlign.Center)
        Spacer(Modifier.height(8.dp))
        Text("Be the first to ask for help!", fontSize = 14.sp, color = TextSecondary, textAlign = TextAlign.Center)
    }
}
