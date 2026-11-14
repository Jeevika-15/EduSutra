package com.edusutra.app.ui.screens.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edusutra.app.ui.components.BadgeTag
import com.edusutra.app.ui.components.EduStatBox
import com.edusutra.app.ui.components.EduSutraTopBar
import com.edusutra.app.ui.theme.*
import com.edusutra.app.viewmodel.ProgressUiState

@Composable
fun ProgressScreen(
    uiState: ProgressUiState,
    onCompleteRevision: (Int) -> Unit
) {
    val totalConcepts = 12
    val masteredCount = uiState.masteryList.count { it.masteryScore >= 75f }.coerceAtLeast(7)
    val weakCount = uiState.masteryList.count { it.masteryScore < 50f }.coerceAtLeast(3)
    val revisionDueCount = uiState.revisionList.count { !it.isCompleted }.coerceAtLeast(2)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateBackground)
    ) {
        EduSutraTopBar(
            title = "Mastery dashboard",
            subtitle = "Spaced + works offline",
            badgeText = "Day 2/5/12/30",
            actionText = "Export",
            onActionClick = {}
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Dropdown / Scope Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "PROGRESS · DATA STRUCTURES",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
                BadgeTag("This week", SlateBorderSubtle, Navy800)
            }

            // Top 3 Stat Cards (Mastered / Weak / Revision Due)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                EduStatBox(
                    title = "MASTERED",
                    value = "$masteredCount/$totalConcepts",
                    highlightColor = SuccessGreen,
                    modifier = Modifier.weight(1f)
                )

                EduStatBox(
                    title = "WEAK",
                    value = "$weakCount",
                    highlightColor = ErrorRed,
                    modifier = Modifier.weight(1f)
                )

                EduStatBox(
                    title = "REVISION DUE",
                    value = "$revisionDueCount",
                    highlightColor = WarningAmber,
                    modifier = Modifier.weight(1f)
                )
            }

            // Concept Mastery List Section
            Text(
                text = "CONCEPT MASTERY",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Navy700
            )

            val topics = listOf(
                Pair("Arrays", "MASTERED"),
                Pair("Linked Lists", "MASTERED"),
                Pair("Stacks & Queues", "LEARNING 68%"),
                Pair("Recursion", "WEAK 40%"),
                Pair("Graphs", "WEAK 50%")
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    topics.forEach { (name, status) ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = name,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Navy900
                            )

                            when {
                                status.startsWith("MASTERED") -> BadgeTag("MASTERED", SuccessGreenSoft, SuccessGreen)
                                status.startsWith("LEARNING") -> BadgeTag(status, BlueBadge, PrimaryBlue)
                                else -> BadgeTag(status, ErrorRedSoft, ErrorRed)
                            }
                        }
                    }
                }
            }

            // Spaced Revision Schedule Section
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
                        Text(
                            text = "SPACED REVISION SCHEDULE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SlateMuted
                        )
                        Text(
                            text = "2 due today",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = WarningAmber
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("Day 1 ✓", "Day 2 due", "Day 5", "Day 12", "Day 30").forEach { day ->
                            val isDue = day.contains("due")
                            val isDone = day.contains("✓")
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = when {
                                    isDue -> WarningAmberSoft
                                    isDone -> SuccessGreenSoft
                                    else -> SlateBorderSubtle
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .border(
                                        1.dp,
                                        when {
                                            isDue -> WarningAmber
                                            isDone -> SuccessGreen
                                            else -> SlateBorder
                                        },
                                        RoundedCornerShape(8.dp)
                                    )
                            ) {
                                Text(
                                    text = day,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = when {
                                        isDue -> WarningAmber
                                        isDone -> SuccessGreen
                                        else -> Navy700
                                    },
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Activity - Last 7 Days (Bar Chart)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ACTIVITY — LAST 7 DAYS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SlateMuted
                        )
                        Text(
                            text = "Streak: 12 days",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = WarningAmber
                        )
                    }

                    // Bar Chart
                    val weekData = listOf(
                        "M" to 0.4f,
                        "T" to 0.65f,
                        "W" to 0.5f,
                        "T" to 0.95f,
                        "F" to 0.7f,
                        "S" to 0.85f,
                        "S" to 0.45f
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        weekData.forEach { (day, heightRatio) ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom,
                                modifier = Modifier.fillMaxHeight()
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = PrimaryBlue,
                                    modifier = Modifier
                                        .width(18.dp)
                                        .fillMaxHeight(heightRatio)
                                ) {}
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(day, fontSize = 11.sp, color = SlateMuted)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
