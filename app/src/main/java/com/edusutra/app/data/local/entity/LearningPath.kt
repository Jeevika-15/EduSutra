
package com.edusutra.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "learning_paths")
data class LearningPath(
    @PrimaryKey(autoGenerate = true)
    val pathId: Int = 0,
    val userId: Int,
    val courseId: Int,
    val stepTitle: String,
    val description: String,
    val stepOrder: Int,
    val isCompleted: Boolean = false
)
