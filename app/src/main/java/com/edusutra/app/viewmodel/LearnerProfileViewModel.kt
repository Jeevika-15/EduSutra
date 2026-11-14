package com.edusutra.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edusutra.app.data.local.entity.LearnerProfile
import com.edusutra.app.data.repository.LearnerProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LearnerProfileUiState(
    val profile: LearnerProfile = LearnerProfile(),
    val isLoading: Boolean = false,
    val isSaved: Boolean = false
)

class LearnerProfileViewModel(
    private val repository: LearnerProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LearnerProfileUiState())
    val uiState: StateFlow<LearnerProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile(1)
    }

    fun loadProfile(userId: Int = 1) {
        viewModelScope.launch {
            repository.getProfile(userId).collect { saved ->
                if (saved != null) {
                    _uiState.value = _uiState.value.copy(profile = saved)
                }
            }
        }
    }

    fun updateProfile(profile: LearnerProfile) {
        viewModelScope.launch {
            repository.saveProfile(profile)
            _uiState.value = _uiState.value.copy(profile = profile, isSaved = true)
        }
    }

    fun saveOnboarding(
        university: String,
        semester: String,
        goal: String,
        dailyTime: String,
        language: String
    ) {
        viewModelScope.launch {
            val updated = _uiState.value.profile.copy(
                university = university,
                semester = semester,
                learningGoal = goal,
                dailyStudyTime = dailyTime,
                contentLanguage = language
            )
            repository.saveProfile(updated)
            _uiState.value = _uiState.value.copy(profile = updated, isSaved = true)
        }
    }
}

class LearnerProfileViewModelFactory(
    private val repository: LearnerProfileRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LearnerProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LearnerProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

