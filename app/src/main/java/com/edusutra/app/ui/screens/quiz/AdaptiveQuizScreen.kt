package com.edusutra.app.ui.screens.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AdaptiveQuizScreen(
    onSubmit: () -> Unit = {},
) {
    var selectedAnswer by remember {
        mutableStateOf("")
    }

    var questionNumber by remember {
        mutableIntStateOf(1)
    }

    val options = listOf(
        "To store and organise information",
        "To create animations",
        "To browse websites",
        "To edit photographs",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "Adaptive Quiz",
            style = MaterialTheme.typography.headlineMedium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Questions are adjusted based on your performance.",
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(24.dp))

        LinearProgressIndicator(
            progress = { questionNumber / 10f },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Question $questionNumber of 10",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "What is the main purpose of a database?",
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(modifier = Modifier.height(16.dp))

        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = selectedAnswer == option,
                    onClick = {
                        selectedAnswer = option
                    },
                )
                Text(option)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (questionNumber < 10) {
                    questionNumber++
                    selectedAnswer = ""
                } else {
                    onSubmit()
                }
            },
            enabled = selectedAnswer.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                if (questionNumber < 10) "Next Question"
                else "Finish Quiz",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdaptiveQuizScreenPreview() {
    AdaptiveQuizScreen()
}
