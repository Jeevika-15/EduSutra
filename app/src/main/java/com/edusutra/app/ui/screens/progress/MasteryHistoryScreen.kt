
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

private val MasteryBlue = Color(0xFF52669A)

@Composable
fun MasteryHistoryScreen(
    masteryList: List<Mastery>
) {
    val averageScore = if (masteryList.isNotEmpty()) {
        masteryList.map { it.masteryScore }.average().toInt()
    } else {
        0
    }

    val strongestTopic = masteryList.maxByOrNull {
        it.masteryScore
    }

    val weakestTopic = masteryList.minByOrNull {
        it.masteryScore
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
                    "Mastery History",
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    "Monitor your understanding of every learning topic.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MasteryBlue
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "Overall Average Mastery",
                        color = Color.White
                    )

                    Text(
                        "$averageScore%",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White
                    )

                    LinearProgressIndicator(
                        progress = { averageScore / 100f },
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        trackColor = Color.White.copy(alpha = 0.3f)
                    )

                    Text(
                        "${masteryList.size} topics recorded",
                        color = Color.White
                    )
                }
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MasterySummaryCard(
                    title = "Strongest Topic",
                    value = strongestTopic?.topicName ?: "Not available",
                    score = strongestTopic?.masteryScore?.toInt(),
                    modifier = Modifier.weight(1f)
                )

                MasterySummaryCard(
                    title = "Needs Practice",
                    value = weakestTopic?.topicName ?: "Not available",
                    score = weakestTopic?.masteryScore?.toInt(),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Text(
                "Topic Performance History",
                style = MaterialTheme.typography.titleLarge
            )
        }

        if (masteryList.isEmpty()) {
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "No mastery history available yet. Complete lessons and quizzes to build your history.",
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(
                items = masteryList,
                key = { it.masteryId }
            ) { mastery ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
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
                                mastery.topicName,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.weight(1f)
                            )

                            Text(
                                "${mastery.masteryScore.toInt()}%",
                                style = MaterialTheme.typography.titleMedium,
                                color = MasteryBlue
                            )
                        }

                        LinearProgressIndicator(
                            progress = {
                                (mastery.masteryScore / 100f)
                                    .coerceIn(0f, 1f)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            color = MasteryBlue
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                mastery.masteryLevel,
                                style = MaterialTheme.typography.labelLarge
                            )

                            Text(
                                "${mastery.questionsCorrect}/${mastery.questionsAttempted} correct",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Text(
                            "Course ID: ${mastery.courseId}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        item {
            Text(
                "Mastery Level Guide",
                style = MaterialTheme.typography.titleLarge
            )

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("Beginner: 0–49%")
                    Text("Intermediate: 50–74%")
                    Text("Advanced: 75–100%")
                }
            }
        }
    }
}

@Composable
private fun MasterySummaryCard(
    title: String,
    value: String,
    score: Int?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                title,
                style = MaterialTheme.typography.labelMedium
            )

            Text(
                value,
                style = MaterialTheme.typography.titleSmall
            )

            Text(
                score?.let { "$it% mastery" } ?: "No records",
                color = MasteryBlue,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
