
package com.edusutra.app.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.edusutra.app.data.local.entity.User

private val ProfileBlue = Color(0xFF52669A)

@Composable
fun ProfileScreen(
    user: User?,
    onSaveProfile: (User) -> Unit,
    onLogout: () -> Unit
) {
    var fullName by remember(user) {
        mutableStateOf(user?.fullName ?: "")
    }
    var email by remember(user) {
        mutableStateOf(user?.email ?: "")
    }
    var university by remember(user) {
        mutableStateOf(user?.university ?: "")
    }
    var course by remember(user) {
        mutableStateOf(user?.course ?: "")
    }
    var selectedPlan by remember {
        mutableStateOf("Free")
    }
    var message by remember {
        mutableStateOf<String?>(null)
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                Text(
                    "My Profile",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    "Manage your account and learning preferences.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.size(88.dp),
                        shape = CircleShape,
                        color = ProfileBlue
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                fullName.firstOrNull()?.uppercase() ?: "E",
                                style = MaterialTheme.typography.displayMedium,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        fullName.ifBlank { "Student Name" },
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        email.ifBlank { "student@example.com" },
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            item {
                Text(
                    "Personal Information",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            item {
                ProfileTextField(
                    label = "Full Name",
                    value = fullName,
                    onValueChange = { fullName = it }
                )
            }

            item {
                ProfileTextField(
                    label = "Email Address",
                    value = email,
                    onValueChange = { email = it }
                )
            }

            item {
                ProfileTextField(
                    label = "University",
                    value = university,
                    onValueChange = { university = it }
                )
            }

            item {
                ProfileTextField(
                    label = "Course / Programme",
                    value = course,
                    onValueChange = { course = it }
                )
            }

            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val currentUser = user

                        if (fullName.isBlank() ||
                            email.isBlank() ||
                            university.isBlank() ||
                            course.isBlank()
                        ) {
                            message = "Please complete all profile fields."
                        } else if (!android.util.Patterns.EMAIL_ADDRESS
                                .matcher(email.trim()).matches()
                        ) {
                            message = "Please enter a valid email address."
                        } else if (currentUser == null) {
                            message = "No saved student account was found."
                        } else {
                            onSaveProfile(
                                currentUser.copy(
                                    fullName = fullName.trim(),
                                    email = email.trim(),
                                    university = university.trim(),
                                    course = course.trim()
                                )
                            )
                            message = "Profile update submitted."
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ProfileBlue
                    )
                ) {
                    Text("Save Profile")
                }
            }

            item {
                Text(
                    "Subscription Plans",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            item {
                SubscriptionCard(
                    name = "Free",
                    description = "Access basic learning resources.",
                    selected = selectedPlan == "Free",
                    onSelect = { selectedPlan = "Free" }
                )
            }

            item {
                SubscriptionCard(
                    name = "Edu Sutra Premium",
                    description = "Explore advanced learning features and personalised study support.",
                    selected = selectedPlan == "Premium",
                    onSelect = { selectedPlan = "Premium" }
                )
            }

            item {
                Text(
                    "Selected plan: $selectedPlan",
                    color = ProfileBlue,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onLogout
                ) {
                    Text("Log Out")
                }
            }

            item {
                Text(
                    "Edu Sutra • Your Personal Learning Path",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    LaunchedEffect(message) {
        message?.let {
            snackbarHostState.showSnackbar(it)
            message = null
        }
    }
}

@Composable
private fun ProfileTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
private fun SubscriptionCard(
    name: String,
    description: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected)
                ProfileBlue.copy(alpha = 0.12f)
            else
                MaterialTheme.colorScheme.surface
        )
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
                    name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                if (selected) {
                    Text("Selected", color = ProfileBlue)
                }
            }

            Text(description)

            OutlinedButton(
                onClick = onSelect,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (selected) "Current Selection" else "Select Plan")
            }
        }
    }
}
