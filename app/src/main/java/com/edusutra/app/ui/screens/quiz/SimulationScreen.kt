package com.edusutra.app.ui.screens.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SimulationScreen(
    onStartSimulation: () -> Unit = {},
    onExit: () -> Unit = {},
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
            text = "Learning Simulation",
            style = MaterialTheme.typography.headlineMedium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Apply your knowledge through a practical scenario.",
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(
                    text = "Scenario 1",
                    style = MaterialTheme.typography.titleMedium,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "You are managing a project. One team member has missed an important deadline. What should you do first?",
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Choose your response:",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        val options = listOf(
            "Ignore the delay",
            "Discuss the problem with the team member",
            "Immediately cancel the project",
            "Blame the entire team",
        )

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
            onClick = onStartSimulation,
            enabled = selectedAnswer.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Continue Simulation")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onExit,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Exit")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SimulationScreenPreview() {
    SimulationScreen()
}
