package com.example.learnloop.ui.screens.postrequest

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.learnloop.ui.components.AvatarInitialsSmall
import com.example.learnloop.ui.navigation.Screen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learnloop.ui.theme.Accent
import com.example.learnloop.ui.theme.Background
import com.example.learnloop.ui.theme.ErrorColor
import com.example.learnloop.ui.theme.Gold
import com.example.learnloop.ui.theme.Primary
import com.example.learnloop.ui.theme.Surface
import com.example.learnloop.ui.theme.TextPrimary
import com.example.learnloop.ui.theme.TextSecondary
import com.example.learnloop.ui.theme.UrgencyHigh
import com.example.learnloop.ui.theme.UrgencyLow
import com.example.learnloop.ui.theme.UrgencyMedium
import com.example.learnloop.ui.theme.UrgencyUrgent
import com.example.learnloop.ui.viewmodel.LearnLoopViewModelFactory
import com.example.learnloop.ui.screens.postrequest.PostRequestViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PostRequestScreen(
    navController: NavController,
    viewModel: PostRequestViewModel = viewModel(factory = LearnLoopViewModelFactory())
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().background(Background)) {
        TopAppBar(
            title = { Text("Post a Request", fontWeight = FontWeight.Bold, color = Color.White) },
            navigationIcon = {
                IconButton(onClick = { if (uiState.step == 0) navController.popBackStack() else viewModel.onStepChange(uiState.step - 1) }) {
                    Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary)
        )

        StepIndicator(currentStep = uiState.step, totalSteps = 4)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (uiState.step) {
                0 -> Step1SubjectTopic(
                    selectedSubject = uiState.selectedSubject,
                    topic = uiState.topic,
                    description = uiState.description,
                    subjects = uiState.subjects,
                    onSubjectSelected = viewModel::onSubjectSelected,
                    onTopicChange = viewModel::onTopicChange,
                    onDescriptionChange = viewModel::onDescriptionChange
                )
                1 -> Step2Preferences(
                    urgency = uiState.urgency,
                    duration = uiState.duration,
                    language = uiState.language,
                    languages = uiState.languages,
                    langDropdownExpanded = uiState.isLanguageMenuExpanded,
                    onUrgencyChange = viewModel::onUrgencyChange,
                    onDurationChange = viewModel::onDurationChange,
                    onLanguageChange = viewModel::onLanguageChange,
                    onLangDropdownChange = viewModel::onLanguageMenuChange
                )
                2 -> Step3CostPreview(
                    duration = uiState.duration,
                    urgency = uiState.urgency,
                    creditCost = uiState.creditCost,
                    userBalance = uiState.userBalance
                )
                3 -> Step4MatchedTutors(uiState.matchedTutors)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (uiState.step > 0) {
                OutlinedButton(
                    onClick = { viewModel.onStepChange(uiState.step - 1) },
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Primary)
                ) {
                    Text("Back", fontWeight = FontWeight.SemiBold)
                }
            }
            Button(
                onClick = {
                    if (uiState.step < 3) viewModel.onStepChange(uiState.step + 1)
                    else navController.navigate(Screen.Home.route) { popUpTo(Screen.Home.route) }
                },
                modifier = Modifier.weight(1f).height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (uiState.step == 3) Accent else Primary,
                    disabledContainerColor = Primary.copy(alpha = 0.4f)
                ),
                enabled = uiState.isNextEnabled
            ) {
                Text(
                    text = when (uiState.step) { 3 -> "Confirm & Post ✓"; else -> "Next" },
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                if (uiState.step < 3) {
                    Spacer(Modifier.width(6.dp))
                    Icon(Icons.Filled.ArrowForward, null, modifier = Modifier.size(16.dp), tint = Color.White)
                }
            }
        }
    }
}

