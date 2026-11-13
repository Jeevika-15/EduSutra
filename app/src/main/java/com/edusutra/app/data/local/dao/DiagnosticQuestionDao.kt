
package com.edusutra.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edusutra.app.data.local.entity.DiagnosticQuestion
import kotlinx.coroutines.flow.Flow

@Dao
interface DiagnosticQuestionDao {

    @Query("SELECT * FROM diagnostic_questions ORDER BY id ASC")
    fun getAllQuestions(): Flow<List<DiagnosticQuestion>>

    @Query("SELECT * FROM diagnostic_questions WHERE topic = :topic")
    fun getQuestionsByTopic(topic: String): Flow<List<DiagnosticQuestion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<DiagnosticQuestion>)

    @Query("DELETE FROM diagnostic_questions")
    suspend fun deleteAllQuestions()
}
