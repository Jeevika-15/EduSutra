package com.edusutra.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_questions")
data class QuizQuestion(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val topic: String,

    val difficulty: String,

    val questionText: String,

    val optionA: String,

    val optionB: String,

    val optionC: String,

    val optionD: String,

    val correctAnswer: String,

    val explanation: String = "",
)
