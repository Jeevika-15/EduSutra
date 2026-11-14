
package com.edusutra.app.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edusutra.app.ui.screens.components.ChipRow

@Composable
fun OnboardingProfileScreen(
    onContinue: (String, String, String, String, String) -> Unit,
    onBack: () -> Unit
) {

    val brandBlue = Color(0xFF52669A)

    var university by rememberSaveable { mutableStateOf("") }
    var semester by rememberSaveable { mutableStateOf("Sem 4") }
    var goal by rememberSaveable { mutableStateOf("Semester exams") }
    var studyTime by rememberSaveable { mutableStateOf("2 hours") }
    var language by rememberSaveable { mutableStateOf("English") }
    var step by rememberSaveable { mutableStateOf(1) }
    var step2Goal by rememberSaveable { mutableStateOf(goal) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {

        Text(
            text = "Create your profile",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1D26)
        )
        Text(
            text = "Step $step of 3",
            fontSize = 13.sp,
            color = Color(0xFF6B7080)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LinearProgressIndicator(
            progress = { step / 3f },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = brandBlue,
            trackColor = Color(0xFFE3E6EC)
        )

        Spacer(modifier = Modifier.height(20.dp))

        when (step) {
            1 -> {
                SectionLabel("UNIVERSITY / COLLEGE")
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = university,
                    onValueChange = { university = it; error = null },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    placeholder = { Text("JNTU University - B.Tech CSE", fontSize = 13.sp) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                SectionLabel("SEMESTER")
                Spacer(modifier = Modifier.height(8.dp))
                ChipRow(
                    options = listOf("Sem 1", "Sem 2", "Sem 3", "Sem 4", "Sem 5", "Sem 6", "Sem 7", "Sem 8"),
                    selected = listOf(semester),
                    onSelect = { semester = it; error = null },
                    singleSelect = true
                )
            }
            2 -> {
                SectionLabel("YOUR GOAL")
                Spacer(modifier = Modifier.height(8.dp))
                ChipRow(
                    options = listOf("Semester exams", "Clear weak concepts", "GATE", "Placements", "Projects"),
                    selected = listOf(goal),
                    onSelect = { goal = it; error = null },
                    singleSelect = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                SectionLabel("STUDY TIME PER DAY")
                Spacer(modifier = Modifier.height(8.dp))
                ChipRow(
                    options = listOf("30 min", "2 hours", "4 hours", "Weekends only"),
                    selected = listOf(studyTime),
                    onSelect = { studyTime = it; error = null },
                    singleSelect = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                SectionLabel("CONTENT LANGUAGE")
                Spacer(modifier = Modifier.height(8.dp))
                ChipRow(
                    options = listOf("English", "Hinglish", "Hindi", "Auto-detect"),
                    selected = listOf(language),
                    onSelect = { language = it; error = null },
                    singleSelect = true
                )
            }
            3 -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F7FA)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Review your profile", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("University: $university", fontSize = 13.sp)
                        Text("Semester: $semester", fontSize = 13.sp)
                        Text("Goal: $goal", fontSize = 13.sp)
                        Text("Study time: $studyTime", fontSize = 13.sp)
                        Text("Language: $language", fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Profile data is kept in this onboarding flow until Firebase Authentication is configured. No account is created or registered yet.",
                    fontSize = 12.sp,
                    color = Color(0xFF6B7080)
                )
            }
        }

        if (error != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(error!!, fontSize = 13.sp, color = Color(0xFFB3261E))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                when (step) {
                    1 -> {
                        if (university.isBlank()) {
                            error = "University / college is required"
                        } else {
                            error = null
                            step = 2
                        }
                    }
                    2 -> {
                        if (goal.isBlank()) {
                            error = "Select a learning goal"
                        } else if (studyTime.isBlank()) {
                            error = "Select your daily study time"
                        } else if (language.isBlank()) {
                            error = "Select a content language"
                        } else {
                            error = null
                            step2Goal = goal
                            step = 3
                        }
                    }
                    3 -> {
                        if (university.isBlank()) {
                            error = "University / college is required"
                            step = 1
                        } else {
                            error = null
                            onContinue(university.trim(), semester, step2Goal, studyTime, language)
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = brandBlue),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = if (step == 3) "Finish  ->" else "Continue  ->",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (step == 1) "< Back" else "< Previous step",
            fontSize = 13.sp,
            color = brandBlue,
            modifier = Modifier
                .clickable {
                    if (step == 1) {
                        onBack()
                    } else {
                        step -= 1
                    }
                }
                .padding(vertical = 4.dp)
        )
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.8.sp,
        color = Color(0xFF3A3E4D)
    )
}
