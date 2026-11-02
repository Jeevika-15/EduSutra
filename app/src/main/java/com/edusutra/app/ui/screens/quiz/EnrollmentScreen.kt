package com.edusutra.app.ui.screens.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EnrollmentScreen(
    onConfirmEnrollment: () -> Unit = {},
) {
    var selectedPayment by remember {
        mutableStateOf("Online Banking")
    }

    var acceptedTerms by remember {
        mutableStateOf(value = false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Text(
            text = "Course Enrollment",
            style = MaterialTheme.typography.headlineMedium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Complete your enrollment to access the course.",
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
                    text = "Course Details",
                    style = MaterialTheme.typography.titleMedium,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("Course: Introduction to Database Systems")
                Text("Duration: 8 Weeks")
                Text("Enrollment Fee: RM 100.00")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Select Payment Method",
            style = MaterialTheme.typography.titleMedium,
        )

        val paymentMethods = listOf(
            "Online Banking",
            "Credit / Debit Card",
            "E-Wallet",
        )

        paymentMethods.forEach { method ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = selectedPayment == method,
                    onClick = {
                        selectedPayment = method
                    },
                )

                Text(method)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = acceptedTerms,
                onCheckedChange = {
                    acceptedTerms = it
                },
            )

            Text("I agree to the enrollment terms and conditions.")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onConfirmEnrollment,
            enabled = acceptedTerms,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Confirm Enrollment")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EnrollmentScreenPreview() {
    EnrollmentScreen()
}
