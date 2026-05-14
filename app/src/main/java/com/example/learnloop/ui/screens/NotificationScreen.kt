package com.example.learnloop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.learnloop.data.models.DummyData
import com.example.learnloop.data.models.Notification
import com.example.learnloop.ui.theme.Accent
import com.example.learnloop.ui.theme.Background
import com.example.learnloop.ui.theme.Gold
import com.example.learnloop.ui.theme.Primary
import com.example.learnloop.ui.theme.Surface
import com.example.learnloop.ui.theme.SuccessColor
import com.example.learnloop.ui.theme.TextPrimary
import com.example.learnloop.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(navController: NavController) {
    val notificationsList = remember { mutableStateOf(DummyData.notifications) }
    val unreadCount = notificationsList.value.count { !it.isRead }

    Column(modifier = Modifier.fillMaxSize().background(Background)) {
        TopAppBar(
            title = { Text("Notifications", fontWeight = FontWeight.Bold, color = Color.White) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
                }
            },
            actions = {
                if (unreadCount > 0) {
                    TextButton(onClick = {
                        notificationsList.value = notificationsList.value.map { it.copy(isRead = true) }
                    }) {
                        Text("Mark all read", fontSize = 13.sp, color = Accent)
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary)
        )

        if (notificationsList.value.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("✓", fontSize = 52.sp, color = SuccessColor)
                    Spacer(Modifier.height(12.dp))
                    Text("You're all caught up!", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                    Spacer(Modifier.height(8.dp))
                    Text("No new notifications", fontSize = 14.sp, color = TextSecondary)
                }
            }
        } else {
            if (unreadCount > 0) {
                Text(
                    "$unreadCount unread",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            LazyColumn(contentPadding = PaddingValues(bottom = 16.dp)) {
                items(notificationsList.value) { notification ->
                    NotificationItem(notification = notification)
                    HorizontalDivider(color = Color(0xFFF0F0F0))
                }
            }
        }
    }
}

@Composable
private fun NotificationItem(notification: Notification) {
    val (icon, iconBg) = notificationIconAndColor(notification.type)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (!notification.isRead) Accent.copy(alpha = 0.04f) else Surface)
            .then(
                if (!notification.isRead)
                    Modifier.border(
                        width = 0.dp,
                        color = Color.Transparent,
                        shape = RoundedCornerShape(0.dp)
                    )
                else Modifier
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.Top
    ) {
        if (!notification.isRead) {
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Accent)
                    .align(Alignment.CenterVertically)
            )
            Spacer(Modifier.width(10.dp))
        }

        Box(
            modifier = Modifier.size(40.dp).clip(CircleShape).background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = Color.White, modifier = Modifier.size(20.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = notification.message,
                fontSize = 14.sp,
                fontWeight = if (!notification.isRead) FontWeight.SemiBold else FontWeight.Normal,
                color = TextPrimary,
                lineHeight = 20.sp
            )
            Spacer(Modifier.height(3.dp))
            Text(notification.createdAt, fontSize = 12.sp, color = TextSecondary)
        }
        if (!notification.isRead) {
            Spacer(Modifier.width(8.dp))
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Accent).align(Alignment.CenterVertically))
        }
    }
}

private fun notificationIconAndColor(type: String): Pair<ImageVector, Color> {
    return when (type) {
        "MATCH_FOUND" -> Icons.Filled.People to Color(0xFF4A90D9)
        "SESSION_STARTING" -> Icons.Filled.Timer to Color(0xFF38A169)
        "CREDITS_EARNED" -> Icons.Filled.MonetizationOn to Color(0xFFF5A623)
        "BADGE_UNLOCKED" -> Icons.Filled.Celebration to Color(0xFF7B68EE)
        else -> Icons.Filled.Notifications to Color(0xFF718096)
    }
}
