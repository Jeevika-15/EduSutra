
package com.edusutra.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.edusutra.app.data.repository.LearningPathRepository

class LearningPathViewModelFactory(
    private val repository: LearningPathRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LearningPathViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LearningPathViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
