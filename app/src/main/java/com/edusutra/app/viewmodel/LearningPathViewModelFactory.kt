
package com.edusutra.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.edusutra.app.data.repository.LearningPathRepository
import com.edusutra.app.data.repository.ProgressRepository

class LearningPathViewModelFactory(
    private val repository: LearningPathRepository,
    private val progressRepository: ProgressRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LearningPathViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LearningPathViewModel(
                repository,
                progressRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
