package com.edusutra.app.ui.screens.learning

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
import com.edusutra.app.data.local.entity.LearningPath
import com.edusutra.app.ui.components.BadgeTag
import com.edusutra.app.ui.components.EduSutraTopBar
import com.edusutra.app.ui.theme.*

@Composable
fun LearningPathScreen(
    courseName: String = "Data Structures & Algorithms",
    learningSteps: List<LearningPath>,
    onStepCompleted: (LearningPath) -> Unit,
    onStartLearningClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateBackground)
    ) {
        EduSutraTopBar(
            title = "Learning path",
            subtitle = "Personalised roadmap",
            onBackClick = onBackClick,
            actionText = "Edit path",
            onActionClick = {}
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Path Title Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "$courseName — your path",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Navy900
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "From syllabus + diagnostic · 6 weeks",
                        fontSize = 11.sp,
                        color = SlateMuted
                    )
                }
            }

            // Stages Timeline
            val stages = listOf(
                Triple("1 Prerequisites", "Big-O basic programming, recursion basics", "DONE"),
                Triple("2 Fundamentals", "Arrays ✓  Linked Lists ✓", "2/2"),
                Triple("3 Core", "• Stacks & Queues (active)\n• Trees (after module 3)\n• Graphs", "IN PROGRESS"),
                Triple("4 Practice · Test · Revision", "Adaptive quiz, mock test, spaced revision days 2/5/12/30", "LOCKED")
            )

            stages.forEachIndexed { index, (stageTitle, stageDesc, status) ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (status == "IN PROGRESS") PrimaryBlueSoft else Color.White
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.5.dp,
                        when (status) {
                            "IN PROGRESS" -> PrimaryBlue
                            "DONE" -> SuccessGreen.copy(alpha = 0.5f)
                            else -> SlateBorder
                        }
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = when (status) {
                                "DONE" -> SuccessGreenSoft
                                "IN PROGRESS" -> PrimaryBlue
                                else -> SlateBorderSubtle
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                when (status) {
                                    "DONE" -> Text("✓", color = SuccessGreen, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    "LOCKED" -> Text("🔒", fontSize = 12.sp)
                                    else -> Text(
                                        text = "${index + 1}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stageTitle,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Navy900
                                )

                                when (status) {
                                    "DONE" -> BadgeTag("DONE", SuccessGreenSoft, SuccessGreen)
                                    "2/2" -> BadgeTag("2/2", BlueBadge, PrimaryBlue)
                                    "IN PROGRESS" -> BadgeTag("IN PROGRESS", BlueBadge, PrimaryBlue)
                                    "LOCKED" -> BadgeTag("LOCKED", SlateBorderSubtle, SlateMuted)
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = stageDesc,
                                fontSize = 12.sp,
                                color = Navy700,
                                lineHeight = 17.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        // Bottom Recommended Next Card
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 10.dp
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
                    Text(
                        text = "RECOMMENDED NEXT",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SlateMuted
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        BadgeTag("VIDEO", SlateBorderSubtle, Navy800)
                        BadgeTag("NOTES", SlateBorderSubtle, Navy800)
                    }
                }

                Text(
                    text = "Stacks & Queues — video lecture, 12 min, beginner friendly",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Navy900
                )

                Button(
                    onClick = onStartLearningClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                ) {
                    Text("Start learning →", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}
