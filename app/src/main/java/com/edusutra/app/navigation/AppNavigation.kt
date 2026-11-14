
package com.edusutra.app.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.edusutra.app.data.local.entity.User
import com.edusutra.app.ui.screens.auth.OnboardingProfileScreen
import com.edusutra.app.ui.screens.auth.SplashSignInScreen
import com.edusutra.app.ui.screens.home.HomeDashboardScreen
import com.edusutra.app.ui.screens.lesson.LessonPlayerScreen
import com.edusutra.app.ui.screens.progress.ProgressScreen
import com.edusutra.app.ui.screens.progress.ResultsScreen
import com.edusutra.app.ui.screens.progress.MasteryHistoryScreen
import com.edusutra.app.ui.screens.profile.ProfileScreen
import com.edusutra.app.ui.screens.learning.AiTutorScreen
import com.edusutra.app.viewmodel.ProgressUiState

@Composable
fun AppNavigation(
    progressUiState: ProgressUiState,
    onCompleteRevision: (Int) -> Unit,
    user: User? = null,
    onSaveProfile: (User) -> Unit = {},
    onLogout: () -> Unit = {},
    isAuthLoading: Boolean = false,
    authMessage: String? = null,
    isDevAuthenticated: Boolean = false,
    isDevOnboardingComplete: Boolean = false,
    onSignIn: (String, String) -> Unit = { _, _ -> },
    onCreateAccount: (String, String) -> Unit = { _, _ -> },
    onForgotPassword: (String) -> Unit = {},
    onContinueOnboarding: (String, String, String, String, String) -> Unit = { _, _, _, _, _ -> },
    onMarkLessonComplete: (Int, Int) -> Unit = { _, _ -> },
    isLessonLoading: Boolean = false,
    onClearAuthMessage: () -> Unit = {}
) {

    val navController = rememberNavController()
    val authInfo = remember { mutableStateOf<String?>(null) }
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    LaunchedEffect(isDevAuthenticated, isDevOnboardingComplete) {
        if (isDevAuthenticated) {
            if (isDevOnboardingComplete) {
                if (currentRoute != Screen.Home.route) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            } else {
                if (currentRoute != Screen.Onboarding.route) {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Login.route) { inclusive = false }
                    }
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        // LOGIN
        composable(Screen.Login.route) {
            SplashSignInScreen(
                isLoading = isAuthLoading,
                errorMessage = authMessage,
                infoMessage = authInfo.value,
                onSignIn = { email, password ->
                    authInfo.value = null
                    onSignIn(email, password)
                },
                onCreateAccount = { email, password ->
                    authInfo.value = null
                    onCreateAccount(email, password)
                    navController.navigate(Screen.Onboarding.route)
                },
                onGoogleSignIn = {
                    authInfo.value = "Google sign-in is not configured yet. Firebase project and OAuth credentials are required."
                },
                onPhoneOtp = {
                    authInfo.value = "Phone OTP is not configured yet. Firebase Phone Authentication setup is required."
                },
                onForgotPassword = { email ->
                    if (email.isBlank()) {
                        authInfo.value = "Enter your email above first, then tap Forgot password."
                    } else {
                        authInfo.value = null
                        onForgotPassword(email)
                    }
                },
                onClearError = {
                    authInfo.value = null
                    onClearAuthMessage()
                }
            )
        }

        // ONBOARDING
        composable(Screen.Onboarding.route) {
            OnboardingProfileScreen(
                onContinue = { university, semester, goal, studyTime, language ->
                    onContinueOnboarding(university, semester, goal, studyTime, language)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // DASHBOARD
        composable(Screen.Home.route) {
            HomeDashboardScreen(
                user = user,
                progressState = progressUiState,
                onNavigate = { route -> navController.navigate(route) },
                onStartLesson = { navController.navigate(Screen.Lesson.route) },
                onOpenProgress = { navController.navigate(Screen.Progress.route) },
                onOpenProfile = { navController.navigate(Screen.Profile.route) },
                onOpenCourses = { navController.navigate(Screen.Courses.route) },
                courseRoute = Screen.Courses.route,
                progressRoute = Screen.Progress.route,
                resultsRoute = Screen.Results.route,
                masteryHistoryRoute = Screen.MasteryHistory.route,
                aiTutorRoute = Screen.AiTutor.route,
                profileRoute = Screen.Profile.route
            )
        }

        // COURSES
        composable(Screen.Courses.route) {
            PlaceholderScreen(
                title = "Course Catalogue",
                description = "Explore available courses.",
                buttonText = "View Progress",
                onClick = {
                    navController.navigate(Screen.Progress.route)
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

        // RESULTS AND WEAK AREAS
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

        // OTHER MODULES - TO BE INTEGRATED
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
            SimpleScreen("Diagnostic Assessment")
        }

        composable(Screen.Lesson.route) {
            LessonPlayerScreen(
                courseId = 1,
                userId = 1,
                isLoading = isLessonLoading,
                onMarkComplete = { userId, courseId ->
                    onMarkLessonComplete(userId, courseId)
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                },
                onPrevious = { navController.popBackStack() },
                onNext = { navController.popBackStack() },
                onDownload = {}
            )
        }

        composable(Screen.Quiz.route) {
            SimpleScreen("Practice Quiz")
        }
    }
}

// DASHBOARD WITH NAVIGATION TO YOUR MODULES

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
            "Edu Sutra",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            "Your Personal Learning Path",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        DashboardButton("Explore Courses") {
            navController.navigate(Screen.Courses.route)
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
        Text(title)
    }
}

// REUSABLE PLACEHOLDER SCREEN

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
            title,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            description,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onClick) {
            Text(buttonText)
        }
    }
}

// SIMPLE PLACEHOLDER FOR OTHER TEAM MODULES

@Composable
fun SimpleScreen(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            title,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
