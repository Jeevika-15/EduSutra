
package com.edusutra.app.ui.screens.courses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SyllabusScreen(
    courseName: String,
    units: List<Pair<String, List<String>>>
) {
    var expandedUnit by remember {
        mutableStateOf(-1)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "$courseName Syllabus",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(units.size) { index ->

                val unit = units[index]

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    expandedUnit =
                                        if (expandedUnit == index) -1
                                        else index
                                },
                            horizontalArrangement =
                                Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = unit.first,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                if (expandedUnit == index) "−" else "+"
                            )
                        }

                        if (expandedUnit == index) {
                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            unit.second.forEachIndexed { topicIndex, topic ->
                                Text(
                                    text = "${topicIndex + 1}. $topic",
                                    modifier = Modifier.padding(
                                        vertical = 6.dp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
