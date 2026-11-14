package com.edusutra.app.ui.screens.onboarding

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
import com.edusutra.app.ui.components.EduSutraTopBar
import com.edusutra.app.ui.theme.*
import com.edusutra.app.viewmodel.LearnerProfileViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OnboardingProfileScreen(
    profileViewModel: LearnerProfileViewModel,
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit
) {
    val uiState by profileViewModel.uiState.collectAsState()

    var university by remember(uiState.profile) {
        mutableStateOf(uiState.profile.university)
    }
    var selectedSemester by remember(uiState.profile) {
        mutableStateOf(uiState.profile.semester)
    }
    var selectedGoal by remember(uiState.profile) {
        mutableStateOf(uiState.profile.learningGoal)
    }
    var selectedTime by remember(uiState.profile) {
        mutableStateOf(uiState.profile.dailyStudyTime)
    }
    var selectedLanguage by remember(uiState.profile) {
        mutableStateOf(uiState.profile.contentLanguage)
    }

    val semesters = listOf("Sem 1", "Sem 2", "Sem 3", "Sem 4", "Sem 5", "Sem 6", "Sem 7", "Sem 8")
    val goals = listOf("Semester exams", "Clear weak concepts", "GATE", "Placements", "Projects")
    val studyTimes = listOf("30 min", "2 hours", "4 hours", "Weekends only")
    val languages = listOf("English", "Hinglish", "हिन्दी", "Auto-detect")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateBackground)
    ) {
        EduSutraTopBar(
            title = "Create your profile",
            subtitle = "Step 1 of 3",
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // University / College
            Column {
                Text(
                    text = "UNIVERSITY / COLLEGE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy700
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = university,
                    onValueChange = { university = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = SlateBorder,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }

            // Semester
            Column {
                Text(
                    text = "SEMESTER",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy700
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    semesters.forEach { sem ->
                        val isSelected = selectedSemester == sem
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) PrimaryBlue else Color.White,
                            modifier = Modifier
                                .clickable { selectedSemester = sem }
                                .border(
                                    1.dp,
                                    if (isSelected) PrimaryBlue else SlateBorder,
                                    RoundedCornerShape(8.dp)
                                )
                        ) {
                            Text(
                                text = sem,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else Navy800,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }

            // Your Goal
            Column {
                Text(
                    text = "YOUR GOAL",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy700
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    goals.forEach { goal ->
                        val isSelected = selectedGoal == goal
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) PrimaryBlue else Color.White,
                            modifier = Modifier
                                .clickable { selectedGoal = goal }
                                .border(
                                    1.dp,
                                    if (isSelected) PrimaryBlue else SlateBorder,
                                    RoundedCornerShape(20.dp)
                                )
                        ) {
                            Text(
                                text = goal,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else Navy800,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }

            // Study Time Per Day
            Column {
                Text(
                    text = "STUDY TIME PER DAY",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy700
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    studyTimes.forEach { time ->
                        val isSelected = selectedTime == time
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) PrimaryBlue else Color.White,
                            modifier = Modifier
                                .clickable { selectedTime = time }
                                .border(
                                    1.dp,
                                    if (isSelected) PrimaryBlue else SlateBorder,
                                    RoundedCornerShape(20.dp)
                                )
                        ) {
                            Text(
                                text = time,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else Navy800,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }

            // Content Language
            Column {
                Text(
                    text = "CONTENT LANGUAGE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy700
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    languages.forEach { lang ->
                        val isSelected = selectedLanguage == lang
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) PrimaryBlue else Color.White,
                            modifier = Modifier
                                .clickable { selectedLanguage = lang }
                                .border(
                                    1.dp,
                                    if (isSelected) PrimaryBlue else SlateBorder,
                                    RoundedCornerShape(20.dp)
                                )
                        ) {
                            Text(
                                text = lang,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else Navy800,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }

            // Explanatory note
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryBlueSoft),
                border = androidx.compose.foundation.BorderStroke(1.dp, BlueBadge)
            ) {
                Text(
                    text = "Why we ask: syllabus + goal + available hours are the three inputs the AI planner uses to build your personalised learning path.",
                    fontSize = 12.sp,
                    color = Navy800,
                    modifier = Modifier.padding(14.dp),
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        // Bottom Continue button
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 8.dp
        ) {
            Button(
                onClick = {
                    profileViewModel.saveOnboarding(
                        university = university,
                        semester = selectedSemester,
                        goal = selectedGoal,
                        dailyTime = selectedTime,
                        language = selectedLanguage
                    )
                    onContinueClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("Continue →", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

