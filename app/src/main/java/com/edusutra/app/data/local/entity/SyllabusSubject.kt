package com.edusutra.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "syllabus_subjects")
data class SyllabusSubject(
    @PrimaryKey(autoGenerate = true)
    val subjectId: Int = 0,
    val userId: Int = 1,
    val semester: String = "Sem 4",
    val subjectName: String,
    val status: String = "PATH READY",
    val isIncluded: Boolean = true
)

