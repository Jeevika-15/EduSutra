package com.edusutra.app.ui.screens.syllabus

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edusutra.app.ui.components.BadgeTag
import com.edusutra.app.ui.components.EduSutraTopBar
import com.edusutra.app.ui.theme.*
import com.edusutra.app.viewmodel.SyllabusUploadViewModel

@Composable
fun AddSyllabusScreen(
    syllabusViewModel: SyllabusUploadViewModel,
    onBackClick: () -> Unit,
    onBuildPlanClick: () -> Unit
) {
    val uiState by syllabusViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateBackground)
    ) {
        EduSutraTopBar(
            title = "Add semester syllabus",
            subtitle = "Step 2 of 3",
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Upload Options
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Upload Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { syllabusViewModel.setUploadSource("PDF") },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (uiState.uploadSource == "PDF") PrimaryBlueSoft else Color.White
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (uiState.uploadSource == "PDF") PrimaryBlue else SlateBorder
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📁", fontSize = 24.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "UPLOAD",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Navy900
                        )
                        Text(
                            text = "PDF / photo of syllabus",
                            fontSize = 10.sp,
                            color = SlateMuted,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Paste Text Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { syllabusViewModel.setUploadSource("Text") },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (uiState.uploadSource == "Text") PrimaryBlueSoft else Color.White
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (uiState.uploadSource == "Text") PrimaryBlue else SlateBorder
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📄", fontSize = 24.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "PASTE TEXT",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Navy900
                        )
                        Text(
                            text = "From college portal",
                            fontSize = 10.sp,
                            color = SlateMuted,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Divider OR
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = SlateBorder)
                Text(
                    text = "OR",
                    fontSize = 11.sp,
                    color = SlateMuted,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = SlateBorder)
            }

            // University template selection card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { syllabusViewModel.setUploadSource("Template") },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(
                    1.5.dp,
                    if (uiState.uploadSource == "Template") PrimaryBlue else SlateBorder
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("📋", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Use university template",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Navy900
                        )
                        Text(
                            text = "JNTU R23 - CSE - Semester 4 - 5 subjects",
                            fontSize = 11.sp,
                            color = SlateMuted
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (uiState.uploadSource == "Template") PrimaryBlue else SlateBorderSubtle
                    ) {
                        Text(
                            text = if (uiState.uploadSource == "Template") "Selected" else "Select",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (uiState.uploadSource == "Template") Color.White else Navy700,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Detected Subjects Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "DETECTED SUBJECTS — TAP TO INCLUDE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy700
                )
                Text(
                    text = "${uiState.subjects.size} found",
                    fontSize = 11.sp,
                    color = SlateMuted
                )
            }

            // Subjects List
            uiState.subjects.forEach { subject ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            syllabusViewModel.toggleSubject(subject.subjectId, !subject.isIncluded)
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (subject.isIncluded) Color.White else SlateBorderSubtle
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (subject.isIncluded) PrimaryBlue.copy(alpha = 0.5f) else SlateBorder
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (subject.isIncluded) PrimaryBlue else Color.White,
                            modifier = Modifier
                                .size(22.dp)
                                .border(
                                    1.dp,
                                    if (subject.isIncluded) PrimaryBlue else SlateBorder,
                                    RoundedCornerShape(6.dp)
                                )
                        ) {
                            if (subject.isIncluded) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "✓",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = subject.subjectName,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (subject.isIncluded) Navy900 else SlateMuted,
                            modifier = Modifier.weight(1f)
                        )

                        if (subject.status == "PATH READY") {
                            BadgeTag(
                                text = "PATH READY",
                                containerColor = SuccessGreenSoft,
                                contentColor = SuccessGreen
                            )
                        } else if (subject.status == "PARSING") {
                            BadgeTag(
                                text = "PARSING",
                                containerColor = WarningAmberSoft,
                                contentColor = WarningAmber
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        // Bottom CTA
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 8.dp
        ) {
            Button(
                onClick = {
                    syllabusViewModel.buildStudyPlan()
                    onBuildPlanClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text(
                    text = "Build my study plan →",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
