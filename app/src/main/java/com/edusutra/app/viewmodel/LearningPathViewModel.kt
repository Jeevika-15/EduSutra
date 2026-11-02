
package com.edusutra.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edusutra.app.data.local.entity.LearningPath
import com.edusutra.app.data.repository.LearningPathRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LearningPathUiState(
    val steps: List<LearningPath> = emptyList(),
    val isLoading: Boolean = false
)

class LearningPathViewModel(
    private val repository: LearningPathRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LearningPathUiState())
    val uiState: StateFlow<LearningPathUiState> = _uiState.asStateFlow()

    fun loadLearningPath(userId: Int, courseId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            repository.getLearningPath(userId, courseId).collect { steps ->
                _uiState.value = LearningPathUiState(
                    steps = steps,
                    isLoading = false
                )
            }
        }
    }

    fun addStep(step: LearningPath) {
        viewModelScope.launch {
            repository.insertStep(step)
        }
    }

    fun addSteps(steps: List<LearningPath>) {
        viewModelScope.launch {
            repository.insertSteps(steps)
        }
    }

    fun updateStep(step: LearningPath) {
        viewModelScope.launch {
            repository.updateStep(step)
        }
    }

    fun deleteStep(pathId: Int) {
        viewModelScope.launch {
            repository.deleteStep(pathId)
        }
    }
}
