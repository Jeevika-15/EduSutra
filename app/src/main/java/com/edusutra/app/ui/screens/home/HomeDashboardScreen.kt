package com.edusutra.app.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edusutra.app.ui.components.BadgeTag
import com.edusutra.app.ui.theme.*

@Composable
fun HomeDashboardScreen(
    studentName: String = "Uday",
    onStartLesson: () -> Unit,
    onStartQuiz: () -> Unit,
    onRevise: () -> Unit,
    onExploreCourses: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Header with Streak
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Good morning, $studentName",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy900
                )
                Text(
                    text = "Sem 4 CSE · JNTU · 2h planned today",
                    fontSize = 12.sp,
                    color = SlateMuted
                )
            }

            Surface(
                shape = RoundedCornerShape(20.dp),
                color = WarningAmberSoft,
                border = androidx.compose.foundation.BorderStroke(1.dp, WarningAmber)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🔥", fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("12-day streak", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = WarningAmber)
                }
            }
        }

        // Overall Mastery Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Navy900),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("OVERALL MASTERY", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.7f))
                    BadgeTag("DUE TODAY: 2", WarningAmberSoft, WarningAmber)
                }

                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("62%", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("7 / 12 concepts", fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f), modifier = Modifier.padding(bottom = 4.dp))
                }

                LinearProgressIndicator(
                    progress = { 0.62f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = PrimaryBlue,
                    trackColor = Color.White.copy(alpha = 0.2f)
                )
            }
        }

        // Today's Tasks Section
        Text(
            text = "TODAY'S TASKS",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Navy700
        )

        // Task 1: Lesson
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = PrimaryBlueSoft,
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("▶", fontSize = 14.sp, color = PrimaryBlue)
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text("Stacks & Queues — Lesson", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Navy900)
                    Text("Module 3 · video · 14 min", fontSize = 11.sp, color = SlateMuted)
                }

                Button(
                    onClick = onStartLesson,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text("Start", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }

        // Task 2: Quiz
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = SuccessGreenSoft,
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("✓", fontSize = 16.sp, color = SuccessGreen, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text("Practice quiz — 10 questions", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Navy900)
                    Text("Stacks/recursion · 5m today", fontSize = 11.sp, color = SlateMuted)
                }

                OutlinedButton(
                    onClick = onStartQuiz,
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text("Open", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Navy900)
                }
            }
        }

        // Task 3: Revision
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = WarningAmberSoft,
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("↺", fontSize = 16.sp, color = WarningAmber, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text("Revise: Recursion", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Navy900)
                    Text("Spaced reminder · due today", fontSize = 11.sp, color = SlateMuted)
                }

                OutlinedButton(
                    onClick = onRevise,
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarningAmber),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text("Revise", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = WarningAmber)
                }
            }
        }

        // Weak Areas Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("WEAK AREAS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SlateMuted)
                    Text("Fix now →", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue, modifier = Modifier.clickable { onRevise() })
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BadgeTag("Recursion 40%", ErrorRedSoft, ErrorRed)
                    BadgeTag("Graphs 50%", WarningAmberSoft, WarningAmber)
                    BadgeTag("Arrays 90%", SuccessGreenSoft, SuccessGreen)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

