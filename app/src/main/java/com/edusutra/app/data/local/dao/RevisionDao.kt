package com.edusutra.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.edusutra.app.data.local.entity.Revision
import kotlinx.coroutines.flow.Flow

@Dao
interface RevisionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRevision(revision: Revision): Long

    @Update
    suspend fun updateRevision(revision: Revision)

    @Delete
    suspend fun deleteRevision(revision: Revision)

    @Query("SELECT * FROM revisions WHERE userId = :userId")
    fun getRevisionsByUser(userId: Int): Flow<List<Revision>>

    @Query("SELECT * FROM revisions WHERE userId = :userId AND isCompleted = 0")
    fun getPendingRevisions(userId: Int): Flow<List<Revision>>

    @Query("UPDATE revisions SET isCompleted = 1 WHERE revisionId = :revisionId")
    suspend fun completeRevision(revisionId: Int)
}
