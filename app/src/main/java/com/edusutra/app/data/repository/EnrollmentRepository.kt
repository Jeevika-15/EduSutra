
package com.edusutra.app.data.repository

import com.edusutra.app.data.local.dao.EnrollmentDao
import com.edusutra.app.data.local.entity.Enrollment

class EnrollmentRepository(
    private val enrollmentDao: EnrollmentDao
) {
    fun getEnrollmentsByUser(userId: Int) =
        enrollmentDao.getEnrollmentsByUser(userId)

    suspend fun enroll(enrollment: Enrollment) {
        enrollmentDao.insertEnrollment(enrollment)
    }

    suspend fun isEnrolled(
        userId: Int,
        courseId: Int
    ): Boolean {
        return enrollmentDao.getEnrollment(userId, courseId) != null
    }

    suspend fun updateEnrollment(enrollment: Enrollment) {
        enrollmentDao.updateEnrollment(enrollment)
    }
}
