package com.edusutra.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edusutra.app.data.local.entity.Progress
import com.edusutra.app.data.local.entity.Mastery
import com.edusutra.app.data.local.entity.Revision
import com.edusutra.app.data.repository.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class ProgressUiState(
    val progressList: List<Progress> = emptyList(),
    val masteryList: List<Mastery> = emptyList(),
    val revisionList: List<Revision> = emptyList(),
    val isLoading: Boolean = false
)

class ProgressViewModel(
    private val repository: ProgressRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProgressUiState())
    val uiState: StateFlow<ProgressUiState> = _uiState.asStateFlow()

    fun loadProgress(userId: Int) {

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(isLoading = true)

            combine(
                repository.getProgress(userId),
                repository.getMastery(userId),
                repository.getRevisions(userId)
            ) { progress, mastery, revisions ->

                ProgressUiState(
                    progressList = progress,
                    masteryList = mastery,
                    revisionList = revisions,
                    isLoading = false
                )

            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun saveProgress(progress: Progress) {
        viewModelScope.launch {
            repository.saveProgress(progress)
        }
    }

    fun saveMastery(mastery: Mastery) {
        viewModelScope.launch {
            repository.saveMastery(mastery)
        }
    }

    fun saveRevision(revision: Revision) {
        viewModelScope.launch {
            repository.saveRevision(revision)
        }
    }

    fun completeLesson(userId: Int, courseId: Int) {
        viewModelScope.launch {
            repository.completeLesson(userId, courseId)
        }
    }

    fun completeRevision(revisionId: Int) {
        viewModelScope.launch {
            repository.completeRevision(revisionId)
        }
    }
}
