package com.example.learnloop.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learnloop.data.models.HelpRequest
import com.example.learnloop.ui.theme.Accent
import com.example.learnloop.ui.theme.Background
import com.example.learnloop.ui.theme.Gold
import com.example.learnloop.ui.theme.Primary
import com.example.learnloop.ui.theme.Surface
import com.example.learnloop.ui.theme.TextPrimary
import com.example.learnloop.ui.theme.TextSecondary
import com.example.learnloop.ui.theme.UrgencyHigh
import com.example.learnloop.ui.theme.UrgencyLow
import com.example.learnloop.ui.theme.UrgencyMedium
import com.example.learnloop.ui.theme.UrgencyUrgent

@Composable
fun RequestCard(
    request: HelpRequest,
    showAcceptButton: Boolean = false,
    onAcceptClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SubjectChipSmall(request.subject)
                Spacer(Modifier.width(6.dp))
                UrgencyChipSmall(request.urgencyLevel)
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = request.topic,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = request.description,
                fontSize = 13.sp,
                color = TextSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.AccessTime,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(Modifier.width(3.dp))
                    Text("${request.sessionDuration} min", fontSize = 12.sp, color = TextSecondary)
                    Spacer(Modifier.width(10.dp))
                    Icon(
                        Icons.Filled.MonetizationOn,
                        contentDescription = null,
                        tint = Gold,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(Modifier.width(3.dp))
                    Text("${request.creditCost}", fontSize = 12.sp, color = Gold, fontWeight = FontWeight.Bold)
                }
                Text(
                    text = request.createdAt,
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AvatarInitialsSmall(request.postedBy.name)
                    Spacer(Modifier.width(6.dp))
                    Text(request.postedBy.name, fontSize = 12.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
                }
                if (showAcceptButton) {
                    Button(
                        onClick = onAcceptClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Accent),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text("Accept & Teach", fontSize = 12.sp, color = Color.White)
                    }
                } else {
                    StatusChipSmall(request.status)
                }
            }
        }
    }
}

@Composable
fun SubjectChipSmall(subject: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Accent.copy(alpha = 0.15f))
    ) {
        Text(
            text = subject,
            color = Accent,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}

@Composable
fun UrgencyChipSmall(urgency: String) {
    val (color, label) = when (urgency.uppercase()) {
        "URGENT" -> UrgencyUrgent to "URGENT"
        "HIGH" -> UrgencyHigh to "HIGH"
        "MEDIUM" -> UrgencyMedium to "MEDIUM"
        else -> UrgencyLow to "LOW"
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color.copy(alpha = 0.12f))
            .border(1.dp, color.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
    ) {
        Text(
            text = label,
            color = color,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun StatusChipSmall(status: String) {
    val (bg, fg, label) = when (status.uppercase()) {
        "ACTIVE", "IN_PROGRESS" -> Triple(Color(0xFF48BB78).copy(alpha = 0.15f), Color(0xFF38A169), "Active")
        "SCHEDULED", "MATCHED" -> Triple(Color(0xFF3182CE).copy(alpha = 0.15f), Color(0xFF2B6CB0), "Matched")
        "COMPLETED" -> Triple(Color(0xFF718096).copy(alpha = 0.15f), Color(0xFF4A5568), "Done")
        "CANCELLED" -> Triple(Color(0xFFE53E3E).copy(alpha = 0.15f), Color(0xFFE53E3E), "Cancelled")
        "OPEN" -> Triple(Accent.copy(alpha = 0.15f), Accent, "Open")
        else -> Triple(Background, TextSecondary, status)
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bg)
    ) {
        Text(
            text = label,
            color = fg,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}

@Composable
fun AvatarInitialsSmall(name: String, size: Int = 28) {
    val initials = name.split(" ").take(2).joinToString("") { it.firstOrNull()?.uppercase() ?: "" }
    val colorList = listOf(
        Color(0xFF4A90D9), Color(0xFF7B68EE), Color(0xFF48BB78),
        Color(0xFFED8936), Color(0xFFE53E3E), Color(0xFF00B4A0)
    )
    val bg = colorList[name.hashCode().and(0x7FFFFFFF) % colorList.size]
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(size.dp).clip(CircleShape).background(bg)
    ) {
        Text(initials, color = Color.White, fontSize = (size / 2.5).sp, fontWeight = FontWeight.Bold)
    }
}
