package com.edusutra.app.data.repository

import com.edusutra.app.data.local.entity.DiagnosticQuestion
import com.edusutra.app.data.local.entity.QuizQuestion

object QuizSampleData {

    val diagnosticQuestions = listOf(
        DiagnosticQuestion(
            questionText = "What is 5 + 3?",
            optionA = "6",
            optionB = "7",
            optionC = "8",
            optionD = "9",
            correctAnswer = "C",
            explanation = "5 + 3 = 8.",
            topic = "Mathematics",
        ),
        DiagnosticQuestion(
            questionText = "Which planet is known as the Red Planet?",
            optionA = "Earth",
            optionB = "Mars",
            optionC = "Jupiter",
            optionD = "Venus",
            correctAnswer = "B",
            explanation = "Mars is called the Red Planet because of its reddish appearance.",
            topic = "Science",
        ),
        DiagnosticQuestion(
            questionText = "What is the opposite of 'ancient'?",
            optionA = "Old",
            optionB = "Historic",
            optionC = "Modern",
            optionD = "Past",
            correctAnswer = "C",
            explanation = "Modern means relating to the present or recent times.",
            topic = "English",
        ),
    )

    val quizQuestions = listOf(
        QuizQuestion(
            questionText = "What is 12 × 4?",
            optionA = "36",
            optionB = "42",
            optionC = "48",
            optionD = "52",
            correctAnswer = "C",
            explanation = "12 multiplied by 4 equals 48.",
            difficulty = "Easy",
            topic = "Mathematics",
        ),
        QuizQuestion(
            questionText = "Which gas do plants absorb?",
            optionA = "Oxygen",
            optionB = "Carbon dioxide",
            optionC = "Nitrogen",
            optionD = "Hydrogen",
            correctAnswer = "B",
            explanation = "Plants absorb carbon dioxide during photosynthesis.",
            difficulty = "Easy",
            topic = "Science",
        ),
        QuizQuestion(
            questionText = "Choose the correct past tense of 'go'.",
            optionA = "Goed",
            optionB = "Going",
            optionC = "Gone",
            optionD = "Went",
            correctAnswer = "D",
            explanation = "The simple past tense of go is went.",
            difficulty = "Medium",
            topic = "English",
        ),
    )
}
