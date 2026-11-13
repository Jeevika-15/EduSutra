package com.edusutra.app.ui.screens.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DiagnosticScreen(
    onSubmit: () -> Unit = {},
) {
    var selectedAnswer by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "Diagnostic Test",
            style = MaterialTheme.typography.headlineMedium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Test your current knowledge before starting the course.",
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(24.dp))

        LinearProgressIndicator(
            progress = { 0.1f },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Question 1 of 10",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "What is the main purpose of a database?",
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(modifier = Modifier.height(16.dp))

        val options = listOf(
            "To store and organise data",
            "To design images",
            "To browse the internet",
            "To edit videos",
        )

        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                RadioButton(
                    selected = selectedAnswer == option,
                    onClick = {
                        selectedAnswer = option
                    },
                )

                Text(text = option)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSubmit,
            enabled = selectedAnswer.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Submit Answer")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiagnosticScreenPreview() {
    DiagnosticScreen()
}
