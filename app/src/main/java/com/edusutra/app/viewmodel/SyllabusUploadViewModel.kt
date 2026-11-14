package com.edusutra.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edusutra.app.data.local.entity.SyllabusSubject
import com.edusutra.app.data.repository.SyllabusSubjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SyllabusUploadUiState(
    val subjects: List<SyllabusSubject> = emptyList(),
    val isUploading: Boolean = false,
    val uploadSource: String = "Template", // "PDF", "Text", "Template"
    val isPlanBuilt: Boolean = false
)

class SyllabusUploadViewModel(
    private val repository: SyllabusSubjectRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SyllabusUploadUiState())
    val uiState: StateFlow<SyllabusUploadUiState> = _uiState.asStateFlow()

    init {
        loadSubjects()
    }

    fun loadSubjects(userId: Int = 1) {
        viewModelScope.launch {
            repository.getSubjects(userId).collect { list ->
                if (list.isEmpty()) {
                    seedDefaultSubjects(userId)
                } else {
                    _uiState.value = _uiState.value.copy(subjects = list)
                }
            }
        }
    }

    private fun seedDefaultSubjects(userId: Int) {
        val defaults = listOf(
            SyllabusSubject(userId = userId, semester = "Sem 4", subjectName = "Data Structures", status = "PATH READY", isIncluded = true),
            SyllabusSubject(userId = userId, semester = "Sem 4", subjectName = "Database Management", status = "PATH READY", isIncluded = true),
            SyllabusSubject(userId = userId, semester = "Sem 4", subjectName = "Computer Networks", status = "PARSING", isIncluded = true),
            SyllabusSubject(userId = userId, semester = "Sem 4", subjectName = "Operating Systems", status = "PENDING", isIncluded = false),
            SyllabusSubject(userId = userId, semester = "Sem 4", subjectName = "Discrete Mathematics", status = "PENDING", isIncluded = false)
        )
        viewModelScope.launch {
            repository.saveSubjects(defaults)
        }
    }

    fun toggleSubject(subjectId: Int, isIncluded: Boolean) {
        viewModelScope.launch {
            repository.toggleInclusion(subjectId, isIncluded)
        }
    }

    fun setUploadSource(source: String) {
        _uiState.value = _uiState.value.copy(uploadSource = source)
    }

    fun buildStudyPlan() {
        _uiState.value = _uiState.value.copy(isPlanBuilt = true)
    }
}

class SyllabusUploadViewModelFactory(
    private val repository: SyllabusSubjectRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SyllabusUploadViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SyllabusUploadViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

