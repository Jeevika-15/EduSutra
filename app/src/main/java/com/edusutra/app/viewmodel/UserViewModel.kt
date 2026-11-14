
package com.edusutra.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edusutra.app.data.local.entity.User
import com.edusutra.app.data.repository.UserRepository
import com.edusutra.app.data.auth.PasswordPolicy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UserUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val message: String? = null,
    val isDevAuthenticated: Boolean = false,
    val isDevOnboardingComplete: Boolean = false
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

    fun registerUser(user: User) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                // Passwords must never be stored in Room. Until Firebase Auth is
                // configured, registration is unavailable rather than falling
                // back to plaintext/local storage.
                _uiState.value = UserUiState(
                    isLoading = false,
                    message = "Account creation requires Firebase Authentication, which is not configured yet."
                )
            } catch (exception: Exception) {
                _uiState.value = UserUiState(
                    isLoading = false,
                    message = "Unable to create account."
                )
            }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val policyError = PasswordPolicy.validate(password)
                if (policyError != null) {
                    _uiState.value = UserUiState(
                        isLoading = false,
                        message = policyError
                    )
                    return@launch
                }

                // Temporary development/testing sign-in: entered credentials are
                // validated but NOT sent anywhere (no Room/Preferences/log
                // writes) and do NOT represent Firebase authentication or email
                // verification. This flag only drives navigation during testing
                // and must be replaced by real Firebase checks once configured.
                _uiState.value = UserUiState(
                    isLoading = false,
                    message = "Development sign-in (NOT Firebase) - email not verified.",
                    isDevAuthenticated = true
                )
            } catch (exception: Exception) {
                _uiState.value = UserUiState(
                    isLoading = false,
                    message = "Unable to sign in."
                )
            }
        }
    }

    fun completeDevOnboarding() {
        _uiState.value = _uiState.value.copy(isDevOnboardingComplete = true)
    }

    fun logoutDev() {
        _uiState.value = UserUiState(
            isDevAuthenticated = false,
            isDevOnboardingComplete = false
        )
    }

    fun requestPasswordReset(email: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                // Firebase password-reset emails require Firebase Auth setup.
                _uiState.value = UserUiState(
                    isLoading = false,
                    message = "Firebase Authentication is not configured yet. Password reset is unavailable."
                )
            } catch (exception: Exception) {
                _uiState.value = UserUiState(
                    isLoading = false,
                    message = "Unable to request a password reset."
                )
            }
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
}
