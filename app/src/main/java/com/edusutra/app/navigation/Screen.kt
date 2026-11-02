
package com.edusutra.app.navigation

sealed class Screen(val route: String) {

    // Authentication
    data object Login : Screen("login")

    data object Onboarding : Screen("onboarding")

    // Main dashboard
    data object Home : Screen("home")

    // Courses
    data object Courses : Screen("courses")

    data object CourseDetails : Screen("course_details")

    data object LearningPath : Screen("learning_path")

    data object Enrollment : Screen("enrollment")

    // Assessment and lessons
    data object Diagnostic : Screen("diagnostic")

    data object Lesson : Screen("lesson")

    data object Quiz : Screen("quiz")

    // Results and progress
    data object Results : Screen("results")

    data object Progress : Screen("progress")

    data object MasteryHistory : Screen("mastery_history")

    // Personal learning assistant
    data object AiTutor : Screen("ai_tutor")

    // Student account
    data object Profile : Screen("profile")
}
