
package com.edusutra.app.ui.screens.courses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.edusutra.app.data.local.entity.Course

@Composable
fun CourseDetailsScreen(
    course: Course,
    isEnrolled: Boolean,
    onEnrollClick: () -> Unit,
    onBackClick: () -> Unit,
    onSyllabusClick: () -> Unit,
    onLearningPathClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TextButton(onClick = onBackClick) {
            Text("← Back")
        }

        Text(
            text = course.title,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = course.description,
            style = MaterialTheme.typography.bodyLarge
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "Course Information",
                    style = MaterialTheme.typography.titleLarge
                )

                Text("Category: ${course.category}")
                Text("Duration: ${course.duration}")
                Text("Difficulty: ${course.difficulty}")
                Text("Instructor: ${course.instructor}")
            }
        }

        Button(
            onClick = onEnrollClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isEnrolled
        ) {
            Text(
                if (isEnrolled) "Already Enrolled"
                else "Enroll Now"
            )
        }

        OutlinedButton(
            onClick = onSyllabusClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Syllabus")
        }

        OutlinedButton(
            onClick = onLearningPathClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("My Learning Path")
        }
    }
}
