
package com.edusutra.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edusutra.app.data.local.entity.Course
import com.edusutra.app.data.local.entity.Enrollment
import com.edusutra.app.data.repository.CourseRepository
import com.edusutra.app.data.repository.EnrollmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CourseDetailsUiState(
    val course: Course? = null,
    val isEnrolled: Boolean = false,
    val isLoading: Boolean = false,
    val message: String? = null
)

class CourseDetailsViewModel(
    private val courseRepository: CourseRepository,
    private val enrollmentRepository: EnrollmentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CourseDetailsUiState())
    val uiState: StateFlow<CourseDetailsUiState> =
        _uiState.asStateFlow()

    fun loadCourse(courseId: Int, userId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )

            courseRepository.getCourseById(courseId).collect { course ->
                val enrolled = enrollmentRepository.isEnrolled(
                    userId,
                    courseId
                )

                _uiState.value = _uiState.value.copy(
                    course = course,
                    isEnrolled = enrolled,
                    isLoading = false
                )
            }
        }
    }

    fun enrollUser(userId: Int, courseId: Int) {
        viewModelScope.launch {
            val alreadyEnrolled = enrollmentRepository.isEnrolled(
                userId,
                courseId
            )

            if (!alreadyEnrolled) {
                enrollmentRepository.enroll(
                    Enrollment(
                        userId = userId,
                        courseId = courseId
                    )
                )

                _uiState.value = _uiState.value.copy(
                    isEnrolled = true,
                    message = "Successfully enrolled!"
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isEnrolled = true,
                    message = "You are already enrolled."
                )
            }
        }
    }
}
