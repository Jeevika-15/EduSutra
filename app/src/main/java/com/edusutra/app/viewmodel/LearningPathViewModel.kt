
package com.edusutra.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edusutra.app.data.local.entity.LearningPath
import com.edusutra.app.data.local.entity.Progress
import com.edusutra.app.data.repository.LearningPathRepository
import com.edusutra.app.data.repository.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class LearningPathUiState(
    val steps: List<LearningPath> = emptyList(),
    val isLoading: Boolean = false
)

class LearningPathViewModel(
    private val repository: LearningPathRepository,
    private val progressRepository: ProgressRepository
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
            syncProgress(step.userId, step.courseId)
        }
    }

    fun addSteps(steps: List<LearningPath>) {
        viewModelScope.launch {
            repository.insertSteps(steps)

            steps.distinctBy { it.courseId to it.userId }
                .forEach { syncProgress(it.userId, it.courseId) }
        }
    }

    fun updateStep(step: LearningPath) {
        viewModelScope.launch {
            repository.updateStep(step)
            syncProgress(step.userId, step.courseId)
        }
    }

    fun deleteStep(pathId: Int) {
        viewModelScope.launch {
            val step = _uiState.value.steps.find { it.pathId == pathId }

            repository.deleteStep(pathId)

            if (step != null) {
                syncProgress(step.userId, step.courseId)
            }
        }
    }

    private suspend fun syncProgress(userId: Int, courseId: Int) {
        val steps = repository.getLearningPath(userId, courseId).first()

        val totalSteps = steps.size
        val completedSteps = steps.count { it.isCompleted }

        val existingProgress =
            progressRepository.getCourseProgress(userId, courseId)

        val percentage = if (totalSteps > 0) {
            completedSteps.toFloat() / totalSteps * 100f
        } else {
            0f
        }

        val updatedProgress = Progress(
            progressId = existingProgress?.progressId ?: 0,
            userId = userId,
            courseId = courseId,
            completedLessons = completedSteps,
            totalLessons = totalSteps,
            completionPercentage = percentage,
            totalStudyMinutes = existingProgress?.totalStudyMinutes ?: 0,
            lastUpdated = System.currentTimeMillis()
        )

        if (existingProgress == null) {
            progressRepository.saveProgress(updatedProgress)
        } else {
            progressRepository.updateProgress(updatedProgress)
        }
    }
}
