
package com.edusutra.app.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edusutra.app.data.local.entity.User
import com.edusutra.app.viewmodel.ProgressUiState

@Composable
fun HomeDashboardScreen(
    user: User?,
    progressState: ProgressUiState,
    onNavigate: (String) -> Unit,
    onStartLesson: () -> Unit,
    onOpenProgress: () -> Unit,
    onOpenProfile: () -> Unit,
    onOpenCourses: () -> Unit,
    courseRoute: String,
    progressRoute: String,
    resultsRoute: String,
    masteryHistoryRoute: String,
    aiTutorRoute: String,
    profileRoute: String
) {

    val brandBlue = Color(0xFF52669A)
    val darkNavy = Color(0xFF1A2340)

    val progressList = progressState.progressList
    val masteryList = progressState.masteryList
    val revisionList = progressState.revisionList

    val totalCompleted = progressList.sumOf { it.completedLessons }
    val totalLessons = progressList.sumOf { it.totalLessons }
    val overallMastery = if (masteryList.isNotEmpty()) {
        (masteryList.map { it.masteryScore }.average()).toInt()
    } else if (totalLessons > 0) {
        ((totalCompleted.toFloat() / totalLessons) * 100).toInt()
    } else {
        0
    }

    val masteredCount = masteryList.count { it.masteryScore >= 70f }
    val totalConcepts = masteryList.size
    val weakTopics = masteryList.filter { it.masteryScore < 50f }
    val pendingRevisions = revisionList.filter { !it.isCompleted }

    val greeting = "Good morning"
    val firstName = user?.fullName?.trim()?.split(" ")?.firstOrNull()?.takeIf { it.isNotEmpty() } ?: "Student"
    val currentSemester = user?.course?.takeIf { it.isNotBlank() } ?: "Course not set"

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F7FA)),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("$greeting, $firstName", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1D26))
                    Text(
                        text = currentSemester,
                        fontSize = 12.sp,
                        color = Color(0xFF6B7080)
                    )
                }
                Box(
                    modifier = Modifier
                        .background(Color(0xFFF6F7FA), RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text("Progress loading", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF6B7080))
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = darkNavy),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("OVERALL MASTERY", fontSize = 11.sp, color = Color(0xFFA3ACCC), fontWeight = FontWeight.Bold)
                        Text("DUE TODAY", fontSize = 11.sp, color = Color(0xFFA3ACCC), fontWeight = FontWeight.Bold)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("$overallMastery%", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text("${pendingRevisions.size}", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                    LinearProgressIndicator(
                        progress = { (overallMastery / 100f).coerceIn(0f, 1f) },
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFF6EE7A8),
                        trackColor = Color.White.copy(alpha = 0.2f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "$masteredCount / $totalConcepts concepts mastered",
                        fontSize = 12.sp,
                        color = Color(0xFFA3ACCC)
                    )
                }
            }
        }

        item {
            Text("TODAY'S TASKS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3A3E4D))
        }

        if (progressList.isNotEmpty()) {
            item {
                TaskCard(
                    title = "Stacks & Queues - Lesson",
                    subtitle = "Video 12 min - module 3",
                    action = "Start",
                    primary = true
                ) { onStartLesson() }
            }
        }

        if (masteryList.isNotEmpty()) {
            item {
                TaskCard(
                    title = "Practice quiz - 10 questions",
                    subtitle = "Adaptive difficulty",
                    action = "Open",
                    primary = false
                ) { onNavigate(resultsRoute) }
            }
        }

        if (pendingRevisions.isNotEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onOpenProgress() }
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Revise: ${pendingRevisions.first().topicName}", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            Text("Spaced revision - due today", fontSize = 12.sp, color = Color(0xFF6B7080))
                        }
                        OutlinedButton(onClick = { onOpenProgress() }) { Text("Revise") }
                    }
                }
            }
        }

        item {
            Text("WEAK AREAS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3A3E4D))
        }

        item {
            if (weakTopics.isEmpty()) {
                Text("No weak areas yet.", fontSize = 13.sp, color = Color(0xFF6B7080))
            } else {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    weakTopics.take(3).forEach { topic ->
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFFCE7F3), RoundedCornerShape(12.dp))
                                .padding(horizontal = 10.dp, vertical = 8.dp)
                        ) {
                            Text(
                                "${topic.topicName} ${topic.masteryScore.toInt()}%",
                                fontSize = 12.sp,
                                color = Color(0xFFAD1457)
                            )
                        }
                    }
                }
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                QuickLink("Courses", Modifier.weight(1f)) { onNavigate(courseRoute) }
                QuickLink("Progress", Modifier.weight(1f)) { onNavigate(progressRoute) }
                QuickLink("Results", Modifier.weight(1f)) { onNavigate(resultsRoute) }
                QuickLink("AI Tutor", Modifier.weight(1f)) { onNavigate(aiTutorRoute) }
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                QuickLink("Mastery History", Modifier.weight(1f)) { onNavigate(masteryHistoryRoute) }
                QuickLink("My Profile", Modifier.weight(1f)) { onNavigate(profileRoute) }
            }
        }
    }
}

@Composable
private fun TaskCard(
    title: String,
    subtitle: String,
    action: String,
    primary: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Text(subtitle, fontSize = 12.sp, color = Color(0xFF6B7080))
            }
            if (primary) {
                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF52669A)),
                    shape = RoundedCornerShape(10.dp)
                ) { Text(action) }
            } else {
                OutlinedButton(onClick = onClick, shape = RoundedCornerShape(10.dp)) { Text(action) }
            }
        }
    }
}

@Composable
private fun QuickLink(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(42.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text, fontSize = 12.sp)
    }
}
