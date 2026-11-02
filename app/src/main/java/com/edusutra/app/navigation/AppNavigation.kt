package com.edusutra.app.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.edusutra.app.ui.screens.progress.ProgressScreen
import com.edusutra.app.ui.screens.quiz.DiagnosticScreen
import com.edusutra.app.viewmodel.ProgressUiState

@Composable
fun AppNavigation(
    progressUiState: ProgressUiState,
    onCompleteRevision: (Int) -> Unit,
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
    ) {

        composable(Screen.Login.route) {
            PlaceholderScreen(
                title = "Welcome to Edu Sutra",
                description = "Your Personal Learning Path",
                buttonText = "Get Started",
            ) {
                navController.navigate(Screen.Onboarding.route)
            }
        }

        composable(Screen.Onboarding.route) {
            PlaceholderScreen(
                title = "Welcome!",
                description = "Start your personalised learning journey.",
                buttonText = "Continue to Home",
            ) {
                navController.navigate(Screen.Home.route)
            }
        }

        composable(Screen.Home.route) {
            PlaceholderScreen(
                title = "Edu Sutra Dashboard",
                description = "Your learning journey starts here.",
                buttonText = "Explore Courses",
            ) {
                navController.navigate(Screen.Courses.route)
            }
        }

        composable(Screen.Courses.route) {
            PlaceholderScreen(
                title = "Course Catalogue",
                description = "Explore available courses.",
                buttonText = "View Progress",
            ) {
                navController.navigate(Screen.Progress.route)
            }
        }

        // Actual Progress screen connected to Room database
        composable(Screen.Progress.route) {
            ProgressScreen(
                uiState = progressUiState,
                onCompleteRevision = onCompleteRevision,
            )
        }

        composable(Screen.Profile.route) {
            PlaceholderScreen(
                title = "My Profile",
                description = "Manage your student profile.",
                buttonText = "Back to Home",
                onClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) {
                            inclusive = true
                        }
                    }
                },
            )
        }

        composable(Screen.CourseDetails.route) {
            SimpleScreen("Course Details")
        }

        composable(Screen.LearningPath.route) {
            SimpleScreen("Personalised Learning Path")
        }

        composable(Screen.Enrollment.route) {
            SimpleScreen("Course Enrollment")
        }

        composable(Screen.Diagnostic.route) {
            DiagnosticScreen(
                onSubmit = {
                    navController.navigate(Screen.Home.route)
                },
            )
        }

        composable(Screen.Lesson.route) {
            SimpleScreen("Lesson Player")
        }

        composable(Screen.Quiz.route) {
            SimpleScreen("Practice Quiz")
        }

        composable(Screen.Results.route) {
            SimpleScreen("Quiz Results")
        }

        composable(Screen.AiTutor.route) {
            SimpleScreen("AI Learning Tutor")
        }
    }
}

@Composable
fun PlaceholderScreen(
    title: String,
    description: String,
    buttonText: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onClick) {
            Text(buttonText)
        }
    }
}

@Composable
fun SimpleScreen(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}
