
package com.edusutra.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edusutra.app.data.local.entity.QuizAttempt
import com.edusutra.app.data.repository.QuizAttemptRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuizAttemptViewModel(
    private val repository: QuizAttemptRepository
) : ViewModel() {

    val attempts = repository.getAllAttempts()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val correctAnswers = repository.getCorrectAnswerCount()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0
        )

    val totalAttempts = repository.getTotalAttemptCount()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0
        )

    fun saveAttempt(
        quizType: String,
        questionId: Int,
        selectedAnswer: String,
        correctAnswer: String
    ) {
        viewModelScope.launch {
            val attempt = QuizAttempt(
                quizType = quizType,
                questionId = questionId,
                selectedAnswer = selectedAnswer,
                correctAnswer = correctAnswer,
                isCorrect = selectedAnswer == correctAnswer
            )

            repository.insertAttempt(attempt)
        }
    }
}
