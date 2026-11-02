package com.edusutra.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.edusutra.app.data.local.entity.Mastery
import kotlinx.coroutines.flow.Flow

@Dao
interface MasteryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMastery(mastery: Mastery): Long

    @Update
    suspend fun updateMastery(mastery: Mastery)

    @Delete
    suspend fun deleteMastery(mastery: Mastery)

    @Query("SELECT * FROM mastery WHERE userId = :userId")
    fun getMasteryByUser(userId: Int): Flow<List<Mastery>>

    @Query("SELECT * FROM mastery WHERE userId = :userId AND courseId = :courseId")
    fun getMasteryByCourse(userId: Int, courseId: Int): Flow<List<Mastery>>

    @Query("SELECT * FROM mastery ORDER BY masteryScore ASC")
    fun getWeakTopics(): Flow<List<Mastery>>
}
