package com.edusutra.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "progress")
data class Progress(

    @PrimaryKey(autoGenerate = true)
    val progressId: Int = 0,

    val userId: Int,

    val courseId: Int,

    val completedLessons: Int = 0,

    val totalLessons: Int = 0,

    val completionPercentage: Float = 0f,

    val totalStudyMinutes: Int = 0,

    val lastUpdated: Long = System.currentTimeMillis(),
)
