
package com.edusutra.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.edusutra.app.data.repository.CourseRepository

class CourseCatalogueViewModelFactory(
    private val repository: CourseRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseCatalogueViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CourseCatalogueViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
