package com.example.learnloop.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.learnloop.ui.navigation.Screen
import com.example.learnloop.ui.theme.Accent
import com.example.learnloop.ui.theme.Background
import com.example.learnloop.ui.theme.Gold
import com.example.learnloop.ui.theme.Primary
import com.example.learnloop.ui.theme.Surface
import com.example.learnloop.ui.theme.TextPrimary
import com.example.learnloop.ui.theme.TextSecondary

@Composable
fun SessionSummaryScreen(navController: NavController, sessionId: String) {
    var rating by remember { mutableIntStateOf(0) }
    var comment by remember { mutableStateOf("") }
    val isTutor = true

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Brush.verticalGradient(listOf(Primary, Color(0xFF2D5A8E)))),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier.size(64.dp).clip(RoundedCornerShape(32.dp)).background(Accent.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Check, null, tint = Accent, modifier = Modifier.size(36.dp))
                }
                Spacer(Modifier.height(10.dp))
                Text("Session Complete! 🎉", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("45 minutes  ·  BST Deletion", fontSize = 13.sp, color = Color.White.copy(alpha = 0.8f))
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            CreditsEarnedCard(isTutor = isTutor, amount = 3)

            AiSummaryCard()

            RatingCard(rating = rating, comment = comment, onRatingChange = { rating = it }, onCommentChange = { comment = it })

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Primary)
                ) {
                    Icon(Icons.Filled.Share, null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Share Summary", fontWeight = FontWeight.SemiBold)
                }
                Button(
                    onClick = { navController.navigate(Screen.Home.route) { popUpTo(0) } },
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text("Back to Home", fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun CreditsEarnedCard(isTutor: Boolean, amount: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = if (isTutor) Color(0xFFF0FDF4) else Color(0xFFFFF5EB)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Filled.MonetizationOn, null, tint = Gold, modifier = Modifier.size(32.dp))
            Spacer(Modifier.width(10.dp))
            Column {
                Text(
                    text = if (isTutor) "+$amount credits earned!" else "$amount credits used",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isTutor) Color(0xFF38A169) else Color(0xFFED8936)
                )
                Text(
                    text = if (isTutor) "Great work! Credits added to your wallet." else "Credits transferred to your tutor.",
                    fontSize = 13.sp,
                    color = TextSecondary
                )
            }
        }
    }
}

@Composable
private fun AiSummaryCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("✨", fontSize = 20.sp)
                Spacer(Modifier.width(8.dp))
                Text("AI Session Summary", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            }
            Spacer(Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(Modifier.height(12.dp))

            SummarySection(
                title = "Key Concepts Covered",
                items = listOf(
                    "Binary Search Tree deletion algorithm",
                    "In-order successor concept",
                    "Three cases: no child, one child, two children",
                    "Time complexity: O(h) where h = height"
                )
            )
            Spacer(Modifier.height(12.dp))
            SummarySection(
                title = "Code / Notes Shared",
                items = listOf(
                    "BST delete() pseudocode walkthrough",
                    "findMin() helper function",
                    "Recursive deletion implementation"
                )
            )
            Spacer(Modifier.height(12.dp))
            SummarySection(
                title = "3 Takeaways",
                items = listOf(
                    "Always find the in-order successor (smallest in right subtree) for two-child deletion.",
                    "The in-order successor has at most one right child, simplifying its own deletion.",
                    "For balanced BSTs: O(log n), but O(n) worst-case for skewed trees."
                ),
                numbered = true
            )
        }
    }
}

@Composable
private fun SummarySection(title: String, items: List<String>, numbered: Boolean = false) {
    Text(title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Primary)
    Spacer(Modifier.height(6.dp))
    items.forEachIndexed { index, item ->
        Row(modifier = Modifier.padding(vertical = 2.dp)) {
            Text(
                text = if (numbered) "${index + 1}." else "•",
                fontSize = 13.sp, color = Accent, fontWeight = FontWeight.Bold,
                modifier = Modifier.width(16.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(item, fontSize = 13.sp, color = TextPrimary)
        }
    }
}

@Composable
private fun RatingCard(rating: Int, comment: String, onRatingChange: (Int) -> Unit, onCommentChange: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Rate this Session", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Spacer(Modifier.height(6.dp))
            Text("How was your experience?", fontSize = 13.sp, color = TextSecondary)
            Spacer(Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                (1..5).forEach { star ->
                    Icon(
                        imageVector = if (star <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = "Rate $star",
                        tint = if (star <= rating) Gold else Color(0xFFCBD5E0),
                        modifier = Modifier
                            .size(40.dp)
                            .padding(2.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { onRatingChange(star) }
                    )
                }
            }
            if (rating > 0) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = when (rating) { 1 -> "Poor"; 2 -> "Fair"; 3 -> "Good"; 4 -> "Very Good"; else -> "Excellent! ⭐" },
                    fontSize = 13.sp, color = Gold, fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = comment,
                onValueChange = onCommentChange,
                placeholder = { Text("Leave a comment (optional)...", color = TextSecondary) },
                modifier = Modifier.fillMaxWidth().height(90.dp),
                shape = RoundedCornerShape(10.dp),
                maxLines = 4,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Primary, unfocusedBorderColor = Color(0xFFCBD5E0))
            )
        }
    }
}
