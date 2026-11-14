package com.edusutra.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edusutra.app.data.local.entity.DiagnosticQuestion
import com.edusutra.app.data.repository.DiagnosticQuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DiagnosticQuestionViewModel(
    private val repository: DiagnosticQuestionRepository,
) : ViewModel() {

    val questions: StateFlow<List<DiagnosticQuestion>> = repository.getAllQuestions()
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

    fun getQuestionsByTopic(topic: String): Flow<List<DiagnosticQuestion>> {
        return repository.getQuestionsByTopic(topic)
    }
}
