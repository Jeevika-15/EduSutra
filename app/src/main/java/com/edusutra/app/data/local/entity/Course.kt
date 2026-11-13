
package com.edusutra.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true)
    val courseId: Int = 0,
    val title: String,
    val description: String,
    val category: String,
    val duration: String,
    val difficulty: String,
    val instructor: String,
    val imageUrl: String = ""
)
