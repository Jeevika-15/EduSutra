
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
import com.edusutra.app.data.local.entity.Mastery
import com.edusutra.app.data.local.entity.Progress

private val ResultsBlue = Color(0xFF52669A)

@Composable
fun ResultsScreen(
    masteryList: List<Mastery>,
    progressList: List<Progress>
) {
    val averageMastery = if (masteryList.isNotEmpty()) {
        masteryList.map { it.masteryScore }.average().toFloat()
    } else {
        0f
    }

    val weakTopics = masteryList.filter {
        it.masteryScore < 50f
    }

    val totalCompleted = progressList.sumOf {
        it.completedLessons
    }

    val totalLessons = progressList.sumOf {
        it.totalLessons
    }

    val completion = if (totalLessons > 0) {
        totalCompleted.toFloat() / totalLessons
    } else {
        0f
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    "Results & Weak Areas",
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    "Understand your performance and identify where to improve.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = ResultsBlue
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "Average Topic Mastery",
                        color = Color.White
                    )

                    Text(
                        "${averageMastery.toInt()}%",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White
                    )

                    LinearProgressIndicator(
                        progress = { (averageMastery / 100f).coerceIn(0f, 1f) },
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        trackColor = Color.White.copy(alpha = 0.3f)
                    )

                    Text(
                        if (masteryList.isEmpty())
                            "No mastery results recorded yet."
                        else
                            "Based on ${masteryList.size} recorded topics",
                        color = Color.White
                    )
                }
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ResultStatCard(
                    "Lessons Completed",
                    "$totalCompleted / $totalLessons",
                    Modifier.weight(1f)
                )

                ResultStatCard(
                    "Weak Topics",
                    "${weakTopics.size}",
                    Modifier.weight(1f)
                )
            }
        }

        item {
            Text(
                "Learning Completion",
                style = MaterialTheme.typography.titleLarge
            )

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("${(completion * 100).toInt()}% completed")

                    LinearProgressIndicator(
                        progress = { completion.coerceIn(0f, 1f) },
                        modifier = Modifier.fillMaxWidth(),
                        color = ResultsBlue
                    )

                    Text(
                        "$totalCompleted lessons completed out of $totalLessons",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            Text(
                "Topic-wise Results",
                style = MaterialTheme.typography.titleLarge
            )
        }

        if (masteryList.isEmpty()) {
            item {
                ResultEmptyCard("No topic results available yet.")
            }
        } else {
            items(masteryList) { mastery ->
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
                                mastery.topicName,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.weight(1f)
                            )

                            Text("${mastery.masteryScore.toInt()}%")
                        }

                        LinearProgressIndicator(
                            progress = {
                                (mastery.masteryScore / 100f)
                                    .coerceIn(0f, 1f)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            color = ResultsBlue
                        )

                        Text(
                            "${mastery.questionsCorrect} correct out of " +
                                    "${mastery.questionsAttempted} questions",
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            mastery.masteryLevel,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        item {
            Text(
                "Topics That Need Improvement",
                style = MaterialTheme.typography.titleLarge
            )
        }

        if (weakTopics.isEmpty()) {
            item {
                ResultEmptyCard("No weak topics identified yet.")
            }
        } else {
            items(weakTopics) { topic ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                topic.topicName,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text("Current mastery: ${topic.masteryScore.toInt()}%")

                            Text(
                                "Recommended: Revise this topic and practise more questions.",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
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
private fun ResultStatCard(
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
            Text(
                title,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                value,
                style = MaterialTheme.typography.headlineSmall,
                color = ResultsBlue
            )
        }
    }
}

@Composable
private fun ResultEmptyCard(message: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Text(
            message,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
