package com.example.learnloop.ui.screens.sessionroom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.learnloop.data.models.ChatMessage
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learnloop.ui.navigation.Screen
import com.example.learnloop.ui.theme.Accent
import com.example.learnloop.ui.theme.Background
import com.example.learnloop.ui.theme.ErrorColor
import com.example.learnloop.ui.theme.Primary
import com.example.learnloop.ui.theme.Surface
import com.example.learnloop.ui.theme.TextPrimary
import com.example.learnloop.ui.theme.TextSecondary
import com.example.learnloop.ui.viewmodel.LearnLoopViewModelFactory
import com.example.learnloop.ui.screens.sessionroom.SessionRoomViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionRoomScreen(
    navController: NavController,
    sessionId: String,
    viewModel: SessionRoomViewModel = viewModel(factory = LearnLoopViewModelFactory())
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    androidx.compose.runtime.LaunchedEffect(sessionId) {
        viewModel.setSessionId(sessionId)
    }

    val minutes = uiState.elapsedSeconds / 60
    val seconds = uiState.elapsedSeconds % 60
    val timerText = "%02d:%02d".format(minutes, seconds)

    if (uiState.showEndDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onToggleEndDialog(false) },
            title = { Text("End Session?", fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to end this session? A summary will be generated.") },
            confirmButton = {
                Button(
                    onClick = { navController.navigate(Screen.SessionSummary.createRoute(sessionId)) },
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorColor)
                ) { Text("End Session", color = Color.White) }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.onToggleEndDialog(false) }) { Text("Cancel") }
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize().background(Background)) {
        TopAppBar(
            title = {
                Column {
                    Text("Session with ${uiState.otherUserName}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("⏱ $timerText", fontSize = 12.sp, color = Accent)
                }
            },
            navigationIcon = {
                IconButton(onClick = { viewModel.onToggleEndDialog(true) }) {
                    Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
                }
            },
            actions = {
                Button(
                    onClick = { viewModel.onToggleEndDialog(true) },
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorColor),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(Icons.Filled.Close, null, modifier = Modifier.size(14.dp), tint = Color.White)
                    Spacer(Modifier.width(4.dp))
                    Text("End", fontSize = 13.sp, color = Color.White)
                }
            },
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
            listOf("Chat", "Whiteboard", "Notes").forEachIndexed { index, title ->
                Tab(
                    selected = uiState.selectedTab == index,
                    onClick = { viewModel.onTabSelected(index) },
                    text = { Text(title, fontWeight = if (uiState.selectedTab == index) FontWeight.SemiBold else FontWeight.Normal) },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(alpha = 0.6f)
                )
            }
        }

        when (uiState.selectedTab) {
            0 -> ChatTab(
                currentUserId = uiState.currentUserId,
                messages = uiState.messages,
                inputText = uiState.inputText,
                isCodeMode = uiState.isCodeMode,
                onInputChange = viewModel::onInputChange,
                onToggleCodeMode = viewModel::onToggleCodeMode,
                onSend = viewModel::onSendMessage
            )
            1 -> WhiteboardTab(content = uiState.whiteboardContent, onContentChange = viewModel::onWhiteboardChange)
            2 -> NotesTab(notes = uiState.notes, onNotesChange = viewModel::onNotesChange)
        }
    }
}

