
package com.edusutra.app.data.repository

import com.edusutra.app.data.local.dao.QuizQuestionDao
import com.edusutra.app.data.local.entity.QuizQuestion
import kotlinx.coroutines.flow.Flow

class QuizQuestionRepository(
    private val quizQuestionDao: QuizQuestionDao,
) {

    fun getAllQuestions(): Flow<List<QuizQuestion>> {
        return quizQuestionDao.getAllQuestions()
    }

    fun getQuestionsByDifficulty(
        difficulty: String
    ): Flow<List<QuizQuestion>> {
        return quizQuestionDao.getQuestionsByDifficulty(difficulty)
    }

    fun getQuestionsByTopic(
        topic: String
    ): Flow<List<QuizQuestion>> {
        return quizQuestionDao.getQuestionsByTopic(topic)
    }

    suspend fun insertQuestions(questions: List<QuizQuestion>) {
        quizQuestionDao.insertQuestions(questions)
    }

    suspend fun insertSampleQuestions() {
        quizQuestionDao.insertQuestions(
            QuizSampleData.quizQuestions
        )
    }

    suspend fun deleteAllQuestions() {
        quizQuestionDao.deleteAllQuestions()
    }
}
