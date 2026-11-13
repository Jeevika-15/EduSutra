
package com.edusutra.app.data.local.dao

import androidx.room.*
import com.edusutra.app.data.local.entity.Course
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: Course)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<Course>)

    @Query("SELECT * FROM courses ORDER BY title ASC")
    fun getAllCourses(): Flow<List<Course>>

    @Query("SELECT * FROM courses WHERE courseId = :id")
    fun getCourseById(id: Int): Flow<Course?>

    @Query("""
        SELECT * FROM courses
        WHERE title LIKE '%' || :query || '%'
        OR category LIKE '%' || :query || '%'
    """)
    fun searchCourses(query: String): Flow<List<Course>>

    @Delete
    suspend fun deleteCourse(course: Course)
}
