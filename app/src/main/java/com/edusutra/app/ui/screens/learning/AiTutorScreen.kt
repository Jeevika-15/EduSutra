
package com.edusutra.app.ui.screens.learning

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

private val TutorBlue = Color(0xFF52669A)

private data class ChatMessage(
    val text: String,
    val isUser: Boolean
)

@Composable
fun AiTutorScreen() {

    var message by remember { mutableStateOf("") }

    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                "Hi! I'm your Edu Sutra learning assistant. What would you like to learn today?",
                false
            )
        )
    }

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    fun sendMessage(question: String) {
        val trimmedQuestion = question.trim()

        if (trimmedQuestion.isBlank()) return

        messages.add(ChatMessage(trimmedQuestion, true))

        val response = when {
            trimmedQuestion.contains("machine learning", true) ->
                "Machine Learning is a branch of Artificial Intelligence that allows computers to learn patterns from data and make predictions. Its main types include supervised, unsupervised and reinforcement learning."

            trimmedQuestion.contains("database", true) ||
                    trimmedQuestion.contains("dbms", true) ->
                "A DBMS is software used to store, manage and retrieve data. Examples include MySQL, Oracle and PostgreSQL. Important concepts include tables, keys, relationships and normalization."

            trimmedQuestion.contains("programming", true) ->
                "Programming involves writing instructions that a computer can execute. Start with variables, data types, conditions, loops and functions before moving to advanced concepts."

            trimmedQuestion.contains("java", true) ->
                "Java is an object-oriented programming language. Important concepts include classes, objects, inheritance, polymorphism, encapsulation and abstraction."

            trimmedQuestion.contains("study", true) ->
                "Try studying in focused 25-minute sessions followed by a 5-minute break. Break large topics into smaller concepts and test yourself after each topic."

            else ->
                "Great question! Try breaking this topic into smaller concepts. Start by understanding the definition, learn its key components, and then practise with examples. You can also ask me about Machine Learning, Java or DBMS."
        }

        messages.add(ChatMessage(response, false))
        message = ""

        scope.launch {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(TutorBlue)
                .padding(20.dp)
        ) {
            Text(
                "Edu Sutra AI Tutor",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                "Your personal learning assistant",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.85f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "● Online • Demo mode",
                style = MaterialTheme.typography.labelMedium,
                color = Color.White
            )
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(messages) { chat ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (chat.isUser)
                        Arrangement.End else Arrangement.Start
                ) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (chat.isUser)
                            TutorBlue
                        else
                            MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.widthIn(max = 300.dp)
                    ) {
                        Text(
                            text = chat.text,
                            modifier = Modifier.padding(14.dp),
                            color = if (chat.isUser)
                                Color.White
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                "Suggested questions",
                style = MaterialTheme.typography.labelLarge
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("Explain ML", "Java basics", "DBMS").forEach { suggestion ->
                    SuggestionButton(
                        text = suggestion,
                        onClick = {
                            sendMessage(
                                when (suggestion) {
                                    "Explain ML" -> "Explain Machine Learning"
                                    "Java basics" -> "Explain Java basics"
                                    else -> "Explain DBMS"
                                }
                            )
                        }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Ask your tutor...") },
                    shape = RoundedCornerShape(24.dp),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { sendMessage(message) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TutorBlue
                    ),
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 14.dp
                    )
                ) {
                    Text("Send")
                }
            }
        }
    }
}

@Composable
private fun SuggestionButton(
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        contentPadding = PaddingValues(
            horizontal = 10.dp,
            vertical = 4.dp
        )
    ) {
        Text(
            text,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
