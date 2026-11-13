package com.edusutra.app.ui.screens.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.edusutra.app.viewmodel.ProgressUiState

private val EduBlue = Color(0xFF52669A)

@Composable
fun ProgressScreen(
    uiState: ProgressUiState,
    onCompleteRevision: (Int) -> Unit,
) {

    val totalCompleted = uiState.progressList.sumOf {
        it.completedLessons
    }

    val totalLessons = uiState.progressList.sumOf {
        it.totalLessons
    }

    val overallProgress = if (totalLessons > 0) {
        (totalCompleted.toFloat() / totalLessons).coerceIn(0f, 1f)
    } else {
        0f
    }

    val studyMinutes = uiState.progressList.sumOf {
        it.totalStudyMinutes
    }

    val weakTopics = uiState.masteryList.filter {
        it.masteryScore < 50f
    }

    val pendingRevisions = uiState.revisionList.filter {
        !it.isCompleted
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {

        item {
            Column {
                Text(
                    text = "My Learning Progress",
                    style = MaterialTheme.typography.headlineMedium,
                )

                Text(
                    text = "Track your learning journey with Edu Sutra.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = EduBlue
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Overall Completion",
                        color = Color.White
                    )

                    Text(
                        text = "${(overallProgress * 100).toInt()}%",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White
                    )

                    LinearProgressIndicator(
                        progress = { overallProgress },
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        trackColor = Color.White.copy(alpha = 0.3f)
                    )

                    Text(
                        text = "$totalCompleted of $totalLessons lessons completed",
                        color = Color.White
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatisticCard(
                    title = "Study Time",
                    value = "${studyMinutes / 60} hrs",
                    modifier = Modifier.weight(1f)
                )

                StatisticCard(
                    title = "Completed Lessons",
                    value = "$totalCompleted",
                    modifier = Modifier.weight(1f),
                )
            }
        }

        item {
            SectionTitle("Course Progress")
        }

        if (uiState.progressList.isEmpty()) {
            item {
                EmptyMessage("No course progress recorded yet.")
            }
        } else {
            items(uiState.progressList) { progress ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Course #${progress.courseId}",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "${progress.completedLessons} / ${progress.totalLessons} lessons"
                        )

                        val fraction = if (progress.totalLessons > 0) {
                            (progress.completedLessons.toFloat() /
                                    progress.totalLessons).coerceIn(0f, 1f)
                        } else {
                            0f
                        }

                        LinearProgressIndicator(
                            progress = { fraction },
                            modifier = Modifier.fillMaxWidth(),
                            color = EduBlue
                        )

                        Text(
                            text = "${(fraction * 100).toInt()}% completed",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        item {
            SectionTitle("Topic Mastery")
        }

        if (uiState.masteryList.isEmpty()) {
            item {
                EmptyMessage("No topic mastery records available.")
            }
        } else {
            items(uiState.masteryList) { mastery ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = mastery.topicName,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text("${mastery.masteryScore.toInt()}%")
                        }

                        val score = (mastery.masteryScore / 100f)
                            .coerceIn(0f, 1f)

                        LinearProgressIndicator(
                            progress = { score },
                            modifier = Modifier.fillMaxWidth(),
                            color = EduBlue
                        )

                        Text(
                            text = mastery.masteryLevel,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        item {
            SectionTitle("Topics to Improve")
        }

        if (weakTopics.isEmpty()) {
            item {
                EmptyMessage("No weak topics identified yet.")
            }
        } else {
            items(weakTopics) { topic ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = topic.topicName,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text("Mastery score: ${topic.masteryScore.toInt()}%")
                    }
                }
            }
        }

        item {
            SectionTitle("Revision Tasks")
        }

        if (pendingRevisions.isEmpty()) {
            item {
                EmptyMessage("You're all caught up! No pending revisions.")
            }
        } else {
            items(pendingRevisions) { revision ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = revision.isCompleted,
                            onCheckedChange = {
                                onCompleteRevision(revision.revisionId)
                            }
                        )

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = revision.topicName,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = revision.reason,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Text(
                            text = revision.priority,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun StatisticCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(title, style = MaterialTheme.typography.bodyMedium)

            Text(
                value,
                style = MaterialTheme.typography.headlineSmall,
                color = EduBlue
            )
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun EmptyMessage(message: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = message,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
