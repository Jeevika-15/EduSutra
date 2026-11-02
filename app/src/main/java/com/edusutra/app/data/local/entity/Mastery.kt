package com.edusutra.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mastery")
data class Mastery(

    @PrimaryKey(autoGenerate = true)
    val masteryId: Int = 0,

    val userId: Int,

    val courseId: Int,

    val topicName: String,

    val masteryScore: Float = 0f,

    val questionsAttempted: Int = 0,

    val questionsCorrect: Int = 0,

    val masteryLevel: String = "Beginner",

    val lastUpdated: Long = System.currentTimeMillis(),
)
