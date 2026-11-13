package com.edusutra.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diagnostic_questions")
data class DiagnosticQuestion(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val topic: String,

    val questionText: String,

    val optionA: String,

    val optionB: String,

    val optionC: String,

    val optionD: String,

    val correctAnswer: String,

    val explanation: String = "",
)
