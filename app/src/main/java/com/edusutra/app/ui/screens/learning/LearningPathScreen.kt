
package com.edusutra.app.ui.screens.learning

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.edusutra.app.data.local.entity.LearningPath

@Composable
fun LearningPathScreen(
    courseName: String,
    learningSteps: List<LearningPath>,
    onStepCompleted: (LearningPath) -> Unit
) {
    val completedCount = learningSteps.count { it.isCompleted }

    val progress = if (learningSteps.isNotEmpty()) {
        completedCount.toFloat() / learningSteps.size
    } else {
        0f
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "My Learning Path",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = courseName,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Overall Progress: ${(progress * 100).toInt()}%"
        )

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Text(
            text = "$completedCount of ${learningSteps.size} steps completed"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Learning Journey",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                learningSteps.sortedBy { it.stepOrder },
                key = { it.pathId }
            ) { step ->

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Checkbox(
                            checked = step.isCompleted,
                            onCheckedChange = {
                                onStepCompleted(
                                    step.copy(isCompleted = it)
                                )
                            }
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = step.stepTitle,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = step.description,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Text(
                                text = if (step.isCompleted) {
                                    "Completed"
                                } else {
                                    "Pending"
                                },
                                color = if (step.isCompleted) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.secondary
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
