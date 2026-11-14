package com.edusutra.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.edusutra.app.data.local.entity.LearnerProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface LearnerProfileDao {

    @Query("SELECT * FROM learner_profiles WHERE userId = :userId LIMIT 1")
    fun getProfile(userId: Int): Flow<LearnerProfile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(profile: LearnerProfile)

    @Update
    suspend fun update(profile: LearnerProfile)
}

