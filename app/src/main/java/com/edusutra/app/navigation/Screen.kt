package com.edusutra.app.navigation

sealed class Screen(val route: String) {

    data object Login : Screen("login")

    data object Onboarding : Screen("onboarding")

    data object Home : Screen("home")

    data object Courses : Screen("courses")

    data object CourseDetails : Screen("course_details")

    data object LearningPath : Screen("learning_path")

    data object Enrollment : Screen("enrollment")

    data object Diagnostic : Screen("diagnostic")

    data object Lesson : Screen("lesson")

    data object Quiz : Screen("quiz")

    data object Results : Screen("results")

    data object Progress : Screen("progress")

    data object AiTutor : Screen("ai_tutor")

    data object Profile : Screen("profile")
}
