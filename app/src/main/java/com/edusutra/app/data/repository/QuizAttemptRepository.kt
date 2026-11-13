
package com.edusutra.app.data.repository

import com.edusutra.app.data.local.dao.QuizAttemptDao
import com.edusutra.app.data.local.entity.QuizAttempt
import kotlinx.coroutines.flow.Flow

class QuizAttemptRepository(
    private val quizAttemptDao: QuizAttemptDao,
) {

    suspend fun insertAttempt(attempt: QuizAttempt) {
        quizAttemptDao.insertAttempt(attempt)
    }

    fun getAllAttempts(): Flow<List<QuizAttempt>> {
        return quizAttemptDao.getAllAttempts()
    }

    fun getAttemptsByType(
        quizType: String
    ): Flow<List<QuizAttempt>> {
        return quizAttemptDao.getAttemptsByType(quizType)
    }

    fun getCorrectAnswerCount(): Flow<Int> {
        return quizAttemptDao.getCorrectAnswerCount()
    }

    fun getTotalAttemptCount(): Flow<Int> {
        return quizAttemptDao.getTotalAttemptCount()
    }
}
