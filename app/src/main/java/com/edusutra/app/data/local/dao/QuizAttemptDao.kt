
package com.edusutra.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.edusutra.app.data.local.entity.QuizAttempt
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizAttemptDao {

    @Insert
    suspend fun insertAttempt(attempt: QuizAttempt)

    @Query("SELECT * FROM quiz_attempts ORDER BY attemptedAt DESC")
    fun getAllAttempts(): Flow<List<QuizAttempt>>

    @Query("SELECT * FROM quiz_attempts WHERE quizType = :quizType ORDER BY attemptedAt DESC")
    fun getAttemptsByType(quizType: String): Flow<List<QuizAttempt>>

    @Query("SELECT COUNT(*) FROM quiz_attempts WHERE isCorrect = 1")
    fun getCorrectAnswerCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM quiz_attempts")
    fun getTotalAttemptCount(): Flow<Int>
}
