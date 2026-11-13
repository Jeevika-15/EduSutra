package com.edusutra.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_attempts")
data class QuizAttempt(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val quizType: String,

    val questionId: Int = 0,

    val selectedAnswer: String = "",

    val correctAnswer: String = "",

    val isCorrect: Boolean = false,

    val attemptedAt: Long = System.currentTimeMillis(),
)
