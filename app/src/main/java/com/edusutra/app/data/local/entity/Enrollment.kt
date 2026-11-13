
package com.edusutra.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "enrollments")
data class Enrollment(
    @PrimaryKey(autoGenerate = true)
    val enrollmentId: Int = 0,
    val userId: Int,
    val courseId: Int,
    val enrollmentDate: Long = System.currentTimeMillis(),
    val status: String = "ENROLLED"
)
