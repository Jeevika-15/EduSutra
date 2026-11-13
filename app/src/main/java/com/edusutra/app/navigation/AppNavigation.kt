package com.edusutra.app.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.edusutra.app.ui.screens.progress.ProgressScreen
import com.edusutra.app.ui.screens.courses.CourseCatalogueScreen
import com.edusutra.app.ui.screens.courses.CourseDetailsScreen
import com.edusutra.app.ui.screens.courses.SyllabusScreen
import com.edusutra.app.ui.screens.learning.LearningPathScreen
import com.edusutra.app.ui.screens.quiz.DiagnosticScreen

import com.edusutra.app.viewmodel.ProgressUiState
import com.edusutra.app.viewmodel.CourseCatalogueViewModel
import com.edusutra.app.viewmodel.CourseDetailsViewModel
import com.edusutra.app.viewmodel.LearningPathViewModel

@Composable
fun AppNavigation(
    progressUiState: ProgressUiState,
    onCompleteRevision: (Int) -> Unit,
    courseCatalogueViewModel: CourseCatalogueViewModel,
    courseDetailsViewModel: CourseDetailsViewModel,
    learningPathViewModel: LearningPathViewModel
) {
    val navController = rememberNavController()

    val catalogueState by courseCatalogueViewModel.uiState.collectAsState()
    val detailsState by courseDetailsViewModel.uiState.collectAsState()
    val learningPathState by learningPathViewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        // LOGIN
        composable(Screen.Login.route) {
            PlaceholderScreen(
                title = "Welcome to Edu Sutra",
                description = "Your Personal Learning Path",
                buttonText = "Get Started",
            ) {
                navController.navigate(Screen.Onboarding.route)
            }
        }

        // ONBOARDING
        composable(Screen.Onboarding.route) {
            PlaceholderScreen(
                title = "Welcome!",
                description = "Start your personalised learning journey.",
                buttonText = "Continue to Home",
            ) {
                navController.navigate(Screen.Home.route)
            }
        }

        // HOME
        composable(Screen.Home.route) {
            PlaceholderScreen(
                title = "Edu Sutra Dashboard",
                description = "Your learning journey starts here.",
                buttonText = "Explore Courses",
            ) {
                courseCatalogueViewModel.loadCourses()
                navController.navigate(Screen.Courses.route)
            }
        }

        // COURSE CATALOGUE
        composable(Screen.Courses.route) {
            LaunchedEffect(Unit) {
                courseCatalogueViewModel.loadCourses()
            }

            CourseCatalogueScreen(
                courses = catalogueState.courses,
                onCourseClick = { courseId ->
                    navController.navigate(
                        "${Screen.CourseDetails.route}/$courseId"
                    )
                },
                onSearch = { query ->
                    courseCatalogueViewModel.searchCourses(query)
                }
            )
        }

        // COURSE DETAILS
        composable(
            route = "${Screen.CourseDetails.route}/{courseId}",
            arguments = listOf(
                navArgument("courseId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val courseId =
                backStackEntry.arguments?.getInt("courseId") ?: 0

            LaunchedEffect(courseId) {
                courseDetailsViewModel.loadCourse(courseId, 1)
            }

            val course = detailsState.course

            if (course != null) {
                CourseDetailsScreen(
                    course = course,
                    isEnrolled = detailsState.isEnrolled,
                    onEnrollClick = {
                        courseDetailsViewModel.enrollUser(1, courseId)
                    },
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSyllabusClick = {
                        navController.navigate(
                            "${Screen.Syllabus.route}/$courseId"
                        )
                    },
                    onLearningPathClick = {
                        navController.navigate(
                            "${Screen.LearningPath.route}/$courseId"
                        )
                    }
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        // SYLLABUS
        composable(
            route = "${Screen.Syllabus.route}/{courseId}",
            arguments = listOf(
                navArgument("courseId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val courseId =
                backStackEntry.arguments?.getInt("courseId") ?: 0

            BackHandler {
                navController.popBackStack()
            }

            val units = listOf(
                "Unit 1: Introduction" to listOf(
                    "Basic Concepts",
                    "Fundamentals",
                    "Applications"
                ),
                "Unit 2: Core Concepts" to listOf(
                    "Important Topics",
                    "Practical Examples",
                    "Case Studies"
                ),
                "Unit 3: Advanced Topics" to listOf(
                    "Advanced Concepts",
                    "Real-world Applications"
                )
            )

            SyllabusScreen(
                courseName = detailsState.course?.title ?: "Course",
                units = units
            )
        }

        // LEARNING PATH
        composable(
            route = "${Screen.LearningPath.route}/{courseId}",
            arguments = listOf(
                navArgument("courseId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val courseId =
                backStackEntry.arguments?.getInt("courseId") ?: 0

            LaunchedEffect(courseId) {
                learningPathViewModel.loadLearningPath(1, courseId)
            }

            BackHandler {
                navController.popBackStack()
            }

            LearningPathScreen(
                courseName = detailsState.course?.title ?: "My Course",
                learningSteps = learningPathState.steps,
                onStepCompleted = { step ->
                    learningPathViewModel.updateStep(step)
                }
            )
        }

        // PROGRESS
        composable(Screen.Progress.route) {
            ProgressScreen(
                uiState = progressUiState,
                onCompleteRevision = onCompleteRevision
            )
        }

        // PROFILE
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
                }
            )
        }

        // OTHER SCREENS
        composable(Screen.Enrollment.route) {
            SimpleScreen("Course Enrollment")
        }

        composable(Screen.Diagnostic.route) {
            DiagnosticScreen(
                onSubmit = {
                    navController.navigate(Screen.Home.route)
                }
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
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge
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
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
