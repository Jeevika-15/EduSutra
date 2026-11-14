package com.edusutra.app.ui.screens.lesson

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
fun LessonPlayerScreen(
    lessonTitle: String = "Stacks & Queues — LIFO vs FIFO",
    moduleInfo: String = "Module 3 of 6",
    onMarkComplete: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onNavigateToQuiz: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf("Lessons") }
    var isCompleted by remember { mutableStateOf(false) }
    var isDownloaded by remember { mutableStateOf(false) }

    val tabs = listOf("Lessons", "Notes", "Transcript", "Practice")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateBackground)
    ) {
        EduSutraTopBar(
            title = "Lesson",
            subtitle = moduleInfo,
            onBackClick = onBackClick,
            actionText = if (isDownloaded) "Downloaded ✓" else "Download",
            onActionClick = { isDownloaded = true }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Video Player Container Box
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Surface(
                            shape = RoundedCornerShape(30.dp),
                            color = PrimaryBlue,
                            modifier = Modifier.size(54.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("▶", fontSize = 22.sp, color = Color.White)
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Stacks & Queues (12:14)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Video lecture · NPTEL Partner Content",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            // Title & Badges
            Column {
                Text(
                    text = lessonTitle,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy900
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    BadgeTag("SOURCE: NPTEL - IIT", BlueBadge, PrimaryBlue)
                    BadgeTag("IN MY PATH", SuccessGreenSoft, SuccessGreen)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Watch original on YouTube → Comes on device for offline study.",
                    fontSize = 11.sp,
                    color = SlateMuted
                )
            }

            // Tabs
            TabRow(
                selectedTabIndex = tabs.indexOf(selectedTab),
                containerColor = Color.White,
                contentColor = PrimaryBlue,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, SlateBorder, RoundedCornerShape(10.dp))
            ) {
                tabs.forEach { tab ->
                    Tab(
                        selected = selectedTab == tab,
                        onClick = {
                            selectedTab = tab
                            if (tab == "Practice") onNavigateToQuiz()
                        },
                        text = {
                            Text(
                                text = tab,
                                fontSize = 12.sp,
                                fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Medium,
                                color = if (selectedTab == tab) PrimaryBlue else SlateMuted
                            )
                        }
                    )
                }
            }

            if (selectedTab == "Lessons") {
                // Lesson checklist
                val lessonItems = listOf(
                    "3.1 Why Stacks" to true,
                    "3.2 Push / Pop Operations" to true,
                    "3.3 Queue Operations" to false,
                    "3.4 Practice Problem Set" to false
                )

                lessonItems.forEach { (title, done) ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (done) SuccessGreenSoft else SlateBorderSubtle,
                                modifier = Modifier.size(20.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = if (done) "✓" else "○",
                                        color = if (done) SuccessGreen else SlateMuted,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = title,
                                fontSize = 13.sp,
                                fontWeight = if (done) FontWeight.Bold else FontWeight.Normal,
                                color = Navy900
                            )
                        }
                    }
                }

                // Notes PDF Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryBlueSoft),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BlueBadge)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Notes PDF — 6 pages",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Navy900
                            )
                            Text(
                                text = "File size: 1.4 MB · stored in Room database",
                                fontSize = 11.sp,
                                color = SlateMuted
                            )
                        }

                        Button(
                            onClick = { isDownloaded = true },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isDownloaded) SuccessGreen else PrimaryBlue
                            ),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = if (isDownloaded) "Offline ✓" else "Download",
                                fontSize = 11.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            } else if (selectedTab == "Notes") {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Module 3 Notes: Stacks & Queues", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "• Stack: LIFO (Last-In-First-Out) linear data structure.\n• Common Operations: push(x), pop(), peek(), isEmpty().\n• Time Complexity: All basic operations run in O(1) time.\n• Common Applications: Function call stacks, Expression evaluation, Undo mechanisms.",
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            color = Navy800
                        )
                    }
                }
            } else if (selectedTab == "Transcript") {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Text(
                        text = "[00:00] Welcome to Lesson 3. In this session we explore Stacks and Queues.\n[02:15] Notice how items pushed onto the top of the stack are the first to be removed.\n[06:30] Let's look at the memory diagram and array implementation...",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = Navy800
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        // Navigation Footer: Previous & Mark Complete
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onBackClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
                ) {
                    Text("Previous", fontSize = 13.sp, color = Navy900)
                }

                Button(
                    onClick = {
                        isCompleted = true
                        onMarkComplete()
                    },
                    modifier = Modifier.weight(1.5f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isCompleted) SuccessGreen else PrimaryBlue
                    )
                ) {
                    Text(
                        text = if (isCompleted) "Completed ✓" else "Mark complete →",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
