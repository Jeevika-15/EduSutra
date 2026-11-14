package com.edusutra.app.data

import com.edusutra.app.data.local.AppDatabase
import com.edusutra.app.data.local.entity.*
import kotlinx.coroutines.flow.first

object DemoDataSeeder {

    suspend fun seedAllIfEmpty(database: AppDatabase) {
        val userId = 1

        // 1. Seed User
        val existingUser = database.userDao().getUserById(userId)
        if (existingUser == null) {
            database.userDao().insertUser(
                User(
                    userId = userId,
                    fullName = "Uday Kumar Kethavath",
                    email = "uday@k-university.edu",
                    passwordHash = "password123",
                    university = "JNTU University",
                    course = "B.Tech CSE - Semester 4"
                )
            )
        }

        // 2. Seed LearnerProfile
        val existingProfile = database.learnerProfileDao().getProfile(userId).first()
        if (existingProfile == null) {
            database.learnerProfileDao().insertOrUpdate(
                LearnerProfile(
                    userId = userId,
                    university = "JNTU University - B.Tech CSE",
                    semester = "Sem 4",
                    learningGoal = "Clear weak concepts",
                    dailyStudyTime = "2 hours",
                    contentLanguage = "English",
                    streakDays = 12,
                    isPro = true,
                    proPlanName = "Full Access",
                    renewalDate = "12 Nov 2026"
                )
            )
        }

        // 3. Seed Courses
        val existingCourses = database.courseDao().getAllCourses().first()
        if (existingCourses.isEmpty()) {
            val courses = listOf(
                Course(
                    courseId = 1,
                    title = "Data Structures & Algorithms",
                    description = "A university-level path from arrays to graphs, with prerequisites checked before each module. 7 adaptive quizzes after every topic and 3 circuit-style simulations.",
                    category = "Computer Science",
                    duration = "12 Weeks",
                    difficulty = "Intermediate",
                    instructor = "Prof. R. Mishra",
                    imageUrl = ""
                ),
                Course(
                    courseId = 2,
                    title = "DBMS — Semester Exam Path",
                    description = "Learn SQL, relational schema design, database normalization and transaction processing.",
                    category = "Database",
                    duration = "8 Weeks",
                    difficulty = "Intermediate",
                    instructor = "Edu Sutra Faculty",
                    imageUrl = ""
                ),
                Course(
                    courseId = 3,
                    title = "Control Systems — Signals",
                    description = "Explore state variables, transfer functions, stability criteria and signal flow graphs.",
                    category = "Electronics",
                    duration = "6 Weeks",
                    difficulty = "Beginner",
                    instructor = "YouTube Edu",
                    imageUrl = ""
                ),
                Course(
                    courseId = 4,
                    title = "Artificial Intelligence",
                    description = "Explore search algorithms, knowledge representation, neural networks and intelligent agents.",
                    category = "Artificial Intelligence",
                    duration = "10 Weeks",
                    difficulty = "Advanced",
                    instructor = "Prof. Michael Lee",
                    imageUrl = ""
                )
            )
            database.courseDao().insertCourses(courses)
        }

        // 4. Seed Enrollments
        val existingEnrollments = database.enrollmentDao().getEnrollmentsByUser(userId).first()
        if (existingEnrollments.isEmpty()) {
            database.enrollmentDao().insertEnrollment(
                Enrollment(
                    enrollmentId = 1,
                    userId = userId,
                    courseId = 1,
                    status = "Active"
                )
            )
        }

        // 5. Seed LearningPath
        val existingSteps = database.learningPathDao().getLearningPath(userId, 1).first()
        if (existingSteps.isEmpty()) {
            val steps = listOf(
                LearningPath(
                    userId = userId,
                    courseId = 1,
                    stepTitle = "1 Prerequisites",
                    description = "Big-O basic programming, recursion basics",
                    stepOrder = 1,
                    isCompleted = true
                ),
                LearningPath(
                    userId = userId,
                    courseId = 1,
                    stepTitle = "2 Fundamentals",
                    description = "Arrays ✓ Linked Lists ✓",
                    stepOrder = 2,
                    isCompleted = true
                ),
                LearningPath(
                    userId = userId,
                    courseId = 1,
                    stepTitle = "3 Core Concepts",
                    description = "• Stacks & Queues (active)\n• Trees (after module 3)\n• Graphs",
                    stepOrder = 3,
                    isCompleted = false
                ),
                LearningPath(
                    userId = userId,
                    courseId = 1,
                    stepTitle = "4 Practice · Test · Revision",
                    description = "Adaptive quiz, mock test, spaced revision days 2/5/12/30",
                    stepOrder = 4,
                    isCompleted = false
                )
            )
            database.learningPathDao().insertSteps(steps)
        }

        // 6. Seed Progress, Mastery & Revision
        val existingProgress = database.progressDao().getProgressByUser(userId).first()
        if (existingProgress.isEmpty()) {
            database.progressDao().insertProgress(
                Progress(
                    userId = userId,
                    courseId = 1,
                    completedLessons = 8,
                    totalLessons = 12,
                    completionPercentage = 66.7f,
                    totalStudyMinutes = 240
                )
            )

            // Mastery List
            val masteries = listOf(
                Mastery(userId = userId, courseId = 1, topicName = "Arrays", masteryScore = 90f, masteryLevel = "Advanced"),
                Mastery(userId = userId, courseId = 1, topicName = "Linked Lists", masteryScore = 85f, masteryLevel = "Advanced"),
                Mastery(userId = userId, courseId = 1, topicName = "Stacks & Queues", masteryScore = 68f, masteryLevel = "Intermediate"),
                Mastery(userId = userId, courseId = 1, topicName = "Graphs", masteryScore = 50f, masteryLevel = "Intermediate"),
                Mastery(userId = userId, courseId = 1, topicName = "Recursion", masteryScore = 40f, masteryLevel = "Beginner")
            )
            for (m in masteries) {
                database.masteryDao().insertMastery(m)
            }

            // Revisions
            database.revisionDao().insertRevision(
                Revision(userId = userId, courseId = 1, topicName = "Recursion", reason = "Low mastery score (40%)", priority = "High")
            )
            database.revisionDao().insertRevision(
                Revision(userId = userId, courseId = 1, topicName = "Database Normalization", reason = "Spaced revision recommended", priority = "Medium")
            )
        }

        // 7. Seed Syllabus Subjects
        val existingSubjects = database.syllabusSubjectDao().getSubjects(userId).first()
        if (existingSubjects.isEmpty()) {
            val subjects = listOf(
                SyllabusSubject(userId = userId, semester = "Sem 4", subjectName = "Data Structures", status = "PATH READY", isIncluded = true),
                SyllabusSubject(userId = userId, semester = "Sem 4", subjectName = "Database Management", status = "PATH READY", isIncluded = true),
                SyllabusSubject(userId = userId, semester = "Sem 4", subjectName = "Computer Networks", status = "PARSING", isIncluded = true),
                SyllabusSubject(userId = userId, semester = "Sem 4", subjectName = "Operating Systems", status = "PENDING", isIncluded = false),
                SyllabusSubject(userId = userId, semester = "Sem 4", subjectName = "Discrete Mathematics", status = "PENDING", isIncluded = false)
            )
            database.syllabusSubjectDao().insertSubjects(subjects)
        }
    }
}
