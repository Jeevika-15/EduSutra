package com.edusutra.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edusutra.app.data.local.entity.ChatMessage
import com.edusutra.app.data.repository.ChatMessageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AiTutorUiState(
    val messages: List<ChatMessage> = emptyList(),
    val courseContext: String = "Data Structures · Pro",
    val isLoading: Boolean = false
)

class AiTutorViewModel(
    private val repository: ChatMessageRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AiTutorUiState())
    val uiState: StateFlow<AiTutorUiState> = _uiState.asStateFlow()

    init {
        loadHistory()
    }

    private fun loadHistory(userId: Int = 1) {
        viewModelScope.launch {
            repository.getMessages(userId).collect { list ->
                if (list.isEmpty()) {
                    seedDefaultConversation(userId)
                } else {
                    _uiState.value = _uiState.value.copy(messages = list)
                }
            }
        }
    }

    private fun seedDefaultConversation(userId: Int) {
        val initial = listOf(
            ChatMessage(
                userId = userId,
                courseContext = "Data Structures · Pro",
                messageText = "Hi Uday — I am your Edu Sutra tutor. Ask me about anything on your path.",
                isFromUser = false
            ),
            ChatMessage(
                userId = userId,
                courseContext = "Data Structures · Pro",
                messageText = "I don't understand recursion.",
                isFromUser = true
            ),
            ChatMessage(
                userId = userId,
                courseContext = "Data Structures · Pro",
                messageText = "Think of recursion like Russian dolls: each doll opens into a smaller version of itself, until you reach the smallest one that cannot open — that is your base case.\n\nYour quiz shows 40% here, so shall we start with a 2-minute example?",
                isFromUser = false
            ),
            ChatMessage(
                userId = userId,
                courseContext = "Data Structures · Pro",
                messageText = "Yes, give me an example.",
                isFromUser = true
            ),
            ChatMessage(
                userId = userId,
                courseContext = "Data Structures · Pro",
                messageText = "factorial(3) = 3 × factorial(2) = 3 × 2 × factorial(1) = 6.",
                isFromUser = false,
                sourceCitation = "SOURCE: EDU SUTRA NOTES 2.4"
            )
        )
        viewModelScope.launch {
            for (msg in initial) {
                repository.saveMessage(msg)
            }
        }
    }

    fun sendMessage(text: String, userId: Int = 1) {
        if (text.isBlank()) return

        val trimmed = text.trim()
        viewModelScope.launch {
            // Save user message
            repository.saveMessage(
                ChatMessage(
                    userId = userId,
                    courseContext = _uiState.value.courseContext,
                    messageText = trimmed,
                    isFromUser = true
                )
            )

            // Generate tutor response
            val replyText: String
            val citation: String?

            when {
                trimmed.contains("explain simply", true) || trimmed.contains("recursion", true) -> {
                    replyText = "In simple terms: A function calls itself to solve a smaller piece of the same problem. When it reaches the base condition, it stops unwinding."
                    citation = "SOURCE: EDU SUTRA NOTES 2.1"
                }
                trimmed.contains("practice", true) || trimmed.contains("problem", true) -> {
                    replyText = "Practice Challenge:\nWrite a recursive function in Kotlin/Java that calculates the sum of the first N natural numbers:\nsum(n) = if (n <= 1) 1 else n + sum(n - 1)."
                    citation = "SOURCE: PRACTICE SET 3 - RECURSION"
                }
                trimmed.contains("summarize", true) || trimmed.contains("notes", true) -> {
                    replyText = "Module 3 Summary (Stacks & Queues):\n1. Stack: LIFO (Last-In-First-Out) — push(), pop(), peek() in O(1).\n2. Queue: FIFO (First-In-First-Out) — enqueue(), dequeue() in O(1).\n3. Practical applications: Browser back-button, Call-stack management."
                    citation = "SOURCE: EDU SUTRA NOTES 3.4"
                }
                trimmed.contains("stack", true) || trimmed.contains("queue", true) -> {
                    replyText = "Stacks operate on LIFO (Last In First Out) principle, like a stack of plates. Queues operate on FIFO (First In First Out), like people standing in a line."
                    citation = "SOURCE: EDU SUTRA NOTES 3.1"
                }
                else -> {
                    replyText = "Great question regarding ${_uiState.value.courseContext}! I've connected this concept to your enrolled syllabus. Would you like me to explain simply, give a practice problem, or summarize your notes?"
                    citation = "SOURCE: EDU SUTRA SYLLABUS MATCH"
                }
            }

            repository.saveMessage(
                ChatMessage(
                    userId = userId,
                    courseContext = _uiState.value.courseContext,
                    messageText = replyText,
                    isFromUser = false,
                    sourceCitation = citation
                )
            )
        }
    }

    fun clearChat(userId: Int = 1) {
        viewModelScope.launch {
            repository.clearChat(userId)
            seedDefaultConversation(userId)
        }
    }
}

class AiTutorViewModelFactory(
    private val repository: ChatMessageRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AiTutorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AiTutorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

