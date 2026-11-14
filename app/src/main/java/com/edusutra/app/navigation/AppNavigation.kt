package com.edusutra.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.edusutra.app.data.local.AppDatabase
import com.edusutra.app.data.repository.*
import com.edusutra.app.ui.components.EduSutraBottomBar
import com.edusutra.app.ui.screens.auth.SignInScreen
import com.edusutra.app.ui.screens.courses.CourseCatalogueScreen
import com.edusutra.app.ui.screens.courses.CourseDetailsScreen
import com.edusutra.app.ui.screens.courses.SyllabusScreen
import com.edusutra.app.ui.screens.home.HomeDashboardScreen
import com.edusutra.app.ui.screens.learning.LearningPathScreen
import com.edusutra.app.ui.screens.lesson.LessonPlayerScreen
import com.edusutra.app.ui.screens.onboarding.OnboardingProfileScreen
import com.edusutra.app.ui.screens.profile.ProfileSubscriptionScreen
import com.edusutra.app.ui.screens.progress.ProgressScreen
import com.edusutra.app.ui.screens.quiz.AdaptiveQuizScreen
import com.edusutra.app.ui.screens.quiz.DiagnosticScreen
import com.edusutra.app.ui.screens.quiz.EnrollmentScreen
import com.edusutra.app.ui.screens.quiz.QuizResultsScreen
import com.edusutra.app.ui.screens.quiz.SimulationScreen
import com.edusutra.app.ui.screens.syllabus.AddSyllabusScreen
import com.edusutra.app.ui.screens.system.SystemArchitectureScreen
import com.edusutra.app.ui.screens.tutor.AiTutorScreen
import com.edusutra.app.viewmodel.*

