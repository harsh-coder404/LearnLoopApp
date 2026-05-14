package com.example.learnloop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.learnloop.data.models.Badge
import com.example.learnloop.data.models.DummyData
import com.example.learnloop.ui.components.AvatarInitialsSmall
import com.example.learnloop.ui.navigation.Screen
import com.example.learnloop.ui.theme.Accent
import com.example.learnloop.ui.theme.Background
import com.example.learnloop.ui.theme.Gold
import com.example.learnloop.ui.theme.Primary
import com.example.learnloop.ui.theme.Surface
import com.example.learnloop.ui.theme.TextPrimary
import com.example.learnloop.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val user = DummyData.currentUser
    var selectedBadge by remember { mutableStateOf<Badge?>(null) }
    val sheetState = rememberModalBottomSheetState()

    if (selectedBadge != null) {
        ModalBottomSheet(
            onDismissRequest = { selectedBadge = null },
            sheetState = sheetState
        ) {
            BadgeDetailSheet(badge = selectedBadge!!, onDismiss = { selectedBadge = null })
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Background),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Box(
                modifier = Modifier.fillMaxWidth().height(180.dp)
                    .background(Brush.verticalGradient(listOf(Primary, Color(0xFF2D5A8E))))
            ) {
                TopAppBar(
                    title = { Text("Profile", fontWeight = FontWeight.Bold, color = Color.White) },
                    actions = {
                        IconButton(onClick = { navController.navigate(Screen.EditProfile.route) }) {
                            Icon(Icons.Filled.Edit, null, tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
                Column(
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Box(
                            modifier = Modifier.size(72.dp).clip(CircleShape)
                                .border(3.dp, Color.White, CircleShape)
                        ) {
                            AvatarInitialsSmall(user.name, 72)
                        }
                        Box(
                            modifier = Modifier.size(22.dp).clip(CircleShape)
                                .background(Accent).border(2.dp, Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.Edit, null, tint = Color.White, modifier = Modifier.size(12.dp))
                        }
                    }
                }
            }
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(user.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text(user.institutionEmail, fontSize = 13.sp, color = TextSecondary)
                if (user.bio != null) {
                    Spacer(Modifier.height(6.dp))
                    Text(user.bio, fontSize = 13.sp, color = TextSecondary, textAlign = TextAlign.Center)
                }
                if (user.teachingStreak > 0) {
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🔥", fontSize = 18.sp)
                        Spacer(Modifier.width(4.dp))
                        Text("${user.teachingStreak} day teaching streak!", fontSize = 14.sp, color = Color(0xFFED8936), fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Surface),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                    StatItem(value = user.totalSessionsTaught.toString(), label = "Taught")
                    VerticalDivider()
                    StatItem(value = user.totalSessionsLearned.toString(), label = "Learned")
                    VerticalDivider()
                    StatItem(value = "${"%.1f".format(user.reputationScore)}★", label = "Avg Rating")
                }
            }
        }

        item { Spacer(Modifier.height(14.dp)) }

        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).border(1.5.dp, Gold.copy(alpha = 0.4f), RoundedCornerShape(14.dp)),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Surface),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.MonetizationOn, null, tint = Gold, modifier = Modifier.size(28.dp))
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text("${user.knowledgeCredits}", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Gold)
                            Text("Knowledge Credits", fontSize = 12.sp, color = TextSecondary)
                        }
                    }
                    TextButton(onClick = { navController.navigate(Screen.CreditWallet.route) }) {
                        Text("View History →", fontSize = 13.sp, color = Accent)
                    }
                }
            }
        }

        item { Spacer(Modifier.height(14.dp)) }

        item {
            ProfileSection("Expert In") {
                FlowRow(modifier = Modifier.padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    user.subjectExpertise.forEach { exp ->
                        Box(
                            modifier = Modifier.clip(RoundedCornerShape(20.dp)).background(Accent.copy(alpha = 0.15f))
                        ) {
                            Text("${exp.subject} · ${exp.level}", fontSize = 12.sp, color = Accent, fontWeight = FontWeight.Medium, modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
                        }
                    }
                }
            }
        }

        item { Spacer(Modifier.height(6.dp)) }

        item {
            ProfileSection("Languages") {
                FlowRow(modifier = Modifier.padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    user.languagesSpoken.forEach { lang ->
                        Box(
                            modifier = Modifier.clip(RoundedCornerShape(20.dp)).border(1.dp, Accent, RoundedCornerShape(20.dp))
                        ) {
                            Text(lang, fontSize = 12.sp, color = Accent, fontWeight = FontWeight.Medium, modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
                        }
                    }
                }
            }
        }

        item { Spacer(Modifier.height(6.dp)) }

        item {
            ProfileSection("Badges") {
                Box(modifier = Modifier.fillMaxWidth().height(200.dp).padding(horizontal = 16.dp)) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        userScrollEnabled = false
                    ) {
                        items(DummyData.badges) { badge ->
                            BadgeItem(badge = badge, onClick = { selectedBadge = badge })
                        }
                    }
                }
            }
        }

        item { Spacer(Modifier.height(14.dp)) }

        item {
            Button(
                onClick = { navController.navigate(Screen.EditProfile.route) },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary)
            ) {
                Icon(Icons.Filled.Edit, null, modifier = Modifier.size(16.dp), tint = Color.White)
                Spacer(Modifier.width(8.dp))
                Text("Edit Profile", fontWeight = FontWeight.SemiBold, color = Color.White)
            }
        }
    }
}

@Composable
private fun ProfileSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
        content()
    }
}

@Composable
private fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Text(label, fontSize = 12.sp, color = TextSecondary)
    }
}

@Composable
private fun VerticalDivider() {
    Box(modifier = Modifier.height(36.dp).width(1.dp).background(Color(0xFFE2E8F0)))
}

@Composable
private fun BadgeItem(badge: Badge, onClick: () -> Unit) {
    Card(
        modifier = Modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = if (badge.isEarned) Surface else Color(0xFFF7FAFC)),
        elevation = CardDefaults.cardElevation(if (badge.isEarned) 2.dp else 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp).graphicsLayer(alpha = if (badge.isEarned) 1f else 0.4f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(badge.icon, fontSize = 26.sp)
            Spacer(Modifier.height(4.dp))
            Text(badge.name, fontSize = 9.sp, fontWeight = FontWeight.Medium, color = TextPrimary, textAlign = TextAlign.Center, maxLines = 2)
            if (!badge.isEarned) {
                Icon(Icons.Filled.Lock, null, tint = TextSecondary, modifier = Modifier.size(12.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BadgeDetailSheet(badge: Badge, onDismiss: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(badge.icon, fontSize = 52.sp)
        Spacer(Modifier.height(12.dp))
        Text(badge.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(Modifier.height(8.dp))
        Text(badge.description, fontSize = 14.sp, color = TextSecondary, textAlign = TextAlign.Center)
        Spacer(Modifier.height(12.dp))
        Box(
            modifier = Modifier.clip(RoundedCornerShape(20.dp))
                .background(if (badge.isEarned) Color(0xFF38A169).copy(alpha = 0.12f) else Color(0xFFED8936).copy(alpha = 0.12f))
        ) {
            Text(
                badge.progress,
                fontSize = 13.sp,
                color = if (badge.isEarned) Color(0xFF38A169) else Color(0xFFED8936),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
            )
        }
        Spacer(Modifier.height(24.dp))
    }
}
