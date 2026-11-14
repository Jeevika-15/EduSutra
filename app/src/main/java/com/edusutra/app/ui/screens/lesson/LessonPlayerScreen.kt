
package com.edusutra.app.ui.screens.lesson

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LessonPlayerScreen(
    courseId: Int,
    userId: Int,
    isLoading: Boolean,
    onMarkComplete: (Int, Int) -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onDownload: () -> Unit
) {

    val brandBlue = Color(0xFF52669A)
    val darkNavy = Color(0xFF1A2340)

    var isCompleted by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf("Lessons") }
    var selectedChapter by remember { mutableStateOf("3.2") }
    var lessonMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Lesson", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("Module 3 of 6", fontSize = 12.sp, color = Color(0xFF6B7080))
            }
            OutlinedButton(onClick = onDownload) { Text("Download") }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(darkNavy, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("VIDEO PLAYER", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
                Text("Stacks & Queues (12:04)", fontSize = 14.sp, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text("Stacks & Queues - LIFO vs FIFO", fontSize = 17.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(6.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            LabelChip("SOURCE: NPTEL / IIT")
            LabelChip("IN MY PATH", filled = true)
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            listOf("Lessons", "Notes", "Transcript", "Practice").forEach { tab ->
                Text(
                    text = tab,
                    fontSize = 14.sp,
                    fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal,
                    color = if (selectedTab == tab) brandBlue else Color(0xFF6B7080),
                    modifier = Modifier.clickable { selectedTab = tab }
                )
            }
        }

        if (selectedTab != "Lessons") {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "$selectedTab is not available yet.",
                fontSize = 13.sp,
                color = Color(0xFF6B7080)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        ChapterRow("3.1", "Why stacks", "4:10", done = true, selected = selectedChapter == "3.1") { selectedChapter = "3.1" }
        ChapterRow("3.2", "Push / Pop", "playing", done = false, selected = selectedChapter == "3.2") { selectedChapter = "3.2" }
        ChapterRow("3.3", "Queue operations", "6:02", done = false, selected = selectedChapter == "3.3") { selectedChapter = "3.3" }
        ChapterRow("3.4", "Practice set", "10 Q", done = false, selected = selectedChapter == "3.4") { selectedChapter = "3.4" }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F7FA)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Notes PDF - 6 pages", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text("LMS notes, screenshots & original slides", fontSize = 12.sp, color = Color(0xFF6B7080))
                }
                Text("Offline", fontSize = 12.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = onPrevious,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Previous") }

            Button(
                onClick = {
                    if (!isCompleted) {
                        isCompleted = true
                        lessonMessage = "Lesson marked complete."
                        onMarkComplete(userId, courseId)
                    }
                },
                modifier = Modifier
                    .weight(1.4f)
                    .height(48.dp),
                enabled = !isCompleted && !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = brandBlue,
                    disabledContainerColor = Color(0xFFC5CAD9)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
                } else {
                    Text(if (isCompleted) "Completed" else "Mark complete  ->", fontWeight = FontWeight.Bold)
                }
            }
        }

        if (lessonMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(lessonMessage!!, fontSize = 13.sp, color = Color(0xFF2E7D32))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Next lesson loads automatically when you finish this module.",
            fontSize = 11.sp,
            color = Color(0xFF8A8FA3)
        )
    }
}

@Composable
private fun LabelChip(text: String, filled: Boolean = false) {
    Box(
        modifier = Modifier
            .background(
                if (filled) Color(0xFFE8F5E9) else Color(0xFFEFF1F5),
                RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = if (filled) Color(0xFF2E7D32) else Color(0xFF6B7080)
        )
    }
}

@Composable
private fun ChapterRow(
    number: String,
    title: String,
    meta: String,
    done: Boolean,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = if (done) "✓ " else "$number ",
                fontSize = 13.sp,
                color = if (done) Color(0xFF2E7D32) else Color(0xFF6B7080),
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                color = if (selected) Color(0xFF52669A) else Color(0xFF1A1D26)
            )
        }
        Text(meta, fontSize = 12.sp, color = Color(0xFF8A8FA3))
    }
}
