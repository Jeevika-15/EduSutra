package com.edusutra.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.edusutra.app.data.local.entity.SyllabusSubject
import kotlinx.coroutines.flow.Flow

@Dao
interface SyllabusSubjectDao {

    @Query("SELECT * FROM syllabus_subjects WHERE userId = :userId ORDER BY subjectId ASC")
    fun getSubjects(userId: Int): Flow<List<SyllabusSubject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubjects(subjects: List<SyllabusSubject>)

    @Update
    suspend fun updateSubject(subject: SyllabusSubject)

    @Query("UPDATE syllabus_subjects SET isIncluded = :isIncluded WHERE subjectId = :subjectId")
    suspend fun toggleInclusion(subjectId: Int, isIncluded: Boolean)
}

