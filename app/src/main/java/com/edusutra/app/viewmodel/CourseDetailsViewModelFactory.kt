
package com.edusutra.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.edusutra.app.data.repository.CourseRepository
import com.edusutra.app.data.repository.EnrollmentRepository

class CourseDetailsViewModelFactory(
    private val courseRepository: CourseRepository,
    private val enrollmentRepository: EnrollmentRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CourseDetailsViewModel(
                courseRepository,
                enrollmentRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