@Composable
private fun StepIndicator(currentStep: Int, totalSteps: Int) {
    val stepLabels = listOf("Subject", "Preferences", "Cost", "Tutors")
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        stepLabels.forEachIndexed { index, label ->
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(32.dp).clip(CircleShape)
                        .background(if (index <= currentStep) Primary else Color(0xFFE2E8F0))
                ) {
                    if (index < currentStep) {
                        Icon(Icons.Filled.Check, null, tint = Color.White, modifier = Modifier.size(16.dp))
                    } else {
                        Text("${index + 1}", fontSize = 13.sp, color = if (index == currentStep) Color.White else TextSecondary, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(Modifier.height(4.dp))
                Text(label, fontSize = 10.sp, color = if (index <= currentStep) Primary else TextSecondary, fontWeight = if (index == currentStep) FontWeight.SemiBold else FontWeight.Normal)
            }
            if (index < totalSteps - 1) {
                Box(modifier = Modifier.weight(0.5f).height(2.dp).background(if (index < currentStep) Primary else Color(0xFFE2E8F0)))
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Step1SubjectTopic(
    selectedSubject: String,
    topic: String,
    description: String,
    subjects: List<String>,
    onSubjectSelected: (String) -> Unit, onTopicChange: (String) -> Unit, onDescriptionChange: (String) -> Unit
) {
    Text("Select Subject", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        subjects.forEach { subj ->
            val selected = selectedSubject == subj
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (selected) Accent else Surface)
                    .border(1.dp, if (selected) Accent else Color(0xFFCBD5E0), RoundedCornerShape(10.dp))
                    .clickable { onSubjectSelected(subj) }
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(subj, fontSize = 13.sp, color = if (selected) Color.White else TextPrimary, fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal)
            }
        }
    }
    OutlinedTextField(
        value = topic, onValueChange = onTopicChange,
        label = { Text("Topic / Question Title") },
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Primary, focusedLabelColor = Primary, cursorColor = Primary)
    )
    OutlinedTextField(
        value = description, onValueChange = onDescriptionChange,
        label = { Text("Description (what do you need help with?)") },
        modifier = Modifier.fillMaxWidth().height(100.dp), shape = RoundedCornerShape(12.dp),
        maxLines = 5,
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Primary, focusedLabelColor = Primary, cursorColor = Primary)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Step2Preferences(
    urgency: String, duration: Int, language: String,
    languages: List<String>,
    langDropdownExpanded: Boolean,
    onUrgencyChange: (String) -> Unit, onDurationChange: (Int) -> Unit,
    onLanguageChange: (String) -> Unit, onLangDropdownChange: (Boolean) -> Unit
) {
    Text("Urgency Level", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
    val urgencies = listOf(
        Triple("LOW", UrgencyLow, "×1.0 · 1 cr per 30min"),
        Triple("MEDIUM", UrgencyMedium, "×1.5 · 1.5 cr per 30min"),
        Triple("HIGH", UrgencyHigh, "×2.0 · 2 cr per 30min"),
        Triple("URGENT", UrgencyUrgent, "×3.0 · 3 cr per 30min")
    )
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        urgencies.forEach { (level, color, hint) ->
            Card(
                modifier = Modifier.weight(1f).clickable { onUrgencyChange(level) },
                shape = RoundedCornerShape(10.dp),
                border = if (urgency == level) androidx.compose.foundation.BorderStroke(2.dp, color) else null,
                colors = CardDefaults.cardColors(containerColor = if (urgency == level) color.copy(alpha = 0.1f) else Surface),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(level, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = color, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(3.dp))
                    Text(hint, fontSize = 9.sp, color = TextSecondary, textAlign = TextAlign.Center)
                }
            }
        }
    }

    Text("Session Duration", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        listOf(15, 30, 45, 60).forEach { mins ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f).height(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (duration == mins) Primary else Surface)
                    .border(1.dp, if (duration == mins) Primary else Color(0xFFCBD5E0), RoundedCornerShape(10.dp))
                    .clickable { onDurationChange(mins) }
            ) {
                Text("${mins}m", fontSize = 14.sp, color = if (duration == mins) Color.White else TextPrimary, fontWeight = FontWeight.SemiBold)
            }
        }
    }

    Text("Preferred Language", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
    ExposedDropdownMenuBox(expanded = langDropdownExpanded, onExpandedChange = onLangDropdownChange) {
        OutlinedTextField(
            value = language, onValueChange = {}, readOnly = true,
            label = { Text("Language") },
            leadingIcon = { Icon(Icons.Filled.Translate, null, tint = TextSecondary) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = langDropdownExpanded) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Primary, focusedLabelColor = Primary),
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = langDropdownExpanded, onDismissRequest = { onLangDropdownChange(false) }) {
            languages.forEach {
                DropdownMenuItem(text = { Text(it) }, onClick = { onLanguageChange(it); onLangDropdownChange(false) })
            }
        }
    }
}

