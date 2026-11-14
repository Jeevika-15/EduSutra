package com.edusutra.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edusutra.app.data.local.entity.User
import com.edusutra.app.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UserUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)

class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    fun loadUser(userId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val user = repository.getUserById(userId)

                _uiState.value = UserUiState(
                    user = user,
                    isLoading = false,
                    message = if (user == null)
                        "No student account found."
                    else null
                )
            } catch (exception: Exception) {
                _uiState.value = UserUiState(
                    isLoading = false,
                    message = "Unable to load profile."
                )
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            try {
                repository.updateUser(user)

                _uiState.value = _uiState.value.copy(
                    user = user,
                    message = "Profile updated successfully."
                )
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    message = "Unable to update profile."
                )
            }
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
}