@Composable
fun AppNavigation(
    database: AppDatabase,
    progressViewModel: ProgressViewModel,
    courseCatalogueViewModel: CourseCatalogueViewModel,
    courseDetailsViewModel: CourseDetailsViewModel,
    learningPathViewModel: LearningPathViewModel,
    learnerProfileViewModel: LearnerProfileViewModel,
    syllabusUploadViewModel: SyllabusUploadViewModel,
    circuitSimulationViewModel: CircuitSimulationViewModel,
    aiTutorViewModel: AiTutorViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarRoutes = listOf(
        Screen.Home.route,
        Screen.Courses.route,
        Screen.Progress.route,
        Screen.Profile.route,
        Screen.AiTutor.route
    )

    val showBottomBar = currentRoute in bottomBarRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                EduSutraBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            // SCREEN 1: Splash & Sign In
            composable(Screen.Login.route) {
                SignInScreen(
                    onSignInSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onCreateAccount = {
                        navController.navigate(Screen.Onboarding.route)
                    }
                )
            }

            // SCREEN 2: Onboarding Profile
            composable(Screen.Onboarding.route) {
                OnboardingProfileScreen(
                    profileViewModel = learnerProfileViewModel,
                    onBackClick = { navController.popBackStack() },
                    onContinueClick = {
                        navController.navigate(Screen.AddSyllabus.route)
                    }
                )
            }

            // SCREEN 3: Add Semester Syllabus
            composable(Screen.AddSyllabus.route) {
                AddSyllabusScreen(
                    syllabusViewModel = syllabusUploadViewModel,
                    onBackClick = { navController.popBackStack() },
                    onBuildPlanClick = {
                        navController.navigate(Screen.Courses.route)
                    }
                )
            }

            // SCREEN 4: Course Catalogue
            composable(Screen.Courses.route) {
                val catalogueState by courseCatalogueViewModel.uiState.collectAsState()

                LaunchedEffect(Unit) {
                    courseCatalogueViewModel.loadCourses()
                }

                CourseCatalogueScreen(
                    courses = catalogueState.courses,
                    onCourseClick = { courseId ->
                        navController.navigate("${Screen.CourseDetails.route}/$courseId")
                    },
                    onSearch = { query ->
                        courseCatalogueViewModel.searchCourses(query)
                    },
                    onMyLearningClick = {
                        navController.navigate("${Screen.LearningPath.route}/1")
                    }
                )
            }

            // SCREEN 5: Course Details
            composable(
                route = "${Screen.CourseDetails.route}/{courseId}",
                arguments = listOf(navArgument("courseId") { type = NavType.IntType })
            ) { backStackEntry ->
                val courseId = backStackEntry.arguments?.getInt("courseId") ?: 1
                val detailsState by courseDetailsViewModel.uiState.collectAsState()

                LaunchedEffect(courseId) {
                    courseDetailsViewModel.loadCourse(courseId, 1)
                }

                val course = detailsState.course
                if (course != null) {
                    CourseDetailsScreen(
                        course = course,
                        isEnrolled = detailsState.isEnrolled,
                        onEnrollClick = {
                            navController.navigate("${Screen.Enrollment.route}/$courseId")
                        },
                        onBackClick = { navController.popBackStack() },
                        onSyllabusClick = {
                            navController.navigate("${Screen.Syllabus.route}/$courseId")
                        },
                        onLearningPathClick = {
                            navController.navigate("${Screen.LearningPath.route}/$courseId")
                        }
                    )
                }
            }

            // SCREEN 6: Enroll & Payment
            composable(
                route = "${Screen.Enrollment.route}/{courseId}",
                arguments = listOf(navArgument("courseId") { type = NavType.IntType })
            ) { backStackEntry ->
                val courseId = backStackEntry.arguments?.getInt("courseId") ?: 1

                EnrollmentScreen(
                    courseTitle = "Data Structures & Algorithms",
                    onConfirmEnrollment = {
                        courseDetailsViewModel.enrollUser(1, courseId)
                        navController.navigate(Screen.Diagnostic.route)
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // SCREEN 7: Diagnostic Test
            composable(Screen.Diagnostic.route) {
                DiagnosticScreen(
                    onSubmit = {
                        navController.navigate("${Screen.LearningPath.route}/1") {
                            popUpTo(Screen.Diagnostic.route) { inclusive = true }
                        }
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // SCREEN 8: Personalized Learning Path
            composable(
                route = "${Screen.LearningPath.route}/{courseId}",
                arguments = listOf(navArgument("courseId") { type = NavType.IntType })
            ) { backStackEntry ->
                val courseId = backStackEntry.arguments?.getInt("courseId") ?: 1
                val learningPathState by learningPathViewModel.uiState.collectAsState()

                LaunchedEffect(courseId) {
                    learningPathViewModel.loadLearningPath(1, courseId)
                }

                LearningPathScreen(
                    courseName = "Data Structures & Algorithms",
                    learningSteps = learningPathState.steps,
                    onStepCompleted = { step ->
                        learningPathViewModel.updateStep(step)
                    },
                    onStartLearningClick = {
                        navController.navigate(Screen.Lesson.route)
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // SCREEN 9: Home Dashboard
            composable(Screen.Home.route) {
                HomeDashboardScreen(
                    studentName = "Uday",
                    onStartLesson = { navController.navigate(Screen.Lesson.route) },
                    onStartQuiz = { navController.navigate(Screen.Quiz.route) },
                    onRevise = { navController.navigate(Screen.Progress.route) },
                    onExploreCourses = { navController.navigate(Screen.Courses.route) }
                )
            }

            // SCREEN 10: Lesson Player
            composable(Screen.Lesson.route) {
                LessonPlayerScreen(
                    onMarkComplete = {
                        navController.navigate(Screen.Simulation.route)
                    },
                    onBackClick = { navController.popBackStack() },
                    onNavigateToQuiz = {
                        navController.navigate(Screen.Quiz.route)
                    }
                )
            }

            // SCREEN 11: Circuit Simulation Lab
            composable(Screen.Simulation.route) {
                SimulationScreen(
                    simulationViewModel = circuitSimulationViewModel,
                    onBackClick = { navController.popBackStack() },
                    onContinueToQuiz = {
                        navController.navigate(Screen.Quiz.route)
                    }
                )
            }

            // SCREEN 12: Adaptive Quiz
            composable(Screen.Quiz.route) {
                AdaptiveQuizScreen(
                    onSubmit = {
                        navController.navigate(Screen.Results.route)
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // SCREEN 13: Results & Weak Areas
            composable(Screen.Results.route) {
                QuizResultsScreen(
                    onRetryMissed = {
                        navController.navigate(Screen.Quiz.route)
                    },
                    onBackToPath = {
                        navController.navigate("${Screen.LearningPath.route}/1")
                    },
                    onFixWeakConcept = {
                        navController.navigate(Screen.Progress.route)
                    }
                )
            }

            // SCREEN 14: Mastery Dashboard (Progress)
            composable(Screen.Progress.route) {
                val progressState by progressViewModel.uiState.collectAsState()

                ProgressScreen(
                    uiState = progressState,
                    onCompleteRevision = { revId ->
                        progressViewModel.completeRevision(revId)
                    }
                )
            }

            // SCREEN 15: AI Tutor Chat
            composable(Screen.AiTutor.route) {
                AiTutorScreen(
                    tutorViewModel = aiTutorViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }

            // SCREEN 16: Profile & Subscription
            composable(Screen.Profile.route) {
                val profileState by learnerProfileViewModel.uiState.collectAsState()

                ProfileSubscriptionScreen(
                    studentName = "Uday Kumar Kethavath",
                    studentInfo = "${profileState.profile.university} · ${profileState.profile.semester}",
                    onLogout = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    onMyEnrollmentsClick = {
                        navController.navigate(Screen.Courses.route)
                    },
                    onArchitectureClick = {
                        navController.navigate(Screen.Architecture.route)
                    }
                )
            }

            // SCREEN 17: System Architecture View
            composable(Screen.Architecture.route) {
                SystemArchitectureScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }

            // SCREEN 18 & Supporting: Syllabus Units
            composable(
                route = "${Screen.Syllabus.route}/{courseId}",
                arguments = listOf(navArgument("courseId") { type = NavType.IntType })
            ) {
                val units = listOf(
                    "Unit 1: Introduction to Data Structures" to listOf(
                        "Basic Concepts and Abstract Data Types",
                        "Arrays and String Manipulation",
                        "Time and Space Complexity (Big-O)"
                    ),
                    "Unit 2: Linear Data Structures" to listOf(
                        "Singly and Doubly Linked Lists",
                        "Stacks — LIFO principles and call stacks",
                        "Queues — FIFO principles and circular buffers"
                    ),
                    "Unit 3: Non-Linear Data Structures & Trees" to listOf(
                        "Binary Trees and Binary Search Trees (BST)",
                        "Tree Traversals: In-order, Pre-order, Post-order",
                        "Graphs: BFS and DFS algorithms"
                    )
                )

                SyllabusScreen(
                    courseName = "Data Structures & Algorithms",
                    units = units
                )
            }
        }
    }
}
