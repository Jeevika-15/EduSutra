package com.edusutra.app.navigation

sealed class Screen(val route: String) {

    // Page 1: Entry, Setup & Discovery
    data object Login : Screen("login")
    data object Onboarding : Screen("onboarding")
    data object AddSyllabus : Screen("add_syllabus")
    data object Courses : Screen("courses")

    // Page 2: Enrollment & Personalized Learning
    data object CourseDetails : Screen("course_details")
    data object Enrollment : Screen("enrollment")
    data object Diagnostic : Screen("diagnostic")
    data object LearningPath : Screen("learning_path")
    data object Home : Screen("home")

    // Page 3: Learn, Practise & Track
    data object Lesson : Screen("lesson")
    data object Simulation : Screen("simulation")
    data object Quiz : Screen("quiz")
    data object Results : Screen("results")
    data object Progress : Screen("progress")

    // Page 4: Ask, Account & System View
    data object AiTutor : Screen("ai_tutor")
    data object Profile : Screen("profile")
    data object Architecture : Screen("architecture")

    // Supporting routes
    data object Syllabus : Screen("syllabus")
}
