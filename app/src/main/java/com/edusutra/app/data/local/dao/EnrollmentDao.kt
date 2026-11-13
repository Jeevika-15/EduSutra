
package com.edusutra.app.data.local.dao

import androidx.room.*
import com.edusutra.app.data.local.entity.Enrollment
import kotlinx.coroutines.flow.Flow

@Dao
interface EnrollmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEnrollment(enrollment: Enrollment)

    @Query("SELECT * FROM enrollments WHERE userId = :userId")
    fun getEnrollmentsByUser(userId: Int): Flow<List<Enrollment>>

    @Query("""
        SELECT * FROM enrollments
        WHERE userId = :userId AND courseId = :courseId
        LIMIT 1
    """)
    suspend fun getEnrollment(userId: Int, courseId: Int): Enrollment?

    @Update
    suspend fun updateEnrollment(enrollment: Enrollment)

    @Delete
    suspend fun deleteEnrollment(enrollment: Enrollment)
}
