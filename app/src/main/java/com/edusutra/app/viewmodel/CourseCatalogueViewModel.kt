
package com.edusutra.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edusutra.app.data.local.entity.Course
import com.edusutra.app.data.repository.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class CourseCatalogueUiState(
    val courses: List<Course> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)

class CourseCatalogueViewModel(
    private val repository: CourseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CourseCatalogueUiState())
    val uiState: StateFlow<CourseCatalogueUiState> =
        _uiState.asStateFlow()

    private var searchJob: kotlinx.coroutines.Job? = null

    fun loadCourses() {
        searchCourses("")
    }

    fun searchCourses(query: String) {
        _uiState.value = _uiState.value.copy(
            searchQuery = query,
            isLoading = true
        )

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            val courseFlow = if (query.isBlank()) {
                repository.allCourses
            } else {
                repository.searchCourses(query)
            }

            courseFlow.collectLatest { courses ->
                _uiState.value = _uiState.value.copy(
                    courses = courses,
                    isLoading = false
                )
            }
        }
    }
}
