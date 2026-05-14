package com.example.learnloop.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.learnloop.data.models.CreditTransaction
import com.example.learnloop.data.models.DummyData
import com.example.learnloop.ui.theme.Accent
import com.example.learnloop.ui.theme.Background
import com.example.learnloop.ui.theme.ErrorColor
import com.example.learnloop.ui.theme.Gold
import com.example.learnloop.ui.theme.Primary
import com.example.learnloop.ui.theme.SuccessColor
import com.example.learnloop.ui.theme.Surface
import com.example.learnloop.ui.theme.TextPrimary
import com.example.learnloop.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditWalletScreen(navController: NavController) {
    val user = DummyData.currentUser
    val transactions = DummyData.creditTransactions
    var howItWorksExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Background),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            TopAppBar(
                title = { Text("Credit Wallet", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary)
            )
        }

        item {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(Primary, Color(0xFF2D5A8E))))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Current Balance", fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.MonetizationOn, null, tint = Gold, modifier = Modifier.size(40.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("${user.knowledgeCredits}", fontSize = 52.sp, fontWeight = FontWeight.Bold, color = Gold)
                    }
                    Text("credits", fontSize = 16.sp, color = Color.White.copy(alpha = 0.7f))
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Surface),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().clickable { howItWorksExpanded = !howItWorksExpanded }.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("💡 How Credits Work", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                        Icon(
                            if (howItWorksExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            null, tint = TextSecondary
                        )
                    }
                    AnimatedVisibility(visible = howItWorksExpanded) {
                        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                            HowItWorksItem("🎁 New accounts start with 20 free credits")
                            HowItWorksItem("📚 Spend credits to get help from tutors")
                            HowItWorksItem("💰 Tutors earn 90% of what learners spend")
                            HowItWorksItem("⚡ Urgent requests cost more credits")
                            HowItWorksItem("🏅 Earn bonus credits for top performance")
                            Spacer(Modifier.height(8.dp))
                            Box(
                                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(Accent.copy(alpha = 0.08f)).padding(12.dp)
                            ) {
                                Text(
                                    "Formula: (Duration/30) × Urgency Multiplier\nLOW=1×  MEDIUM=1.5×  HIGH=2×  URGENT=3×",
                                    fontSize = 12.sp, color = TextSecondary, lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Transaction History", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text("${transactions.size} transactions", fontSize = 12.sp, color = TextSecondary)
            }
        }

        var runningBalance = user.knowledgeCredits
        items(transactions) { tx ->
            TransactionRow(transaction = tx, runningBalance = runningBalance)
            val delta = if (tx.type == "SPENT") -tx.amount else tx.amount
            runningBalance -= delta
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF0F0F0))
        }
    }
}

@Composable
private fun HowItWorksItem(text: String) {
    Text(text, fontSize = 13.sp, color = TextSecondary, modifier = Modifier.padding(vertical = 3.dp))
}

@Composable
private fun TransactionRow(transaction: CreditTransaction, runningBalance: Int) {
    val isEarned = transaction.type == "EARNED"
    val isBonus = transaction.type == "BONUS"
    val isSpent = transaction.type == "SPENT"

    val (icon, color, sign) = when {
        isEarned -> Triple(Icons.Filled.ArrowUpward, SuccessColor, "+")
        isBonus -> Triple(Icons.Filled.Star, Gold, "+")
        else -> Triple(Icons.Filled.ArrowDownward, ErrorColor, "-")
    }

    Row(
        modifier = Modifier.fillMaxWidth().background(Surface).padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(38.dp).clip(CircleShape).background(color.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = color, modifier = Modifier.size(18.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(transaction.reason, fontSize = 13.sp, color = TextPrimary, fontWeight = FontWeight.Medium, maxLines = 1)
            Text(transaction.timestamp, fontSize = 11.sp, color = TextSecondary)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "$sign${transaction.amount}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text("$runningBalance cr", fontSize = 11.sp, color = TextSecondary)
        }
    }
}
