package com.edusutra.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.edusutra.app.data.local.entity.Progress
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: Progress): Long

    @Update
    suspend fun updateProgress(progress: Progress)

    @Delete
    suspend fun deleteProgress(progress: Progress)

    @Query("SELECT * FROM progress WHERE userId = :userId")
    fun getProgressByUser(userId: Int): Flow<List<Progress>>

    @Query("SELECT * FROM progress WHERE userId = :userId AND courseId = :courseId LIMIT 1")
    suspend fun getCourseProgress(userId: Int, courseId: Int): Progress?

    @Query("SELECT * FROM progress ORDER BY lastUpdated DESC")
    fun getAllProgress(): Flow<List<Progress>>
}