@Composable
private fun ChatTab(
    currentUserId: String,
    messages: List<ChatMessage>,
    inputText: String,
    isCodeMode: Boolean,
    onInputChange: (String) -> Unit,
    onToggleCodeMode: () -> Unit,
    onSend: () -> Unit
) {
    val listState = rememberLazyListState()

    androidx.compose.runtime.LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.size - 1)
    }

    Column(modifier = Modifier.fillMaxSize().imePadding()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                ChatBubble(message = message, isOwn = message.senderId == currentUserId)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Surface)
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = onInputChange,
                    placeholder = {
                        Text(if (isCodeMode) "Enter code snippet..." else "Type a message...", color = TextSecondary, fontSize = 14.sp)
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    maxLines = 4,
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontFamily = if (isCodeMode) FontFamily.Monospace else FontFamily.Default,
                        fontSize = 14.sp
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = if (isCodeMode) KeyboardCapitalization.None else KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Default
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (isCodeMode) Accent else Primary,
                        unfocusedBorderColor = Color(0xFFCBD5E0)
                    )
                )
                IconButton(
                    onClick = onToggleCodeMode,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        if (isCodeMode) Icons.Filled.TextFields else Icons.Filled.Code,
                        null,
                        tint = if (isCodeMode) Accent else TextSecondary
                    )
                }
                IconButton(
                    onClick = onSend,
                    modifier = Modifier.size(40.dp).clip(RoundedCornerShape(20.dp)).background(Primary)
                ) {
                    Icon(Icons.Filled.Send, null, tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
private fun ChatBubble(message: ChatMessage, isOwn: Boolean) {
    val isCode = message.type == "CODE"
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isOwn) Arrangement.End else Arrangement.Start
    ) {
        Column(
            horizontalAlignment = if (isOwn) Alignment.End else Alignment.Start,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            if (!isOwn) {
                Text(message.senderName, fontSize = 11.sp, color = TextSecondary, modifier = Modifier.padding(start = 4.dp, bottom = 2.dp))
            }
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp, topEnd = 16.dp,
                            bottomStart = if (isOwn) 16.dp else 4.dp,
                            bottomEnd = if (isOwn) 4.dp else 16.dp
                        )
                    )
                    .background(
                        when {
                            isCode -> Color(0xFF1A202C)
                            isOwn -> Primary
                            else -> Color(0xFFEDF2F7)
                        }
                    )
                    .padding(10.dp)
            ) {
                Text(
                    text = message.content,
                    color = if (isOwn || isCode) Color.White else TextPrimary,
                    fontSize = if (isCode) 12.sp else 14.sp,
                    fontFamily = if (isCode) FontFamily.Monospace else FontFamily.Default
                )
            }
            Text(message.timestamp, fontSize = 10.sp, color = TextSecondary, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
        }
    }
}

@Composable
private fun WhiteboardTab(content: String, onContentChange: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF1A202C)).padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Shared Whiteboard", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White.copy(alpha = 0.7f))
            IconButton(onClick = { onContentChange("") }) {
                Icon(Icons.Filled.Delete, null, tint = Color(0xFFE53E3E))
            }
        }
        OutlinedTextField(
            value = content,
            onValueChange = onContentChange,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .border(1.dp, Accent.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp,
                color = Color.White
            ),
            placeholder = {
                Text(
                    "Start writing here... Both users can type simultaneously.",
                    color = Color.White.copy(alpha = 0.3f),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 13.sp
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Accent,
                unfocusedBorderColor = Accent.copy(alpha = 0.3f),
                cursorColor = Accent,
                focusedContainerColor = Color(0xFF2D3748),
                unfocusedContainerColor = Color(0xFF2D3748)
            ),
            shape = RoundedCornerShape(8.dp)
        )
    }
}

@Composable
private fun NotesTab(notes: String, onNotesChange: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(Background).padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Private Notes", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Icon(Icons.Filled.Save, null, modifier = Modifier.size(14.dp), tint = Color.White)
                Spacer(Modifier.width(4.dp))
                Text("Save to Session", fontSize = 12.sp, color = Color.White)
            }
        }
        Text("These notes are private to you. Tap 'Save to Session' to share them.", fontSize = 12.sp, color = TextSecondary, modifier = Modifier.padding(bottom = 10.dp))
        OutlinedTextField(
            value = notes,
            onValueChange = onNotesChange,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f),
            placeholder = { Text("Write your notes here...", color = TextSecondary) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Primary,
                unfocusedBorderColor = Color(0xFFCBD5E0),
                focusedContainerColor = Surface,
                unfocusedContainerColor = Surface
            ),
            shape = RoundedCornerShape(12.dp)
        )
    }
}


