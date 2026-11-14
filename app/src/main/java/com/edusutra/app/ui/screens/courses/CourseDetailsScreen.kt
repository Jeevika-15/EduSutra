package com.edusutra.app.ui.screens.courses

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
import com.edusutra.app.data.local.entity.Course
import com.edusutra.app.ui.components.BadgeTag
import com.edusutra.app.ui.components.EduSutraTopBar
import com.edusutra.app.ui.theme.*

@Composable
fun CourseDetailsScreen(
    course: Course,
    isEnrolled: Boolean,
    onEnrollClick: () -> Unit,
    onBackClick: () -> Unit,
    onSyllabusClick: () -> Unit,
    onLearningPathClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("Overview") }
    val tabs = listOf("Overview", "Syllabus", "Reviews", "Source")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateBackground)
    ) {
        EduSutraTopBar(
            title = "Course details",
            subtitle = if (isEnrolled) "Enrolled" else "Before enrollment",
            onBackClick = onBackClick,
            badgeText = "NPTEL - IIT"
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Badges row
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                BadgeTag("NPTEL - IIT", BlueBadge, PrimaryBlue)
                BadgeTag("SYLLABUS MATCH 95%", SuccessGreenSoft, SuccessGreen)
            }

            // Title & Instructor info
            Column {
                Text(
                    text = course.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy900
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${course.instructor} · ${course.duration} · 4.8★ (2.4k) · English",
                    fontSize = 12.sp,
                    color = SlateMuted
                )
            }

            // Tab Row
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
                            if (tab == "Syllabus") onSyllabusClick()
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

            // Overview text
            Text(
                text = "A university-level path from arrays to graphs, with prerequisites checked before each module. 7 adaptive quizzes after every topic and 3 circuit-style simulations for recursion and trees.",
                fontSize = 13.sp,
                color = Navy700,
                lineHeight = 19.sp
            )

            // Two highlight cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryBlueSoft)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("YOU GET", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("• Personal path\n• Diagnostic test", fontSize = 11.sp, color = Navy800, lineHeight = 16.sp)
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SuccessGreenSoft)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("PRACTICE", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SuccessGreen)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("• Adaptive quizzes\n• Spaced revisions", fontSize = 11.sp, color = Navy800, lineHeight = 16.sp)
                    }
                }
            }

            // Modules section
            Text(
                text = "COURSE MODULES",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Navy700
            )

            val modules = listOf(
                Triple("1. Arrays & Strings", "Free preview · 6 lessons", "completed"),
                Triple("2. Linked Lists", "3 lessons · 1 simulation", "unlocked"),
                Triple("3. Stacks & Queues", "4 lessons · 1 quiz", "active"),
                Triple("4. Trees & Graphs", "Unlocks after module 3", "locked")
            )

            modules.forEach { (modTitle, modSubtitle, state) ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (state == "active") PrimaryBlueSoft else Color.White
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (state == "active") PrimaryBlue else SlateBorder
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = when (state) {
                                "completed" -> SuccessGreenSoft
                                "active" -> PrimaryBlue
                                else -> SlateBorderSubtle
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                when (state) {
                                    "completed" -> Text("✓", color = SuccessGreen, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    "locked" -> Text("🔒", fontSize = 11.sp)
                                    "active" -> Text("▶", color = Color.White, fontSize = 10.sp)
                                    else -> Text("○", color = Navy700, fontSize = 11.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = modTitle,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Navy900
                            )
                            Text(
                                text = modSubtitle,
                                fontSize = 11.sp,
                                color = SlateMuted
                            )
                        }

                        if (state == "completed") {
                            BadgeTag("DONE", SuccessGreenSoft, SuccessGreen)
                        } else if (state == "active") {
                            BadgeTag("IN PATH", BlueBadge, PrimaryBlue)
                        }
                    }
                }
            }

            // Learning Path shortcut
            OutlinedButton(
                onClick = onLearningPathClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryBlue)
            ) {
                Text("View Personalised Learning Path →", color = PrimaryBlue, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        // Bottom Sticky Enrollment bar
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 10.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("ENROLMENT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SlateMuted)
                    Text("Free + or Pro ₹149/mo", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Navy900)
                }

                Button(
                    onClick = onEnrollClick,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isEnrolled) SuccessGreen else PrimaryBlue
                    ),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = if (isEnrolled) "Enrolled ✓" else "Enroll now",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
