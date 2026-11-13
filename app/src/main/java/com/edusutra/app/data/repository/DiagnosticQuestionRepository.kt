
package com.edusutra.app.data.repository

import com.edusutra.app.data.local.dao.DiagnosticQuestionDao
import com.edusutra.app.data.local.entity.DiagnosticQuestion
import kotlinx.coroutines.flow.Flow

class DiagnosticQuestionRepository(
    private val diagnosticQuestionDao: DiagnosticQuestionDao,
) {

    fun getAllQuestions(): Flow<List<DiagnosticQuestion>> {
        return diagnosticQuestionDao.getAllQuestions()
    }

    fun getQuestionsByTopic(topic: String): Flow<List<DiagnosticQuestion>> {
        return diagnosticQuestionDao.getQuestionsByTopic(topic)
    }

    suspend fun insertQuestions(questions: List<DiagnosticQuestion>) {
        diagnosticQuestionDao.insertQuestions(questions)
    }

    suspend fun insertSampleQuestions() {
        diagnosticQuestionDao.insertQuestions(
            QuizSampleData.diagnosticQuestions
        )
    }

    suspend fun deleteAllQuestions() {
        diagnosticQuestionDao.deleteAllQuestions()
    }
}
