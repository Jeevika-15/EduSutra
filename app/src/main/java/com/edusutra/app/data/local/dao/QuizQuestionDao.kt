
package com.edusutra.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edusutra.app.data.local.entity.QuizQuestion
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizQuestionDao {

    @Query("SELECT * FROM quiz_questions ORDER BY id ASC")
    fun getAllQuestions(): Flow<List<QuizQuestion>>

    @Query("SELECT * FROM quiz_questions WHERE difficulty = :difficulty")
    fun getQuestionsByDifficulty(difficulty: String): Flow<List<QuizQuestion>>

    @Query("SELECT * FROM quiz_questions WHERE topic = :topic")
    fun getQuestionsByTopic(topic: String): Flow<List<QuizQuestion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuizQuestion>)

    @Query("DELETE FROM quiz_questions")
    suspend fun deleteAllQuestions()
}
