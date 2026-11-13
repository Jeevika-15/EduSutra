package com.edusutra.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edusutra.app.data.local.entity.QuizQuestion
import com.edusutra.app.data.repository.QuizQuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuizQuestionViewModel(
    private val repository: QuizQuestionRepository,
) : ViewModel() {

    val questions: StateFlow<List<QuizQuestion>> = repository.getAllQuestions()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList(),
        )

    fun loadSampleQuestions() {
        viewModelScope.launch {
            if (questions.value.isEmpty()) {
                repository.insertSampleQuestions()
            }
        }
    }

    fun getQuestionsByDifficulty(difficulty: String): Flow<List<QuizQuestion>> {
        return repository.getQuestionsByDifficulty(difficulty)
    }

    fun getQuestionsByTopic(topic: String): Flow<List<QuizQuestion>> {
        return repository.getQuestionsByTopic(topic)
    }
}
