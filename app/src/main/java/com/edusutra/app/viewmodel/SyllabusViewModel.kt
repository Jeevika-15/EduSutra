
package com.edusutra.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SyllabusUnit(
    val unitTitle: String,
    val topics: List<String>
)

data class SyllabusUiState(
    val courseName: String = "",
    val units: List<SyllabusUnit> = emptyList(),
    val isLoading: Boolean = false
)

class SyllabusViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SyllabusUiState())
    val uiState: StateFlow<SyllabusUiState> =
        _uiState.asStateFlow()

    fun loadSyllabus(
        courseName: String,
        units: List<SyllabusUnit>
    ) {
        viewModelScope.launch {
            _uiState.value = SyllabusUiState(
                courseName = courseName,
                units = units,
                isLoading = false
            )
        }
    }
}
