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
fun DiagnosticScreen(
    onSubmit: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var questionIndex by remember { mutableIntStateOf(3) } // Starting at Q4 to match wireframe exactly
    var selectedAnswer by remember { mutableStateOf("C") }

    val totalQuestions = 15
    val currentQuestion = "Which traversal visits the root node last?"
    val options = listOf(
        "A" to "In-order traversal",
        "B" to "Pre-order traversal",
        "C" to "Post-order traversal",
        "D" to "Level-order traversal"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateBackground)
    ) {
        EduSutraTopBar(
            title = "Diagnostic test",
            subtitle = "Unlocks your path",
            onBackClick = onBackClick,
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
                    text = "DIAGNOSTIC · DATA STRUCTURES",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
                Text(
                    text = "Q${questionIndex + 1} / $totalQuestions",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy900
                )
            }

            // Progress bar
            LinearProgressIndicator(
                progress = { (questionIndex + 1).toFloat() / totalQuestions },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = PrimaryBlue,
                trackColor = SlateBorder
            )

            // Difficulty & Prereq tags
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                BadgeTag("Difficulty: easy", SlateBorderSubtle, Navy800)
                BadgeTag("Prerequisites check", BlueBadge, PrimaryBlue)
            }

            // Question Text
            Text(
                text = currentQuestion,
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

            // Explanation card
            if (selectedAnswer == "C") {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SuccessGreenSoft),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SuccessGreenBorder)
                ) {
                    Text(
                        text = "✓ Correct. Post-order = left, right, root — the root is visited last.",
                        fontSize = 12.sp,
                        color = Navy900,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(14.dp)
                    )
                }
            }

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        if (questionIndex < totalQuestions - 1) questionIndex++
                        else onSubmit()
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
                ) {
                    Text("Skip", color = SlateMuted, fontSize = 13.sp)
                }

                Button(
                    onClick = {
                        if (questionIndex < totalQuestions - 1) {
                            questionIndex++
                            selectedAnswer = ""
                        } else {
                            onSubmit()
                        }
                    },
                    modifier = Modifier.weight(2f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                ) {
                    Text("Next question →", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }

            // Gap Map footer note
            Text(
                text = "Answers build the gap map: what you already know, what is weak, what is a prerequisite for next topic.",
                fontSize = 11.sp,
                color = SlateMuted,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
