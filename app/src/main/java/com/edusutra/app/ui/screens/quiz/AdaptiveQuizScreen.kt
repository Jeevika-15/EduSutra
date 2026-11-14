package com.edusutra.app.ui.screens.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edusutra.app.ui.components.BadgeTag
import com.edusutra.app.ui.components.EduSutraTopBar
import com.edusutra.app.ui.theme.*

@Composable
fun AdaptiveQuizScreen(
    onSubmit: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var questionNumber by remember { mutableIntStateOf(3) } // Q3 to match wireframe
    var selectedAnswer by remember { mutableStateOf("B") }
    var currentAccuracy by remember { mutableIntStateOf(70) }
    var currentDifficulty by remember { mutableStateOf("Medium - adapt") }

    val totalQuestions = 10
    val questionText = "Which data structure follows the LIFO principle?"
    val options = listOf(
        "A" to "Queue",
        "B" to "Stack",
        "C" to "Array",
        "D" to "Linked List"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateBackground)
    ) {
        EduSutraTopBar(
            title = "Adaptive practice",
            subtitle = "Module 3 — Stacks",
            onBackClick = onBackClick,
            badgeText = "AI-difficulty",
            actionText = "Exit",
            onActionClick = onSubmit
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Category & Question Counter
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "PRACTICE QUIZ · DATA STRUCTURES",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
                Text(
                    text = "Q$questionNumber / $totalQuestions",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy900
                )
            }

            // Stats row (Accuracy %, Difficulty, +1 Mastery)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BadgeTag("Accuracy $currentAccuracy%", BlueBadge, PrimaryBlue)
                BadgeTag(currentDifficulty, WarningAmberSoft, WarningAmber)
                BadgeTag("+1 mastery", SuccessGreenSoft, SuccessGreen)
            }

            // Question
            Text(
                text = questionText,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Navy900,
                lineHeight = 24.sp
            )

            // Options List
            options.forEach { (key, text) ->
                val isSelected = selectedAnswer == key
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedAnswer = key },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) SuccessGreenSoft else Color.White
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.5.dp,
                        if (isSelected) SuccessGreen else SlateBorder
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isSelected) SuccessGreen else SlateBorderSubtle,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = key,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else Navy800
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = text,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) SuccessGreen else Navy900
                        )
                    }
                }
            }

            // Feedback Card with Analogy
            if (selectedAnswer == "B") {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SuccessGreenSoft),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SuccessGreenBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "✓ Correct! Stack follows LIFO — last in, first out (like a tray of plates).",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Navy900
                        )
                        Text(
                            text = "Spaced revision automatically schedules: wrong answers get +1 extra practice, and the topic is added to spaced review.",
                            fontSize = 11.sp,
                            color = Navy700,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            // Next Question / Finish CTA
            Button(
                onClick = {
                    if (questionNumber < totalQuestions) {
                        questionNumber++
                        currentAccuracy = ((questionNumber * 10).coerceAtMost(90))
                    } else {
                        onSubmit()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text(
                    text = if (questionNumber < totalQuestions) "Next question →" else "Finish Quiz & View Results",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Text(
                text = "Right/wrong card with rule, analogy and content source.",
                fontSize = 11.sp,
                color = SlateMuted
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
