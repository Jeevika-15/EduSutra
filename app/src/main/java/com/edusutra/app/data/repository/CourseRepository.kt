
package com.edusutra.app.data.repository

import com.edusutra.app.data.local.dao.CourseDao
import com.edusutra.app.data.local.entity.Course

class CourseRepository(
    private val courseDao: CourseDao
) {
    val allCourses = courseDao.getAllCourses()

    fun getCourseById(id: Int) =
        courseDao.getCourseById(id)

    fun searchCourses(query: String) =
        courseDao.searchCourses(query)

    suspend fun insertCourse(course: Course) {
        courseDao.insertCourse(course)
    }

    suspend fun insertCourses(courses: List<Course>) {
        courseDao.insertCourses(courses)
    }

    suspend fun deleteCourse(course: Course) {
        courseDao.deleteCourse(course)
    }
}
