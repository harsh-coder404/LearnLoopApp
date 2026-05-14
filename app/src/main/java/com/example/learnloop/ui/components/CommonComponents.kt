package com.example.learnloop.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learnloop.ui.theme.Gold
import com.example.learnloop.ui.theme.Surface

@Composable
fun AvatarInitials(name: String, size: Dp = 40.dp, fontSize: Int = 16) {
    val initials = name.split(" ")
        .take(2)
        .joinToString("") { it.firstOrNull()?.uppercase() ?: "" }
    val colors = listOf(
        Color(0xFF4A90D9), Color(0xFF7B68EE), Color(0xFF48BB78),
        Color(0xFFED8936), Color(0xFFE53E3E), Color(0xFF00B4A0)
    )
    val color = colors[name.hashCode().and(0x7FFFFFFF) % colors.size]
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    ) {
        Text(
            text = initials,
            color = Color.White,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CreditBadge(amount: Int, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(Gold)
        ) {
            Text("⊙", fontSize = 8.sp, color = Color.White)
        }
        Spacer(Modifier.width(3.dp))
        Text(
            text = "$amount",
            color = Gold,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun UrgencyChip(urgency: String) {
    val (color, label) = when (urgency.uppercase()) {
        "URGENT" -> Color(0xFFE53E3E) to "URGENT"
        "HIGH" -> Color(0xFFDD6B20) to "HIGH"
        "MEDIUM" -> Color(0xFF3182CE) to "MEDIUM"
        else -> Color(0xFF718096) to "LOW"
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
fun SubjectChip(subject: String, modifier: Modifier = Modifier) {
    val accent = Color(0xFF00B4A0)
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(accent.copy(alpha = 0.15f))
    ) {
        Text(
            text = subject,
            color = accent,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}

@Composable
fun StatusChip(status: String) {
    val (bg, fg, label) = when (status.uppercase()) {
        "ACTIVE", "IN_PROGRESS" -> Triple(Color(0xFF48BB78).copy(alpha = 0.15f), Color(0xFF38A169), "Active")
        "SCHEDULED", "MATCHED" -> Triple(Color(0xFF3182CE).copy(alpha = 0.15f), Color(0xFF2B6CB0), "Scheduled")
        "COMPLETED" -> Triple(Color(0xFF718096).copy(alpha = 0.15f), Color(0xFF4A5568), "Completed")
        "CANCELLED" -> Triple(Color(0xFFE53E3E).copy(alpha = 0.15f), Color(0xFFE53E3E), "Cancelled")
        "OPEN" -> Triple(Color(0xFF00B4A0).copy(alpha = 0.15f), Color(0xFF00B4A0), "Open")
        else -> Triple(Color(0xFFEDF2F7), Color(0xFF718096), status)
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
fun ShimmerBox(modifier: Modifier = Modifier, cornerRadius: Dp = 8.dp) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val alpha by transition.animateFloat(
        initialValue = 0.15f,
        targetValue = 0.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(900),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer_alpha"
    )
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(Color(0xFFCBD5E0).copy(alpha = alpha))
    )
}

@Composable
fun ShimmerCard() {
    androidx.compose.foundation.layout.Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Surface)
            .padding(16.dp)
    ) {
        ShimmerBox(Modifier.fillMaxWidth().height(14.dp), 4.dp)
        Spacer(Modifier.height(8.dp))
        ShimmerBox(Modifier.fillMaxWidth(0.7f).height(12.dp), 4.dp)
        Spacer(Modifier.height(12.dp))
        ShimmerBox(Modifier.fillMaxWidth().height(12.dp), 4.dp)
        Spacer(Modifier.height(6.dp))
        ShimmerBox(Modifier.fillMaxWidth(0.85f).height(12.dp), 4.dp)
    }
}