@Composable
private fun Step3CostPreview(
    duration: Int, urgency: String,
    creditCost: Int, userBalance: Int
) {
    val insufficient = userBalance < creditCost
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text("Cost Preview", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Surface),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.MonetizationOn, null, tint = Gold, modifier = Modifier.size(40.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("$creditCost", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Gold)
                    Spacer(Modifier.width(4.dp))
                    Text("credits", fontSize = 18.sp, color = TextSecondary, modifier = Modifier.align(Alignment.Bottom).padding(bottom = 6.dp))
                }
                Spacer(Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(Modifier.height(12.dp))
                FormulaRow("Duration", "${duration}min")
                FormulaRow("Urgency", urgency)
                FormulaRow("Estimated Cost", "$creditCost credits")
                Spacer(Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Your balance:", fontSize = 14.sp, color = TextSecondary)
                    Text("$userBalance credits", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Gold)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("After posting:", fontSize = 14.sp, color = TextSecondary)
                    Text("${userBalance - creditCost} credits", fontSize = 14.sp, fontWeight = FontWeight.SemiBold,
                        color = if (insufficient) ErrorColor else Gold)
                }
                if (insufficient) {
                    Spacer(Modifier.height(8.dp))
                    Text("⚠ Insufficient credits. You need $creditCost but have $userBalance.", fontSize = 13.sp, color = ErrorColor, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
private fun FormulaRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontSize = 13.sp, color = TextSecondary)
        Text(value, fontSize = 13.sp, color = TextPrimary, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun Step4MatchedTutors(tutors: List<com.example.learnloop.data.models.User>) {
    Text("Matched Tutors", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
    Text("Top tutors matching your request:", fontSize = 13.sp, color = TextSecondary)
    Spacer(Modifier.height(4.dp))
    tutors.forEachIndexed { index, tutor ->
        TutorMatchCard(tutor = tutor, rank = index, sessions = 12 - index * 3, rating = 4.9f - index * 0.15f)
        Spacer(Modifier.height(10.dp))
    }
}

@Composable
private fun TutorMatchCard(tutor: com.example.learnloop.data.models.User, rank: Int, sessions: Int, rating: Float) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        border = if (rank == 0) androidx.compose.foundation.BorderStroke(2.dp, Gold) else null
    ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            AvatarInitialsSmall(tutor.name, 44)
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(tutor.name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    if (rank == 0) {
                        Spacer(Modifier.width(6.dp))
                        Box(Modifier.clip(RoundedCornerShape(4.dp)).background(Gold.copy(alpha = 0.15f))) {
                            Text(" Best Match", fontSize = 10.sp, color = Gold, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                }
                Spacer(Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Star, null, tint = Gold, modifier = Modifier.size(13.dp))
                    Text(" ${"%.1f".format(rating)}  ·  $sessions sessions  ·  ${tutor.languagesSpoken.firstOrNull() ?: "English"}", fontSize = 12.sp, color = TextSecondary)
                }
            }
        }
    }
}
