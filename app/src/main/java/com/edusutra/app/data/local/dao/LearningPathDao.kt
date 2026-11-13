
package com.edusutra.app.data.local.dao

import androidx.room.*
import com.edusutra.app.data.local.entity.LearningPath
import kotlinx.coroutines.flow.Flow

@Dao
interface LearningPathDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStep(step: LearningPath)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSteps(steps: List<LearningPath>)

    @Query("""
        SELECT * FROM learning_paths
        WHERE userId = :userId AND courseId = :courseId
        ORDER BY stepOrder ASC
    """)
    fun getLearningPath(
        userId: Int,
        courseId: Int
    ): Flow<List<LearningPath>>

    @Update
    suspend fun updateStep(step: LearningPath)

    @Query("DELETE FROM learning_paths WHERE pathId = :pathId")
    suspend fun deleteStep(pathId: Int)
}
