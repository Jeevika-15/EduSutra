
package com.edusutra.app.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.edusutra.app.data.local.entity.User

import com.edusutra.app.ui.screens.progress.ProgressScreen
import com.edusutra.app.ui.screens.progress.ResultsScreen
import com.edusutra.app.ui.screens.progress.MasteryHistoryScreen
import com.edusutra.app.ui.screens.profile.ProfileScreen
import com.edusutra.app.ui.screens.learning.AiTutorScreen
import com.edusutra.app.ui.screens.learning.LearningPathScreen
import com.edusutra.app.ui.screens.courses.CourseCatalogueScreen
import com.edusutra.app.ui.screens.courses.CourseDetailsScreen
import com.edusutra.app.ui.screens.courses.SyllabusScreen
import com.edusutra.app.ui.screens.quiz.AdaptiveQuizScreen
import com.edusutra.app.ui.screens.quiz.EnrollmentScreen
import com.edusutra.app.ui.screens.quiz.DiagnosticScreen
import com.edusutra.app.ui.screens.quiz.SimulationScreen

import com.edusutra.app.viewmodel.ProgressUiState
import com.edusutra.app.viewmodel.CourseCatalogueViewModel
import com.edusutra.app.viewmodel.CourseDetailsViewModel
import com.edusutra.app.viewmodel.LearningPathViewModel

@Composable
fun AppNavigation(
    progressUiState: ProgressUiState,
    onCompleteRevision: (Int) -> Unit,
    user: User? = null,
    onSaveProfile: (User) -> Unit = {},
    onLogout: () -> Unit = {},
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
        startDestination = Screen.Home.route
    ) {

        // LOGIN
        composable(Screen.Login.route) {
            PlaceholderScreen(
                title = "Welcome to Edu Sutra",
                description = "Your Personal Learning Path",
                buttonText = "Get Started"
            ) {
                navController.navigate(Screen.Onboarding.route)
            }
        }

        // ONBOARDING
        composable(Screen.Onboarding.route) {
            PlaceholderScreen(
                title = "Welcome!",
                description = "Start your personalised learning journey.",
                buttonText = "Continue to Home"
            ) {
                navController.navigate(Screen.Home.route)
            }
        }

        // HOME DASHBOARD
        composable(Screen.Home.route) {
            DashboardNavigationScreen(navController)
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
        ) {
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

        // PERSONALIZED LEARNING PATH
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

        // RESULTS
        composable(Screen.Results.route) {
            ResultsScreen(
                masteryList = progressUiState.masteryList,
                progressList = progressUiState.progressList
            )
        }

        // MASTERY HISTORY
        composable(Screen.MasteryHistory.route) {
            MasteryHistoryScreen(
                masteryList = progressUiState.masteryList
            )
        }

        // AI TUTOR
        composable(Screen.AiTutor.route) {
            AiTutorScreen()
        }

        // PROFILE
        composable(Screen.Profile.route) {
            ProfileScreen(
                user = user,
                onSaveProfile = onSaveProfile,
                onLogout = {
                    onLogout()

                    navController.navigate(Screen.Login.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        // ENROLLMENT
        composable(Screen.Enrollment.route) {
            EnrollmentScreen(
                onConfirmEnrollment = {
                    navController.navigate(Screen.Diagnostic.route)
                }
            )
        }

        // DIAGNOSTIC TEST
        composable(Screen.Diagnostic.route) {
            DiagnosticScreen(
                onSubmit = {
                    navController.navigate(Screen.Home.route)
                }
            )
        }

        // LESSON
        composable(Screen.Lesson.route) {
            SimpleScreen("Lesson Player")
        }

        // PRACTICE QUIZ
        composable(Screen.Quiz.route) {
            AdaptiveQuizScreen(
                onSubmit = {
                    navController.navigate(Screen.Results.route)
                }
            )
        }

        // LEARNING SIMULATION
        composable(Screen.Simulation.route) {
            SimulationScreen(
                onStartSimulation = {
                    navController.navigate(Screen.Results.route)
                },
                onExit = {
                    navController.popBackStack()
                }
            )
        }
    }
}

// DASHBOARD
@Composable
fun DashboardNavigationScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Edu Sutra",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Your Personal Learning Path",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        DashboardButton("Explore Courses") {
            navController.navigate(Screen.Courses.route)
        }

        DashboardButton("Course Enrollment") {
            navController.navigate(Screen.Enrollment.route)
        }

        DashboardButton("Diagnostic Test") {
            navController.navigate(Screen.Diagnostic.route)
        }

        DashboardButton("Practice Quiz") {
            navController.navigate(Screen.Quiz.route)
        }

        DashboardButton("Learning Simulation") {
            navController.navigate(Screen.Simulation.route)
        }

        DashboardButton("My Learning Progress") {
            navController.navigate(Screen.Progress.route)
        }

        DashboardButton("Results & Weak Areas") {
            navController.navigate(Screen.Results.route)
        }

        DashboardButton("Mastery History") {
            navController.navigate(Screen.MasteryHistory.route)
        }

        DashboardButton("AI Learning Tutor") {
            navController.navigate(Screen.AiTutor.route)
        }

        DashboardButton("My Profile") {
            navController.navigate(Screen.Profile.route)
        }
    }
}

@Composable
fun DashboardButton(
    title: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = title)
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
            Text(text = buttonText)
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
